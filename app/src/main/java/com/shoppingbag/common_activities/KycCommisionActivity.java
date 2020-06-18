package com.shoppingbag.common_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.referral.ResponseTodayReferral;
import com.shoppingbag.model.response.profile.ResponseProfileUpdate;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.FileUtils;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.mlm.fragments.SELECTION;
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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KycCommisionActivity extends BaseActivity implements IPickCancel, IPickResult {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.camera_front)
    ImageView camera_front;
    @BindView(R.id.camera_back)
    ImageView camera_back;
    @BindView(R.id.front_cam_constraint)
    ConstraintLayout front_cam_constraint;
    @BindView(R.id.rear_cam_constraint)
    ConstraintLayout rear_cam_constraint;
    private final String ID_PROOF_PIC_FRONT = "AddressFront";
    private final String ID_PROOF_PIC_BACK = "AddressBack";
    @BindView(R.id.account_holder_et)
    EditText accountHolderEt;
    @BindView(R.id.et_bank)
    EditText etBank;
    @BindView(R.id.account_no_et)
    EditText accountNoEt;
    @BindView(R.id.ifsc_et)
    EditText ifscEt;
    @BindView(R.id.branch_et)
    EditText branchEt;
    @BindView(R.id.pincode_et)
    EditText pincodeEt;
    @BindView(R.id.adhaar_et)
    EditText adhaarEt;
    @BindView(R.id.submit)
    Button submit;
    private String  aadhar_pic_front = "";
    private String  aadhar_pic_back = "";
    private SELECTION selection;
    private ProgressDialog pd;
    private File aadharFile_front, aadharFile_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_commission_layout);
        ButterKnife.bind(this);
        title.setText("KYC");

        sideMenu.setOnClickListener(v -> onBackPressed());
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

    @OnClick({R.id.front_cam_constraint, R.id.rear_cam_constraint, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.front_cam_constraint:
                selection = SELECTION.ID_PROOF_FRONT;
                showDialog();
                break;
            case R.id.rear_cam_constraint:
                selection = SELECTION.ID_PROOF_BACK;
                showDialog();
                break;
            case R.id.submit:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        updateBank();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
           /* case R.id.btnUploadDocuments:
                if ((!idProof_pic_front.equalsIgnoreCase("")) || (!idProof_pic_back.equalsIgnoreCase(""))) {
                    if (!txtAddressType.getText().toString().trim().equalsIgnoreCase("")) {
                        if (!addressIdNo.getText().toString().trim().equalsIgnoreCase("")) {
                            uploadDocumentNo(PAN_PIC);
                        } else {
                            showMessage("Please enter " + txtAddressType.getText().toString() + " number");
                        }
                    } else {
                        showMessage("Please select address proof type");
                    }

                } else if (!etPanCard.getText().toString().trim().equalsIgnoreCase("")) {
                    uploadDocumentNo(PAN_PIC);
                } else {
                    showMessage("Please enter PAN number");
                }
                break;*/
        }
    }

    private void showDialog() {
        PickSetup pickSetup = new PickSetup();
        switch (selection) {
            case ID_PROOF_FRONT:
                pickSetup.setTitle("Choose Aadhar Front Pic");
                break;
            case ID_PROOF_BACK:
                pickSetup.setTitle("Choose Aadhar Back Pic");
                break;
        }

        pickSetup.setGalleryIcon(R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);

        // If show system dialog..
        // pickSetup.setSystemDialog(true);

        PickImageDialog dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(this);
    }

    @Override
    public void onCancelClick() {
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        //Log.e("RESULT", "= " + pickResult.getPath());
        if (pickResult.getError() == null) {
            switch (selection) {
                case ID_PROOF_FRONT:
                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(16, 9)
                            .start(context);
                    break;
                case ID_PROOF_BACK:
                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(16, 9)
                            .start(context);
                    break;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("PRofile File ", "KYC Activity");
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            switch (selection) {
                case ID_PROOF_FRONT:
                    if (resultCode == RESULT_OK) {
                        aadharFile_front = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(camera_front);

                        aadhar_pic_front = "AdharFront";
                        //Log.e("idProof_pic_front ", ID_ProofFile_front.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;
                case ID_PROOF_BACK:
                    if (resultCode == RESULT_OK) {
                        aadharFile_back = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(camera_back);
                        aadhar_pic_back = "AdharBack";
                        //Log.e("idProof_pic_back ", ID_ProofFile_back.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;
            }

        }
    }

    private boolean Validation() {
        if (accountHolderEt.getText().toString().trim().length() < 2) {
            accountHolderEt.setError("Enter account holder name");
            return false;
        } else if (etBank.getText().toString().trim().length() < 2) {
            etBank.setError("Enter Bank name");
            return false;
        } else if (accountNoEt.getText().toString().trim().length() < 9) {
            accountNoEt.setError("Enter valid account number");
            return false;
        }else if (ifscEt.getText().toString().trim().length() < 11) {
            ifscEt.setError("Enter IFSC");
            return false;
        } else if (branchEt.getText().toString().trim().length() < 2) {
            branchEt.setError("Enter Branch name");
            return false;
        } else if (pincodeEt.getText().toString().trim().length() < 6) {
            pincodeEt.setError("Enter Pincode");
            return false;
        } else if (adhaarEt.getText().toString().trim().length() < 12) {
            adhaarEt.setError("Enter valid Aadhar number");
            return false;
        }else if (aadharFile_front==null) {
           showMessage("Please upload aadhar front pic");
            return false;
        }else if (aadharFile_back==null) {
            showMessage("Please upload aadhar back pic");
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
    private void updateBank() {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("fk_memId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("memberAccNo", accountNoEt.getText().toString());
            param.addProperty("memberBankName", etBank.getText().toString());
            param.addProperty("memberBranch", branchEt.getText().toString());
            param.addProperty("bankHolderName", accountHolderEt.getText().toString());
            param.addProperty("ifscCode", ifscEt.getText().toString());
            param.addProperty("pincode", pincodeEt.getText().toString());
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
                                if (aadharFile_front != null) {
                                    showProgressDialog();
                                    uploadFile(aadharFile_front, aadhar_pic_front, adhaarEt.getText().toString().trim());
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
    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Upload Documents...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.show();
    }
    private void uploadFile(File file, final String type, String uniqueno) {
        try {
            //Type:-'Kyc'
            //creating request body for Profile file
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), uniqueno);
            RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), type);
            RequestBody Type = RequestBody.create(MediaType.parse("text/plain"), "Kyc");
            MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
            //creating our api
            ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
            //creating a call and calling the upload image method
            Call<JsonObject> call = apiServices.uploadImageKYC(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), imgType,Type, uniquenoBody, body, PreferencesManager.getInstance(this).getANDROIDID());
            //finally performing the call
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    Log.e("***********", call.request().url().toString());
                    if (aadharFile_back != null) {
                        uploadFileBack(aadharFile_back, aadhar_pic_back, adhaarEt.getText().toString().trim());
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
    } private void uploadFileBack(File file, final String type, String uniqueno) {
        try {
            //creating request body for Profile file
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), uniqueno);
            RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), type);
            RequestBody Type = RequestBody.create(MediaType.parse("text/plain"), "Kyc");
            MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
            //creating our api
            ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
            //creating a call and calling the upload image method
            Call<JsonObject> call = apiServices.uploadImageKYC(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), imgType,Type, uniquenoBody, body, PreferencesManager.getInstance(this).getANDROIDID());
            //finally performing the call
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    Log.e("***********", call.request().url().toString());
                    pd.dismiss();
                    finish();

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
    /* private void uploadDocumentNo(String DOC_TYPE) {
         //Log.e("UPLOAD", DOC_TYPE);
         switch (DOC_TYPE) {
             case PAN_PIC:
                 showProgressDialog();
                 if (!pan_pic.equalsIgnoreCase("")) {
                     uploadFile(PANFile, PAN_PIC, etPanCard.getText().toString().trim());
                     pan_pic = "";
                 } else if (!idProof_pic_front.equalsIgnoreCase("")) {
                     uploadFile(ID_ProofFile_front, ID_PROOF_PIC_FRONT, addressIdNo.getText().toString());
                     idProof_pic_front = "";
                 } else if (!idProof_pic_back.equalsIgnoreCase("")) {
                     uploadFile(ID_ProofFile_back, ID_PROOF_PIC_BACK, addressIdNo.getText().toString());
                     idProof_pic_back = "";
                 } else {
                     pd.dismiss();
 //                    getUserProfile();
                 }
                 break;
             case ID_PROOF_PIC_FRONT:
                 if (!idProof_pic_front.equalsIgnoreCase("")) {
                     uploadFile(ID_ProofFile_front, ID_PROOF_PIC_FRONT, addressIdNo.getText().toString());
                     idProof_pic_front = "";
                 } else if (!idProof_pic_back.equalsIgnoreCase("")) {
                     uploadFile(ID_ProofFile_back, ID_PROOF_PIC_BACK, addressIdNo.getText().toString());
                     idProof_pic_back = "";
                 } else {
                     showMessage("KYC Update Successfully");
                     pd.dismiss();
                     getUserProfile();
                 }
                 break;
             case ID_PROOF_PIC_BACK:
                 pd.setTitle("Finishing...");
                 if (!idProof_pic_back.equalsIgnoreCase("")) {
                     uploadFile(ID_ProofFile_back, ID_PROOF_PIC_BACK, addressIdNo.getText().toString());
                     idProof_pic_back = "";
                 } else {
                     showMessage("KYC Update Successfully");
                     pd.dismiss();
                     getUserProfile();
                 }
                 break;
         }
     }

     private void uploadFile(File file, final String uploadDocument, String uniqueno) {
         try {
             //Log.e("***********", "file is : " + file.length());
             //Log.e("***********", "uploadDocument is " + uploadDocument);

             //creating request body for Profile file
             RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
             RequestBody uniquenoBody = RequestBody.create(MediaType.parse("text/plain"), uniqueno);
             RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), uploadDocument);
             MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
             //creating our api
             ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
             //creating a call and calling the upload image method
             Call<JsonObject> call = apiServices.uploadImage(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent),  imgType, uniquenoBody, body, PreferencesManager.getInstance(getActivity()).getANDROIDID());
             //finally performing the call
             call.enqueue(new Callback<JsonObject>() {
                 @Override
                 public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                     LoggerUtil.logItem(response.body());
                     //Log.e("***********", call.request().url().toString());
                     switch (uploadDocument) {
                         case PAN_PIC:
                             if (pan_pic.equalsIgnoreCase("")) {
                                 uploadDocumentNo(ID_PROOF_PIC_FRONT);
                             } else {
                                 uploadDocumentNo(PAN_PIC);
                             }
                             break;
                         case ID_PROOF_PIC_FRONT:
                             uploadDocumentNo(ID_PROOF_PIC_BACK);
                             break;
                         case ID_PROOF_PIC_BACK:
                             // Complete...
                             showMessage("KYC Update Successfully");
                             pd.dismiss();
                             getUserProfile();
                             break;
                     }
                 }

                 @Override
                 public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                     showMessage(t.getMessage());
                 }
             });
         } catch (Exception e) {
             e.printStackTrace();
         }
     }*/


}

