package com.shoppingbag.wallet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.bankdetails.ResponseBankDetails;
import com.shoppingbag.model.response.wallet.ResponseNewWalletRequest;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.FileUtils;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.shoppingbag.R;
import com.shoppingbag.common_activities.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shoppingbag.app.AppConfig.PAYLOAD_BUNDLE;

public class OfflineBagRequest extends BaseActivity implements IPickCancel, IPickResult {

    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.et_RequestAmount)
    TextInputEditText etRequestAmount;
    @BindView(R.id.documentPreview)
    ImageView documentPreview;


    @BindView(R.id.et_TransactionNumber)
    TextInputEditText etTransactionNumber;

    @BindView(R.id.inputNumber)
    TextInputLayout inputNumber;

    @BindView(R.id.etPaymentDate)
    TextInputEditText etPaymentDate;
    @BindView(R.id.radio_neft)
    RadioButton radioNeft;
    @BindView(R.id.radio_imps)
    RadioButton radioImps;
    @BindView(R.id.radio_rtgs)
    RadioButton radioRtgs;
    @BindView(R.id.radio_upi)
    RadioButton radioUpi;

    String payMode = "";
    PickImageDialog dialog;
    File documentFile;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;
    @BindView(R.id.etCompanyName)
    TextView etCompanyName;
    @BindView(R.id.tv_acco_number)
    TextView tvAccoNumber;
    @BindView(R.id.tv_ifsc)
    TextView tvIfsc;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_branch)
    TextView tvBranch;
    @BindView(R.id.radioGroup_payment)
    RadioGroup radioGroupPayment;
    @BindView(R.id.otherOptionsLayout)
    LinearLayout otherOptionsLayout;
    @BindView(R.id.selectDocument)
    Button selectDocument;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_bag_request_new);
        ButterKnife.bind(this);

        title.setText("Wallet Request");

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getBankDetails();
        } else {
            showMessage(context.getString(R.string.alert_internet));
        }

        try {
            if (getIntent().getBundleExtra(PAYLOAD_BUNDLE) != null) {
                etRequestAmount.setText(getIntent().getBundleExtra(PAYLOAD_BUNDLE).getString("amount"));
                etRequestAmount.setSelection(etRequestAmount.getText().toString().length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        radioNeft.setOnClickListener(view -> {
            payMode = "NEFT";
            radioNeft.setChecked(true);
            radioImps.setChecked(false);
            radioRtgs.setChecked(false);
            radioUpi.setChecked(false);
        });
        radioImps.setOnClickListener(view -> {
            payMode = "IMPS";
            radioNeft.setChecked(false);
            radioImps.setChecked(true);
            radioRtgs.setChecked(false);
            radioUpi.setChecked(false);
        });
        radioRtgs.setOnClickListener(view -> {
            payMode = "RTGS";
            radioNeft.setChecked(false);
            radioImps.setChecked(false);
            radioRtgs.setChecked(true);
            radioUpi.setChecked(false);
        });
        radioUpi.setOnClickListener(view -> {
            payMode = "UPI";
            radioNeft.setChecked(false);
            radioImps.setChecked(false);
            radioRtgs.setChecked(false);
            radioUpi.setChecked(true);
        });
    }

    private void datePicker(final EditText et) {
        Calendar cal = Calendar.getInstance();
        int mYear, mMonth, mDay;
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
            et.setText(Utils.changeDateFormatSlash(dayOfMonth, monthOfYear, year));

        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick({R.id.selectDocument, R.id.btn_submit, R.id.etPaymentDate, R.id.side_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectDocument:
                showDialog();
                break;
            case R.id.btn_submit:
                if (validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        try {
                            //Log.e("Mobile Number == ", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
                            if (Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent).equalsIgnoreCase("")) {
                                showAlert("Mobile number not found. Kindly, update it from profile.", R.color.red, R.drawable.error);
                            } else {
                                if (documentFile != null)
                                    setRequestForBagRequest();
                                else
                                    showAlert("Please attach any respective image of your offline payment.", R.color.red, R.drawable.error);
                            }
                        } catch (Error | Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.etPaymentDate:
                datePicker(etPaymentDate);
                break;
            case R.id.side_menu:
                finish();
                break;
        }
    }

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        pickSetup.setTitle("Choose Document");
        pickSetup.setGalleryIcon(R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);
        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getSupportFragmentManager());
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        //Log.e("RESULT", " = " + pickResult.getPath());
        if (pickResult.getError() == null) {
            CropImage.activity(pickResult.getUri())
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setAspectRatio(16, 9)
                    .start(context);
        } else {
            //Log.e("RESULT", "ERROR = " + pickResult.getError());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                documentFile = FileUtils.getFile(context, result.getUri());
                Bitmap bitmap = Utils.getCompressedBitmap(documentFile.getAbsolutePath());
                documentPreview.setImageBitmap(bitmap);
                documentPreview.setVisibility(View.VISIBLE);
                //Log.e("Document File ", documentFile.toString());
            }
        }
    }

    @Override
    public void onCancelClick() {
    }

    private void setRequestForBagRequest() {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("memberId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("amount", etRequestAmount.getText().toString().trim());
            param.addProperty("paymentmode", payMode);
            param.addProperty("transactionId", etTransactionNumber.getText().toString());
//            param.addProperty("RequestRemark", "");
            param.addProperty("paymentdate", etPaymentDate.getText().toString());
            LoggerUtil.logItem(param);
            showLoading();

            Call<JsonObject> call = apiServices.getNewWalletRequest(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseNewWalletRequest convertedObject = new Gson().fromJson(paramResponse, ResponseNewWalletRequest.class);
                            LoggerUtil.logItem(convertedObject);
                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                showMessage(convertedObject.getMessage());
                                uploadBagRequestFile(documentFile, convertedObject.getTransId());
                            } else {
                                showMessage(convertedObject.getResponse());
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(context, LoginActivity.class, null);
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

    private void uploadBagRequestFile(File file, String reqId) {
        try {
            showLoading();
            //creating request body for Profilefile
            RequestBody requestBody = null;
            if (file != null)
                requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            // TODO CHANGE ACTION AS PER REQUIREMENT
            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "SlipUpload");
            RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), reqId);
            MultipartBody.Part body = MultipartBody.Part.createFormData("File", file.getName(), requestBody);

            //creating a call and calling the upload image method
            ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
            Call<JsonObject> call = apiServices.uploadImage(String.valueOf(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent)), action, uniquenoBody, body, PreferencesManager.getInstance(context).getANDROIDID());
            //finally performing the call
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    hideLoading();
                    onBackPressed();
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    Log.e("***********", call.request().url().toString());
                    Log.e("***********", "= " + t.getMessage());
                    showMessage(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validation() {
        if (etRequestAmount.getText().toString().equalsIgnoreCase("")) {
            etRequestAmount.setError("Request amount cannot be empty.");
            requestFocus(etRequestAmount);
            return false;
        } else if (payMode.equalsIgnoreCase("")) {
            showMessage("Please choose any payment mode.");
            return false;
        } else if (etPaymentDate.getText().toString().equalsIgnoreCase("")) {
            showMessage("Please select payment date");
            return false;
        } else if (etTransactionNumber.getText().toString().equalsIgnoreCase("")) {
            showMessage("Please enter transaction number");
            requestFocus(etTransactionNumber);
            return false;
        } else if (etTransactionNumber.getText().toString().length() < 6) {
            showMessage("Invalid transaction number");
            requestFocus(etTransactionNumber);
            return false;
        } else if (documentFile == null) {
            showMessage("Please select file..");
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void getBankDetails() {
        showLoading();
        Call<JsonObject> jsonObjectCall = apiServices.getBankDetails();
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                LoggerUtil.logItem(response.code());
                try {

                    if (response.isSuccessful()) {
                        String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        LoggerUtil.logItem(call.request().url());
                        LoggerUtil.logItem(param);
                        Gson gson = new GsonBuilder().create();
                        ResponseBankDetails bankDetails = gson.fromJson(param, ResponseBankDetails.class);
                        if (bankDetails.getResponse().equalsIgnoreCase("success")) {
                            tvAccoNumber.setText(bankDetails.getResult().getAccountno());
                            tvBankName.setText(bankDetails.getResult().getBankname());
                            tvBranch.setText(bankDetails.getResult().getBranchname());
                            tvIfsc.setText(bankDetails.getResult().getIfsccode());
                            etCompanyName.setText(bankDetails.getResult().getAccountmembername());


                        }

                    } else {
                        showMessage("Something went wrong");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}
