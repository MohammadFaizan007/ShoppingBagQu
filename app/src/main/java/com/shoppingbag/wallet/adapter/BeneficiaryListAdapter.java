package com.shoppingbag.wallet.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.CheckErrorCode;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.request.utility.response.ResponseBalanceAmount;
import com.shoppingbag.model.utility.ResponseBenficiarylist;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.wallet.dmt.DmtActivity;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;
import static com.shoppingbag.app.AppConfig.PAYLOAD_TYPE_SIX_DMT_BENEFICIARY_DELETE;
import static com.shoppingbag.app.AppConfig.PAYLOAD_TYPE_THREE_DMT_FUND_TRANSFER;

public class BeneficiaryListAdapter extends RecyclerView.Adapter<BeneficiaryListAdapter.ViewHolder> {

    private static DecimalFormat format = new DecimalFormat("0.00");
    private final Activity context;
    private SecretKey cross_intent;
    private List<ResponseBenficiarylist> list;
    private MvpView mvpView;
    private Dialog transferDialog;

    public BeneficiaryListAdapter(Context context, List<ResponseBenficiarylist> body, MvpView mvpView) {
        this.context = (Activity) context;
        this.list = body;
        this.mvpView = mvpView;
        try {
            cross_intent = new SecretKeySpec(BuildConfig.CASHBAG_COMPARED.getBytes(), "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ben_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtName.setText(String.format("%s %s", list.get(position).getBeneficiaryFirstName(), list.get(position).getBeneficiaryLastName()));
        holder.txtMobileno.setText(list.get(position).getBeneficiaryMobileNo());
        holder.txtAccountNo.setText(list.get(position).getBeneficiaryAccountNo());
        holder.txtBankName.setText(list.get(position).getBeneficiaryBankName());
        holder.txtIfscCode.setText(list.get(position).getBeneficiaryIFSC());

        if (list.get(position).isSelect()) {
            holder.rbSelect.setChecked(true);
        } else {
            holder.rbSelect.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void transferDialog(String mobileNumber, String benId, String benName) {
        transferDialog = new Dialog(context);
        transferDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        transferDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        transferDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        transferDialog.setContentView(R.layout.dialog_fund_transfer);

        EditText et_amount = transferDialog.findViewById(R.id.et_amount);
        EditText et_Remark = transferDialog.findViewById(R.id.et_Remark);
        Button btn_send_money = transferDialog.findViewById(R.id.btn_send_money);
        Button btn_cancel = transferDialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(v -> transferDialog.dismiss());
        btn_send_money.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(et_amount.getText().toString()) && Float.parseFloat(et_amount.getText().toString().trim()) >= 50.00) {
                getWalletBalance(benId, benName, mobileNumber, et_amount.getText().toString().trim(),
                        et_Remark.getText().toString());
            } else
                Toast.makeText(context, "Minimum amount should be 50.", Toast.LENGTH_SHORT).show();
        });

        transferDialog.setCancelable(false);
        transferDialog.show();
    }

    private void getFundTransfer(String benId, String benName, String token, String amount, String remark) {

        try {
            amount = format.format(Double.parseDouble(amount));
            ((DmtActivity) context).showLoading();
            ApiServices apiServices = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_CyberPlate);

            JsonObject object = new JsonObject();
            object.addProperty("Type", PAYLOAD_TYPE_THREE_DMT_FUND_TRANSFER);
            object.addProperty("NUMBER", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            object.addProperty("AMOUNT_ALL", amount);
            object.addProperty("Amount", amount);
            object.addProperty("benId", benId);
            object.addProperty("ValidateToken", token);
            object.addProperty("BeneficiaryName", benName);
            object.addProperty("routingType", "IMPS");
            object.addProperty("FK_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));

            JsonObject body = new JsonObject();
            body.addProperty("body", Cons.encryptMsg(object.toString(), cross_intent));
            LoggerUtil.logItem(body);


            Call<JsonObject> call = apiServices.getFundTransfer(body, PreferencesManager.getInstance(context).getANDROIDID());
            String finalAmount = amount;
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    ((DmtActivity) context).hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            JsonArray response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonArray.class);
                            if (response_new.size() > 0) {
                                transferDialog.dismiss();
                                JsonObject jObj = response_new.get(0).getAsJsonObject();


                                if ((jObj.get("ERROR").getAsString()).equalsIgnoreCase("0") && (jObj.get("RESULT").getAsString()).equalsIgnoreCase("0")) {
                                    ((DmtActivity) context).showMessage("Transaction Successful");
                                } else {
                                    CheckErrorCode code = new CheckErrorCode();
                                    code.isValidTransaction(context, jObj.get("RESULT").getAsString());
                                }

                            } else {
                                transferDialog.dismiss();
                                Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    ((DmtActivity) context).hideLoading();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JsonObject bodyParam(JsonObject param) {
        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(param.toString(), cross_intent));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    private void getBeneficiaryDelete(String mobileNumber, String benId) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_CyberPlate);
        ((DmtActivity) context).showLoading();

        try {
            JsonObject object = new JsonObject();
            object.addProperty("Type", PAYLOAD_TYPE_SIX_DMT_BENEFICIARY_DELETE);
            object.addProperty("NUMBER", mobileNumber);
            object.addProperty("AMOUNT_ALL", "1.00");
            object.addProperty("Amount", "1.00");
            object.addProperty("benId", benId);
            object.addProperty("remId", Cons.decryptMsg(PreferencesManager.getInstance(context).getREMITTER_ID(), cross_intent));

            JsonObject body = new JsonObject();
            body.addProperty("body", Cons.encryptMsg(object.toString(), cross_intent));
            LoggerUtil.logItem(body);

            Call<JsonObject> objectCall = apiServices.beneficiarydelete(body, PreferencesManager.getInstance(context).getANDROIDID());
            objectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    ((DmtActivity) context).hideLoading();
                    try {
                        LoggerUtil.logItem(response.body());
                        mvpView.getClickPosition(0, "delete");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    ((DmtActivity) context).hideLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getWalletBalance(String benId, String benName, String mobileNumber, String amountPackage, String remark) {
        try {
            ((DmtActivity) context).showLoading();
            ApiServices apiServices = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_CyberPlate);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("MemberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);

            Call<JsonObject> call = apiServices.getbalanceAmount(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        ((DmtActivity) context).hideLoading();
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        if (response.isSuccessful()) {
                            ResponseBalanceAmount responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseBalanceAmount.class);
                            if (response.body() != null && responseWalletBalance.getStatus().equalsIgnoreCase("Success")) {
                                getWalletBalance1(benId, benName, responseWalletBalance.getValidateToken(), amountPackage, remark);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    ((DmtActivity) context).hideLoading();
                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void getWalletBalance1(String benId, String benName, String token, String amount, String remark) {
        try {
            ((DmtActivity) context).showLoading();
            ApiServices apiServices = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_MLM);

            String url = BuildConfig.BASE_URL_MLM + "GetWallet?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
            Call<JsonObject> call = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    try {
                        ((DmtActivity) context).hideLoading();
                        LoggerUtil.logItem(response.body());
                        LoggerUtil.logItem(response.code());
                        if (response.isSuccessful()) {
                            ResponseWalletBalance responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseWalletBalance.class);
                            if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {

                                if (responseWalletBalance.getResult().getCommisionWalletAmount() >= Float.parseFloat(amount)) {
                                    getFundTransfer(benId, benName, token, amount, remark);
                                } else {
                                    createAddBalanceDialog(context);
                                }

                            } else {
                                createAddBalanceDialog(context);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    ((DmtActivity) context).hideLoading();
                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }


    private void createAddBalanceDialog(Context context) {
        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder1.setTitle("Insufficient commission");
        builder1.setMessage("You have insufficient commission.");
        builder1.setCancelable(true);

        builder1.setNegativeButton("Cancel", (dialog, id) -> {
            dialog.dismiss();

        });

        androidx.appcompat.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null) intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
//        activity.overridePendingTransition(kkm.com.shoppingbag.R.anim.slide_from_right, kkm.com.shoppingbag.R.anim.slide_to_left);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtMobileno)
        TextView txtMobileno;
        @BindView(R.id.txtAccountNo)
        TextView txtAccountNo;
        @BindView(R.id.txtBankName)
        TextView txtBankName;
        @BindView(R.id.txtIfscCode)
        TextView txtIfscCode;
        @BindView(R.id.rbSelect)
        RadioButton rbSelect;
//        @BindView(R.id.imgDelete)
//        ImageView imgDelete;
//        @BindView(R.id.btn_send_money)
//        Button btn_send_money;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for (int i = 0; i < list.size(); i++) {
                        if (i != getAdapterPosition())
                            list.get(i).setSelect(false);
                    }

                    list.get(getAdapterPosition()).setSelect(true);
                    mvpView.getClickPosition(getAdapterPosition(), "");
                    notifyDataSetChanged();
                }
            });

//            btn_send_money.setOnClickListener(v -> transferDialog(list.get(getAdapterPosition()).getBeneficiaryMobileNo(),
//                    list.get(getAdapterPosition()).getBeneficiaryId(),
//                    list.get(getAdapterPosition()).getBeneficiaryFirstName() + " " +
//                            list.get(getAdapterPosition()).getBeneficiaryLastName()));
//
//            imgDelete.setOnClickListener(v -> getBeneficiaryDelete(list.get(getAdapterPosition()).getBeneficiaryMobileNo(),
//                    list.get(getAdapterPosition()).getBeneficiaryId()));
        }
    }
}