package com.shoppingbag.mlm.fragments.profile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.constants.UrlConstants;
import com.shoppingbag.mlm.fragments.SELECTION;
import com.shoppingbag.model.response.pincode.ResponsePincode;
import com.shoppingbag.model.response.profile.ResponseProfileUpdate;
import com.shoppingbag.model.response.profile.ResponseViewProfile;
import com.shoppingbag.model.response.profile.Result;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.FileUtils;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.utils.Utils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

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

public class EditPersonalDetails extends BaseFragment implements IPickResult, IPickCancel {

    private final String PROFILE_PIC = "Profile";
    @BindView(R.id.first_name_et)
    EditText firstNameEt;
    @BindView(R.id.last_name_et)
    EditText lastNameEt;
    @BindView(R.id.dob_et)
    EditText dobEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.gender_et)
    EditText genderEt;
    @BindView(R.id.father_name_et)
    EditText fatherNameEt;
    @BindView(R.id.et_nominee_name)
    EditText etNomineeName;
    @BindView(R.id.et_nominee_relation)
    EditText etNomineeRelation;
    @BindView(R.id.marital_status)
    EditText maritalStatus;
    @BindView(R.id.address_et)
    EditText addressEt;
    @BindView(R.id.pin_code_et)
    EditText pinCodeEt;
    @BindView(R.id.city_et)
    TextInputEditText cityEt;
    @BindView(R.id.state_et)
    TextInputEditText stateEt;
    @BindView(R.id.mobile_et)
    EditText mobile_et;
    @BindView(R.id.aadhar_et)
    EditText aadhar_et;
    @BindView(R.id.pan_et)
    EditText panEt;
    @BindView(R.id.submit)
    Button submit;
    Unbinder unbinder;
    @BindView(R.id.imgProfle)
    ImageView imgProfle;
    @BindView(R.id.address_et2)
    EditText address_et2;
    @BindView(R.id.pincodeProgress)
    ProgressBar pincodeProgress;
    private ProgressDialog pd;
    private File Profilefile;

    private String panCard = "";
    private SELECTION selection;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_personal_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        pinCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && s.length() == 6) {
                    getStateCityName(pinCodeEt.getText().toString().trim());
                } else {
                    cityEt.setText("");
                    stateEt.setText("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        setData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.dob_et, R.id.gender_et, R.id.marital_status, R.id.submit, R.id.imgProfle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dob_et:
                datePicker(dobEt);
                break;
            case R.id.gender_et:
                getPopUpForGender();
                break;
            case R.id.marital_status:
                getPopUpMaritalStatus();
                break;
            case R.id.imgProfle:
                selection = SELECTION.PROFILE_PIC;
                showDialog();
                break;
            case R.id.submit:
                if (validate()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        getPersonalUpdate();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
        }
    }

    private void showDialog() {
        PickSetup pickSetup = new PickSetup();
        switch (selection) {
            case PROFILE_PIC:
                pickSetup.setTitle("Choose Profile Pic");
                break;
        }

        pickSetup.setGalleryIcon(R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);

        PickImageDialog dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getFragmentManager());
    }


    private void setData() {
        try {
            Result profile = UrlConstants.profile.getResult();

            panCard = profile.getPancard();

            if (!TextUtils.isEmpty(panCard) && panCard.length() == 10) {
                panEt.setText(String.format("xxxxxx%s", profile.getPancard().substring(profile.getPancard().length() - 4)));
                panEt.setClickable(false);
                panEt.setFocusable(false);
                panEt.setFocusableInTouchMode(false);
                panEt.setCursorVisible(false);
            } else {
                panEt.setText(panCard);
            }

            firstNameEt.setText(profile.getFirstName());
            lastNameEt.setText(profile.getLastName());
            if (profile.getDob() != null && !profile.getDob().equalsIgnoreCase("NA"))
                dobEt.setText(profile.getDob());
            emailEt.setText(profile.getEmail());
            genderEt.setText(profile.getGender());
            fatherNameEt.setText(profile.getFatherName());
            etNomineeName.setText(profile.getNomineeName());
            etNomineeRelation.setText(profile.getRelationWithApplicant());
            maritalStatus.setText(profile.getMarritalStatus());
            addressEt.setText(String.format("%s", profile.getAddress1()));
            address_et2.setText(String.format("%s%s", profile.getAddress2(), profile.getAddress3()));
            pinCodeEt.setText(profile.getPinCode());
            cityEt.setText(profile.getCity());
            stateEt.setText(profile.getState());
            mobile_et.setText(profile.getMobile());
            aadhar_et.setText(profile.getAadhar());

            if (UrlConstants.profile.getResult().getProfilepic() == null) {
                Glide.with(context).load(R.drawable.camera_upload).into(imgProfle);
            } else {
                Log.e("==========", "===============================" + BuildConfig.BASE_URL_FORIMAGE + UrlConstants.profile.getResult().getProfilepic());
                Glide.with(context).load(UrlConstants.profile.getResult().getProfilepic().replace("~", BuildConfig.BASE_URL_FORIMAGE)).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .placeholder(R.mipmap.ic_launcher_foreground)
                                .error(R.mipmap.ic_launcher_foreground))
                        .into(imgProfle);
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void getPopUpForGender() {
        PopupMenu popupg = new PopupMenu(context, genderEt);
        popupg.getMenuInflater().inflate(R.menu.menu_gender, popupg.getMenu());
        popupg.setOnMenuItemClickListener(item -> {
            try {
                genderEt.setText(item.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
        popupg.show();
    }

    private void getPopUpMaritalStatus() {
        PopupMenu popupg = new PopupMenu(context, maritalStatus);
        popupg.getMenuInflater().inflate(R.menu.menu_marital_status, popupg.getMenu());
        popupg.setOnMenuItemClickListener(item -> {
            try {
                maritalStatus.setText(item.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
        popupg.show();
    }

    private void datePicker(final EditText et) {
        int mYear, mMonth, mDay;
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogTheme,
                (view, year, monthOfYear, dayOfMonth) ->
                        et.setText(String.format(Locale.ENGLISH, "%d/%d/%d", dayOfMonth, monthOfYear + 1, year))
                , mYear, mMonth, mDay);
        cal.add(Calendar.YEAR, -18);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    private boolean validate() {
        if (firstNameEt.getText().toString().trim().length() < 2) {
            firstNameEt.setError("Enter name");
            return false;
        } else if (dobEt.getText().toString().trim().length() == 0) {
            dobEt.setError("Date of birth can not be blank.");
            return false;
        } else if (TextUtils.isEmpty(pinCodeEt.getText().toString().trim()) && pinCodeEt.getText().toString().trim().length() < 6) {
            pinCodeEt.setError("Invalid pin code");
            pinCodeEt.requestFocus();
            return false;
        } else if (!panEt.getText().toString().startsWith("xxx") && !Utils.validatePan(panEt.getText().toString().trim())) {
            panEt.setError("Invalid PAN number");
            panEt.requestFocus();
            return false;
        } else if (stateEt.getText().toString().trim().length() == 0) {
            stateEt.setError("Please enter a valid state");
            stateEt.requestFocus();
            return false;
        } else if (cityEt.getText().toString().trim().length() == 0) {
            cityEt.setError("Please enter a valid city");
            cityEt.requestFocus();
            return false;
        } /*else if (TextUtils.isEmpty(panEt.getText().toString())) {
            panEt.setError("Invalid PAN number");
            panEt.requestFocus();
            return false;
        }*/
        return true;
    }

    private void getPersonalUpdate() {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("dob", dobEt.getText().toString());
            param.addProperty("gender", genderEt.getText().toString().trim());
            param.addProperty("fatherName", fatherNameEt.getText().toString().trim());
            param.addProperty("nomineeName", etNomineeName.getText().toString().trim());
            param.addProperty("relationWithApplicant", etNomineeRelation.getText().toString().trim());
            param.addProperty("mobile", Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent));
            param.addProperty("firstName", firstNameEt.getText().toString().trim());
            param.addProperty("lastName", lastNameEt.getText().toString().trim());
            param.addProperty("marritalStatus", maritalStatus.getText().toString());
            param.addProperty("email", emailEt.getText().toString());
            param.addProperty("address1", addressEt.getText().toString().trim());
            param.addProperty("address2", address_et2.getText().toString().trim());
            param.addProperty("address3", "");
            param.addProperty("pinCode", pinCodeEt.getText().toString());
            param.addProperty("city", cityEt.getText().toString());
            param.addProperty("state", stateEt.getText().toString());
            param.addProperty("tehsil", "");
            param.addProperty("aadhar", aadhar_et.getText().toString().trim());

            if (panEt.getText().toString().trim().startsWith("xxx")) {
                param.addProperty("pancard", panCard);
            } else {
                param.addProperty("pancard", panEt.getText().toString().trim());
            }


            LoggerUtil.logItem(param);
            showLoading();
            Call<JsonObject> call = apiServices.getPersonalUpdate(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    try {
                        hideLoading();
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseProfileUpdate convertedObject = new Gson().fromJson(paramResponse, ResponseProfileUpdate.class);
                            LoggerUtil.logItem(convertedObject);

                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                showMessage("Details Updated Successfully");
//                                if (Profilefile != null) {
//                                    uploadFile(Profilefile, PROFILE_PIC, "");
//                                } else {
//                                    getUserProfile();
//                                }

                                getUserProfile();

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

    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Upload Profile Pic...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.show();
    }

    private void uploadFile(File file, final String uploadDocument, String uniqueno) {
        try {
            //Log.e("***********", "file is : " + file.length());
            //Log.e("***********", "uploadDocument is " + uploadDocument);
            showProgressDialog();
            //creating request body for Profile file
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), uniqueno);
            RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), uploadDocument);
            MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
            //creating our api
            ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
            //creating a call and calling the upload image method
            Call<JsonObject> call = apiServices.uploadImage(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), imgType, uniquenoBody, body, PreferencesManager.getInstance(getActivity()).getANDROIDID());
            //finally performing the call
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem("IIIIIIIMMMMMM" + response.body());
                    LoggerUtil.logItem(call.request().url().toString());
                    //Log.e("***********", call.request().url().toString());
                    pd.dismiss();
                    getUserProfile();
//
                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    Log.e("***********", call.request().url().toString());
                    Log.e("***********", "= " + t.getMessage());
                    Log.e("***********", "= " + t.getLocalizedMessage());
                    showMessage(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getStateCityName(String pincode) {
        try {
            pincodeProgress.setVisibility(View.VISIBLE);
            String url = BuildConfig.PINCODEURL + Cons.encryptMsg(pincode, cross_intent);
            LoggerUtil.logItem(url);
            Call<JsonObject> getCity = apiServicesLogin.getStateCity(url, PreferencesManager.getInstance(context).getANDROIDID());
            getCity.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());

                    try {
                        pincodeProgress.setVisibility(View.GONE);
                        String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                        LoggerUtil.logItem(paramResponse);
                        Gson gson = new GsonBuilder().create();
                        ResponsePincode responseRequestOTP = gson.fromJson(paramResponse, ResponsePincode.class);
                        if (response.body() != null && responseRequestOTP.getResponse().equalsIgnoreCase("Success")) {
                            cityEt.setText(responseRequestOTP.getResult().get(0).getDistrictName());
                            stateEt.setText(responseRequestOTP.getResult().get(0).getStateName());
                        } else {
                            cityEt.setText("");
                            stateEt.setText("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        cityEt.setText("");
                        stateEt.setText("");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Profilefile = FileUtils.getFile(context, result.getUri());
                    Glide.with(context).load(result.getUri()).
                            apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                    .placeholder(R.mipmap.ic_launcher_foreground)
                                    .error(R.mipmap.ic_launcher_foreground))
                            .into(imgProfle);
                    uploadFile(Profilefile, PROFILE_PIC, "");


                    break;
                }
            }
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
                                PreferencesManager.getInstance(context).setMOBILE(Cons.encryptMsg(UrlConstants.profile.getResult().getMobile(), cross_intent));
                                PreferencesManager.getInstance(context).setNAME(Cons.encryptMsg(UrlConstants.profile.getResult().getFirstName(), cross_intent));
                                PreferencesManager.getInstance(context).setLNAME(Cons.encryptMsg(UrlConstants.profile.getResult().getLastName(), cross_intent));
                                PreferencesManager.getInstance(context).setPINCODE(Cons.encryptMsg(UrlConstants.profile.getResult().getPinCode(), cross_intent));
                                PreferencesManager.getInstance(context).setState(Cons.encryptMsg(UrlConstants.profile.getResult().getState(), cross_intent));
                                PreferencesManager.getInstance(context).setCity(Cons.encryptMsg(UrlConstants.profile.getResult().getCity(), cross_intent));
                                PreferencesManager.getInstance(context).setAddress(Cons.encryptMsg(UrlConstants.profile.getResult().getAddress1(), cross_intent));
                                PreferencesManager.getInstance(context).setEMAIL(Cons.encryptMsg(UrlConstants.profile.getResult().getEmail(), cross_intent));
                                PreferencesManager.getInstance(context).setDOB(Cons.encryptMsg(UrlConstants.profile.getResult().getDob(), cross_intent));
                                PreferencesManager.getInstance(context).setPROFILEPIC(UrlConstants.profile.getResult().getProfilepic());

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


    @Override
    public void onCancelClick() {

    }

    @Override
    public void onPickResult(PickResult pickResult) {
        CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1, 1)
                .start(context);
    }
}
