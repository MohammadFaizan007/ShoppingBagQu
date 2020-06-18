package com.shoppingbag.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.adapter.BrowsePlanAdapter;
import com.shoppingbag.app.CheckErrorCode;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.jioPrepaid.ResponseAddInfoJio;
import com.shoppingbag.model.jioPrepaid.ResponseJioBrowsePlanPrepaid;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utilities.activities.MobileRechargeActivity;
import com.shoppingbag.utils.DialogUtil;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class BrowsePlanJio extends DialogFragment implements MvpView {
    public static String TAG = "BrowsePlanJio";
    Unbinder unbinder;
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.browsePlan_recycler)
    RecyclerView browsePlanRecycler;
    @BindView(R.id.txtNoRecord)
    TextView txtNoRecord;
    String keys1 = "AndroidIconReuse";
    String keys2 = "";
    SecretKey cross_intent, temp_compared;
    private ProgressDialog mProgressDialog;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }


    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.browse_plan, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
           // temp_compared = new SecretKeySpec(keys1.getBytes(), "AES");
          //  keys2 = Cons.decryptMsg(BuildConfig.CASHBAG_COMPARED, temp_compared);
           // cross_intent = new SecretKeySpec(keys2.getBytes(), "AES");

            cross_intent = new SecretKeySpec(BuildConfig.CASHBAG_COMPARED.getBytes(), "AES");
            title.setText("Available Plan");
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(RecyclerView.VERTICAL);
            browsePlanRecycler.setLayoutManager(manager);
            if (NetworkUtils.getConnectivityStatus(getActivity()) != 0) {
                getPlan(getArguments().getString("MobNo"));
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void getPlan(String mobNo) {
       // ApiServices apiServicesv2 = ServiceGenerator.createServiceFile(ApiServices.class);
        ApiServices apiServicesv2 = ServiceGenerator.createService(ApiServices.class, BuildConfig.BASE_URL_CyberPlate);
        showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("NUMBER", mobNo);
        object.addProperty("REQ_TYPE", "2");
        object.addProperty("AMOUNT", "10.00");

        JsonObject body = new JsonObject();
        try {
            body.addProperty("body", Cons.encryptMsg(object.toString(), cross_intent));
            LoggerUtil.logItem(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoggerUtil.logItem(object);
        Call<JsonObject> call = apiServicesv2.getJioPlan(body, PreferencesManager.getInstance(getActivity()).getANDROIDID());
        call.enqueue(new Callback<JsonObject>() {

            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                try {
                    hideLoading();
                    LoggerUtil.logItem(call.request().url());
                    if (response.isSuccessful()) {
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        JsonArray convertedObject = new Gson().fromJson(paramResponse, JsonArray.class);
                        Cons.responseJioBrowsePlanArray = new ArrayList<>();
                        LoggerUtil.logItem(convertedObject.toString());
                        if (convertedObject.size() > 0) {
                            JsonObject object = convertedObject.get(0).getAsJsonObject();
                            if (object.get("ERROR").getAsString().equalsIgnoreCase("0") &&
                                    object.get("RESULT").getAsString().equalsIgnoreCase("0")) {
                                //Log.e("ORIGINAL", (object.get("ADDINFO").getAsString()));
                                String addinfo = Utils.replaceBackSlash(object.get("ADDINFO").getAsString());
                                //Log.e("Object==", "PARSING---------------");
                                //Log.e("AFTER", addinfo);

                                String firstChar = String.valueOf(addinfo.charAt(0));
                                if (firstChar.equalsIgnoreCase("[")) {
                                    //Log.e("[", addinfo);
                                    Cons.responseJioBrowsePlanArray = Utils.getList(addinfo, ResponseJioBrowsePlanPrepaid.class);
                                    BrowsePlanAdapter adapter = new BrowsePlanAdapter(getActivity(), Cons.responseJioBrowsePlanArray.get(0).getAddinfo().getPlanoffering(), BrowsePlanJio.this);
                                    browsePlanRecycler.setAdapter(adapter);
                                    browsePlanRecycler.setVisibility(View.VISIBLE);
                                    txtNoRecord.setVisibility(View.GONE);
                                    //Log.e("Array==", Cons.responseJioBrowsePlanArray.get(0).getErrmsg());
                                } else {
                                    //Log.e("else", addinfo);
                                    Gson gson = new GsonBuilder().create();
                                    ResponseAddInfoJio responseJioBrowsePlanPrepaid = gson.fromJson(addinfo, ResponseAddInfoJio.class);
                                    LoggerUtil.logItem(responseJioBrowsePlanPrepaid);
                                    if (responseJioBrowsePlanPrepaid.getPlanoffering() != null && responseJioBrowsePlanPrepaid.getPlanoffering().size() > 0) {
                                        BrowsePlanAdapter adapter = new BrowsePlanAdapter(getActivity(), responseJioBrowsePlanPrepaid.getPlanoffering(), BrowsePlanJio.this);
                                        browsePlanRecycler.setAdapter(adapter);
                                        browsePlanRecycler.setVisibility(View.VISIBLE);
                                        txtNoRecord.setVisibility(View.GONE);
                                    } else {
                                        browsePlanRecycler.setVisibility(View.GONE);
                                        txtNoRecord.setVisibility(View.VISIBLE);
                                    }
                                }

                            } else {
                                hideLoading();
                                CheckErrorCode checkErrorCode = new CheckErrorCode();
                                checkErrorCode.isValidTransaction(getActivity(), object.get("ERROR").getAsString());
                                browsePlanRecycler.setVisibility(View.GONE);
                                txtNoRecord.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    browsePlanRecycler.setVisibility(View.GONE);
                    txtNoRecord.setVisibility(View.VISIBLE);
//                    Toast.makeText(getContext(), "Something went wrong. \nPlease try after some time.", Toast.LENGTH_SHORT).show();
                }
            }


            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }


    public void showAlert(String msg, int color, int icon) {
        Alerter.create(getActivity())
                .setText(msg)
                .setTextAppearance(R.style.alertTextColor)
                .setBackgroundColorRes(color)
                .setIcon(icon)
                .show();
    }

    public void goToActivity(Activity activity, Class<?> classActivity, Bundle bundle) {
        Utils.hideSoftKeyboard(activity);
        Intent intent = new Intent(activity, classActivity);
        if (bundle != null)
            intent.putExtra(PAYLOAD_BUNDLE, bundle);
        activity.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        dismiss();
    }


    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    public void openActivityOnTokenExpire() {

    }


    public void onError(int resId) {

    }


    public void onError(String message) {

    }


    public void showMessage(String message) {

    }


    public void showMessage(int resId) {

    }


    public boolean isNetworkConnected() {
        return false;
    }


    public void hideKeyboard() {

    }


    public void getTrainingID(String trainingamount, String trainingname, String trainingID) {

    }


    public void getDateDetails(Date date) {

    }


    public void getClickPosition(int amount, String id) {
        ((MobileRechargeActivity) getContext()).etRechargeAmount.setText(String.valueOf(amount));
        ((MobileRechargeActivity) getContext()).jio_plan_id = id;
        dismiss();
    }


    public void getMyClickPosition(String name, String tag) {

    }


    public void getGiftCardCategoryId(String id, String name) {

    }


    public void checkAvailability(String id, String date, String name, String amount) {

    }


    public void openSearchCategory(String searchItemId, String searchName) {

    }


    public void getClickChildPosition(String name, String tag, Bundle bundle) {

    }


    public void getPayoutWithdrawalId(String requestId) {

    }


    public void getProviderHint(String providerName, String hint) {

    }

    public void showLoading() {
        mProgressDialog = DialogUtil.showLoadingDialog(getActivity(), "Base Activity");
    }


    public void getClickPositionDirectMember(int position, String tag, String memberId) {

    }

    @OnClick(R.id.side_menu)
    public void onViewClicked() {
        dismiss();
    }


    public void setTransferTicket(String trainingamount, String trainingname, String trainingID, String totalTickets, String date) {

    }
}
