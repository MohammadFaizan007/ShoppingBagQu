package com.shoppingbag.wallet.dmt.addmoney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.CheckErrorCode;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.MaintenancePage;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.request.utility.response.ResponseBalanceAmount;
import com.shoppingbag.model.utility.ResponseBenficiarylist;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.wallet.adapter.BeneficiaryListAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_TYPE_FOUR_DMT_BENEFICIARY_REGISTRATION;
import static com.shoppingbag.app.AppConfig.PAYLOAD_TYPE_THREE_DMT_FUND_TRANSFER;

public class BeneficiaryList extends BaseFragment implements MvpView {

    Unbinder unbinder;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btnTransfer)
    Button btnTransfer;
    @BindView(R.id.floatingAdd)
    FloatingActionButton floatingAdd;
    private int selectPosition = -1;
    private List<ResponseBenficiarylist> response_new;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beneficiary_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AddMoneyContainer)context).title.setText("Beneficiary List");
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        list.setLayoutManager(manager);

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectPosition > -1) {
                    getWalletBalance();
                } else {
                    showMessage("Please Select Beneficiary");
                }
            }
        });

        floatingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddMoneyContainer)context).replaceFragmentAddBack(new AddBeneficiary(),"Add Beneficiary");
            }
        });

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            list.setVisibility(View.GONE);
            getBeneficiaryList();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }



    private void getBeneficiaryList() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            JsonObject param = new JsonObject();
            param.addProperty("RemitterId", Cons.decryptMsg(PreferencesManager.getInstance(context).getREMITTER_ID(), cross_intent));
            LoggerUtil.logItem(param);
            Call<JsonObject> listCall = apiServicesCyper.getBeneficiaryDetils(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            listCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    try {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(call.request().url());

                        if (response.isSuccessful()) {
                            response_new = Utils.getList(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseBenficiarylist.class);
                            if (response_new.size() > 0) {
                                list.setVisibility(View.VISIBLE);
                                BeneficiaryListAdapter listAdapter = new BeneficiaryListAdapter(context, response_new, BeneficiaryList.this);
                                list.setAdapter(listAdapter);
                            } else {
                                getBeneficiaryRegistration();
//                                list.setVisibility(View.GONE);
//                                showMessage("No Beneficiary found!");
                            }
                        } else {
                            goToActivityWithFinish(MaintenancePage.class, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        list.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    try {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                        list.setVisibility(View.GONE);
                        LoggerUtil.logItem(t);
                        LoggerUtil.logItem(call.execute().body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getClickPosition(int position, String tag) {
        super.getClickPosition(position, tag);
//        if (tag.equalsIgnoreCase("delete")) {
//            if (NetworkUtils.getConnectivityStatus(context) != 0)
//                getBeneficiaryList();
//        }
        selectPosition = position;

    }

    private void getWalletBalance() {
        try {
            showLoading();
            ApiServices apiServices = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_CyberPlate);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> call = apiServices.getbalanceAmount(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        if (response.isSuccessful()) {
                            ResponseBalanceAmount responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseBalanceAmount.class);
                            if (response.body() != null && responseWalletBalance.getStatus().equalsIgnoreCase("Success")) {
                                getFundTransfer(responseWalletBalance.getValidateToken());
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private static DecimalFormat format = new DecimalFormat("0.00");

    private void getFundTransfer(String token) {

        try {
            ((AddMoneyContainer) context).amount = format.format(Double.parseDouble(((AddMoneyContainer) context).amount));
            showLoading();
            ApiServices apiServices = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_CyberPlate);

            JsonObject object = new JsonObject();
            object.addProperty("Type", PAYLOAD_TYPE_THREE_DMT_FUND_TRANSFER);
            object.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            object.addProperty("AMOUNT_ALL", ((AddMoneyContainer) context).amount);
            object.addProperty("Amount", ((AddMoneyContainer) context).amount);
            object.addProperty("benId", response_new.get(selectPosition).getBeneficiaryId());
            object.addProperty("ValidateToken", token);
            object.addProperty("BeneficiaryName", response_new.get(selectPosition).getBeneficiaryFirstName() + " " +
                    response_new.get(selectPosition).getBeneficiaryLastName());
            object.addProperty("routingType", "IMPS");
            object.addProperty("FK_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));

            JsonObject body = new JsonObject();
            body.addProperty("body", Cons.encryptMsg(object.toString(), cross_intent));
            LoggerUtil.logItem(body);


            Call<JsonObject> call = apiServices.getFundTransfer(body, PreferencesManager.getInstance(context).getANDROIDID());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            JsonArray response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonArray.class);
                            if (response_new.size() > 0) {
                                JsonObject jObj = response_new.get(0).getAsJsonObject();

                                if ((jObj.get("ERROR").getAsString()).equalsIgnoreCase("0") || (jObj.get("RESULT").getAsString()).equalsIgnoreCase("0")) {
                                    showMessage("Transaction Successful");
                                    context.finish();
                                } else {
                                    CheckErrorCode code = new CheckErrorCode();
                                    code.isValidTransaction(context, jObj.get("RESULT").getAsString());
                                }

                            } else {
                                Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBeneficiaryRegistration() {
        try {

            showLoading();

            JsonObject object = new JsonObject();
            object.addProperty("remId", Cons.decryptMsg(PreferencesManager.getInstance(context).getREMITTER_ID(), cross_intent));
            object.addProperty("AMOUNT", "1.0");
            object.addProperty("AMOUNT_ALL", "1.0");
            object.addProperty("fName", Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
            object.addProperty("lName", Cons.decryptMsg(PreferencesManager.getInstance(context).getLNAME(), cross_intent));
            object.addProperty("benAccount", PreferencesManager.getInstance(context).getBankAccount());
            object.addProperty("benIFSC", PreferencesManager.getInstance(context).getBankIfsc());
            object.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            object.addProperty("Type", PAYLOAD_TYPE_FOUR_DMT_BENEFICIARY_REGISTRATION);

            LoggerUtil.logItem(object);

            Call<JsonObject> objectCall = apiServicesCyper.getBeneficiaryRegistration(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
            objectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());

                    try {
                        JsonArray response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonArray.class);
                        if (response_new.size() > 0) {

                            JsonObject object = response_new.get(0).getAsJsonObject();
                            if (object.get("ERROR").getAsString().equalsIgnoreCase("0") &&
                                    object.get("RESULT").getAsString().equalsIgnoreCase("0")) {

                                JSONObject addinfo = new JSONObject((Utils.replaceBackSlash(object.get("ADDINFO").getAsString())));
                                JSONObject data = addinfo.getJSONObject("data");
                                JSONObject beneficiary = data.getJSONObject("beneficiary");
                                getAddBeneficiaryDetailsLog(beneficiary.getString("id"));

                            } else {
                                CheckErrorCode checkErrorCode = new CheckErrorCode();
                                checkErrorCode.isValidTransaction(context, object.get("ERROR").getAsString());
                            }
                        } else {
                            showAlert("Something went wrong.", R.color.red, R.drawable.alerter_ic_notifications);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                }
            });
        } catch (Error | Exception e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    private void getAddBeneficiaryDetailsLog(String id) {
        try {

            LoggerUtil.logItem("ADD BEN LOG");

            JsonObject param = new JsonObject();
            param.addProperty("BeneficiaryMobileNo", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            param.addProperty("BeneficiaryBankName", PreferencesManager.getInstance(context).getBankName());
            param.addProperty("BeneficiaryIFSC", PreferencesManager.getInstance(context).getBankIfsc());
            param.addProperty("BeneficiaryLastName", Cons.decryptMsg(PreferencesManager.getInstance(context).getLNAME(), cross_intent));
            param.addProperty("BeneficiaryAccountNo", PreferencesManager.getInstance(context).getBankAccount());
            param.addProperty("BeneficiaryFirstName", Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
            param.addProperty("BeneficiaryId", id);
            param.addProperty("RemitterId", Cons.decryptMsg(PreferencesManager.getInstance(context).getREMITTER_ID(), cross_intent));

            Call<JsonObject> objectCall = apiServicesCyper.getAddBeneficiaryDetils(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            objectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    try {
                        LoggerUtil.logItem(response.body());
//                        {"Msg":"Success","Status":"0"}
                        JsonObject response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonObject.class);
                        if (response_new.get("Status").getAsString().equalsIgnoreCase("0")) {
                            showMessage("Beneficiary Added Successfully");
                            getBeneficiaryList();
                        } else {
                            showMessage("Something went wrong.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();

                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }


}
