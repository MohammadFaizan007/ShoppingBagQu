package com.shoppingbag.mlm.fragments.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lid.lib.LabelImageView;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.constants.UrlConstants;
import com.shoppingbag.mlm.fragments.SELECTION;
import com.shoppingbag.model.response.profile.ResponseProfileUpdate;
import com.shoppingbag.model.response.profile.ResponseViewProfile;
import com.shoppingbag.model.response.profile.Result;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.FileUtils;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditBankDetails extends BaseFragment implements IPickCancel, IPickResult {
    private final String BLANK_CHEQUE_PIC = "Bank";
    @BindView(R.id.account_holder_et)
    EditText accountHolderEt;
    @BindView(R.id.account_no_et)
    EditText accountNoEt;
    @BindView(R.id.ifsc_et)
    EditText ifscEt;
    @BindView(R.id.et_bank)
    EditText etBank;
    @BindView(R.id.branch_et)
    EditText branchEt;
    @BindView(R.id.submit)
    Button submit;
    Unbinder unbinder;
    @BindView(R.id.imgAddressFront)
    LabelImageView imgbankDetail;
    boolean approved = false;
    private File Chequefile;
    private ProgressDialog pd;
    private SELECTION selection;
    private PickImageDialog dialog;

    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Upload Documents...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.show();
    }

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        switch (selection) {
            case CHEQUE_DETAIL:
                pickSetup.setTitle("Choose Photocopy of Cheque");
                break;

        }

        pickSetup.setGalleryIcon(R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);

        // If show system dialog..
        // pickSetup.setSystemDialog(true);

        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_bank_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        setData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (Validation()) {
            if (NetworkUtils.getConnectivityStatus(context) != 0) {
                updateBank();
            } else {
                showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
            }
        }
    }

    private void setData() {
        try {
            Result profile = UrlConstants.profile.getResult();
            accountHolderEt.setText(profile.getFirstName() + " " + profile.getLastName());
            accountNoEt.setText(profile.getMemberAccNo());
            ifscEt.setText(profile.getIfscCode());
            etBank.setText(profile.getMemberBankName());
            branchEt.setText(profile.getMemberBranch());

            if (UrlConstants.profile.getResult().getBankStatus() == 0) {
                Glide.with(context).load(UrlConstants.profile.getResult().getBankDocumentURL().replace("~", BuildConfig.BASE_URL_FORIMAGE)).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .placeholder(R.drawable.camera_upload)
                                .error(R.drawable.camera_upload))
                        .into(imgbankDetail);

                imgbankDetail.setOnClickListener(v -> {
                    selection = SELECTION.CHEQUE_DETAIL;
                    showDialog();
                });
            } else {
                Glide.with(context).load(UrlConstants.profile.getResult().getBankDocumentURL().replace("~", BuildConfig.BASE_URL_FORIMAGE)).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .placeholder(R.drawable.camera_upload)
                                .error(R.drawable.camera_upload))
                        .into(imgbankDetail);
            }

            if (UrlConstants.profile.getResult().getBankStatus() == 0) {
                imgbankDetail.setLabelText("Pending");
                imgbankDetail.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.orange));
                approved = false;
                imgbankDetail.setClickable(true);
            } else {
                approved = true;
                imgbankDetail.setClickable(false);
                imgbankDetail.setLabelText("Approved");
                imgbankDetail.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.green));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (approved) {
            submit.setVisibility(View.GONE);
        } else {
            ifscEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() >= 10) {
                        getBankName(s.toString());
                    } else {
                        etBank.setText("");
                        branchEt.setText("");
                    }
                }
            });
        }
    }
//    public long fk_memId { get; set; }
//    public string bankAccName { get; set; }
//    public string memberBankName { get; set; }
//    public string memberBranch { get; set; }
//    public string memberAccNo { get; set; }
//    public string bankHolderName { get; set; }
//    public string ifscCode { get; set; }
//    public string relationWithApplicant { get; set; }

    private void updateBank() {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("fk_memId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("memberAccNo", accountNoEt.getText().toString());
            param.addProperty("memberBankName", etBank.getText().toString());
            param.addProperty("memberBranch", branchEt.getText().toString());
            param.addProperty("bankHolderName", accountHolderEt.getText().toString());
            param.addProperty("ifscCode", ifscEt.getText().toString());
            param.addProperty("relationWithApplicant", "");

            LoggerUtil.logItem(param);
            showLoading();

            Call<JsonObject> call = apiServices.getBankUpdate(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseProfileUpdate convertedObject = new Gson().fromJson(paramResponse, ResponseProfileUpdate.class);
                            LoggerUtil.logItem(convertedObject);

                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                showMessage("Updated Successfully");
                                if (Chequefile != null) {
                                    showProgressDialog();
                                    uploadFile(Chequefile, BLANK_CHEQUE_PIC, "");
                                }
                            } else {
                                showMessage(convertedObject.getResponse());
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
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

    private boolean Validation() {
        if (accountHolderEt.getText().toString().trim().length() < 2) {
            accountHolderEt.setError("Enter name");
            return false;
        } else if (accountNoEt.getText().toString().trim().length() < 9) {
            accountNoEt.setError("Enter valid account number");
            return false;
        } else if (etBank.getText().toString().trim().length() < 2) {
            etBank.setError("Enter Bank name");
            return false;
        } else if (branchEt.getText().toString().trim().length() < 2) {
            branchEt.setError("Enter Branch name");
            return false;
        } else if (ifscEt.getText().toString().trim().length() < 11) {
            ifscEt.setError("Enter IFSC");
            return false;
        }
        return true;
    }

    private void getBankName(String ifscCode) {
        try {
            String url = "https://ifsc.razorpay.com/" + ifscCode;
            Call<JsonObject> getCity = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
            getCity.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    if (response.body() != null) {
                        ////Log.e("Response", response.body().toString());
                        try {
                            etBank.setText(response.body().get("BANK").getAsString());
                            branchEt.setText(response.body().get("BRANCH").getAsString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelClick() {
        dialog.dismiss();
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        ////Log.e("RESULT", "= " + pickResult.getPath());
        if (pickResult.getError() == null) {
            switch (selection) {
                case CHEQUE_DETAIL:
                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(16, 9)
                            .start(context);
                    break;
            }
        } else {
            ////Log.e("RESULT", "ERROR = " + pickResult.getError());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////Log.e("Cheque File ", "Bank Fragment");
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            switch (selection) {
                case CHEQUE_DETAIL:
                    if (resultCode == RESULT_OK) {
                        Chequefile = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(imgbankDetail);
                        ////Log.e("BANK DETAIL File ", Chequefile.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;

            }
        }
    }

    private void uploadFile(File file, final String type, String uniqueno) {
        try {
            //creating request body for Profile file
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), uniqueno);
            RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), type);
            MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
            //creating our api
            ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
            //creating a call and calling the upload image method
            Call<JsonObject> call = apiServices.uploadImage(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), imgType, uniquenoBody, body, PreferencesManager.getInstance(getActivity()).getANDROIDID());
            //finally performing the call
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    ////Log.e("***********", call.request().url().toString());
                    switch (type) {
                        case BLANK_CHEQUE_PIC:
                            pd.dismiss();
                            getUserProfile();
                            break;
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    ////Log.e("***********", call.request().url().toString());
                    ////Log.e("***********", "= " + t.getMessage());
                    ////Log.e("***********", "= " + t.getLocalizedMessage());
                    showMessage(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserProfile() {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_MLM + "ViewProfile?memberId=" + PreferencesManager.getInstance(context).getUSERID();
            LoggerUtil.logItem(url);
            Call<JsonObject> call = apiServices.getProfile(url, AppConfig.ANDROIDID);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseViewProfile convertedObject = new Gson().fromJson(paramResponse, ResponseViewProfile.class);
                            LoggerUtil.logItem(convertedObject);
                            if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                UrlConstants.profile = convertedObject;
                            }
                        } else {
                            clearPrefrenceforTokenExpiry();
                            getAndroidId();
                            goToActivityWithFinish(LoginActivity.class, null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                    LoggerUtil.logItem(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
