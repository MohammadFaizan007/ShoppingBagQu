package com.shoppingbag.mlm.fragments.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
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
import com.shoppingbag.model.response.profile.ResponseViewProfile;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.FileUtils;
import com.shoppingbag.utils.LoggerUtil;
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

public class EditKycDetail extends BaseFragment implements IPickCancel, IPickResult {

    private final String PAN_PIC = "Pan";
    private final String ID_PROOF_PIC_FRONT = "AddressFront";
    private final String ID_PROOF_PIC_BACK = "AddressBack";

    Unbinder unbinder;
    @BindView(R.id.imgPanCard)
    LabelImageView imgPanCard;

    @BindView(R.id.imgAddressFront)
    LabelImageView imgAddressFront;

    @BindView(R.id.imgAddressBack)
    LabelImageView imgAddressBack;

    @BindView(R.id.txtAddressType)
    TextView txtAddressType;

    @BindView(R.id.address_id_no)
    EditText addressIdNo;

    @BindView(R.id.btnUploadDocuments)
    Button btnUploadDocuments;
    @BindView(R.id.et_pan_card)
    EditText etPanCard;
    boolean approvePan = false;
    boolean approveAddress = false;
    private ProgressDialog pd;
    private String pan_pic = "";
    private String idProof_pic_front = "";
    private String idProof_pic_back = "";
    private SELECTION selection;
    private File PANFile, ID_ProofFile_front, ID_ProofFile_back;

    private void showProgressDialog() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Upload Documents...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    public void onCancelClick() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_kyc, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void showDialog() {
        PickSetup pickSetup = new PickSetup();
        switch (selection) {
            case PAN:
                pickSetup.setTitle("Choose PAN");
                break;
            case ID_PROOF_FRONT:
                pickSetup.setTitle("Choose Address Proof");
                break;
            case ID_PROOF_BACK:
                pickSetup.setTitle("Choose Address");
                break;
        }

        pickSetup.setGalleryIcon(R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);

        // If show system dialog..
        // pickSetup.setSystemDialog(true);

        PickImageDialog dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getFragmentManager());
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        try {
            String pan_status, address_status;
            String pan_number = UrlConstants.profile.getResult().getPancard();
            String address_number = UrlConstants.profile.getResult().getAddressProofNo();
            etPanCard.setText(UrlConstants.profile.getResult().getPancard());
            txtAddressType.setText(UrlConstants.profile.getResult().getAddressProofType());
            if (UrlConstants.profile.getResult().getAddressProofType().equalsIgnoreCase("Aadhar")) {
                if (!TextUtils.isEmpty(UrlConstants.profile.getResult().getAddressProofType())) {
                    addressIdNo.setText(String.format("xxxxxxxx%s", UrlConstants.profile.getResult().getAddressProofNo().substring(UrlConstants.profile.getResult().getAddressProofNo().length() - 4)));
                }
            } else {
                addressIdNo.setText(UrlConstants.profile.getResult().getAddressProofNo());
            }

            //pan
            if (UrlConstants.profile.getResult().getPanCardURL() == null ||
                    UrlConstants.profile.getResult().getPanCardURL().equalsIgnoreCase("")) {
                Glide.with(context).load(R.drawable.camera_upload).into(imgPanCard);
            } else {
                Glide.with(context).load(UrlConstants.profile.getResult().getPanCardURL().replace("~", BuildConfig.BASE_URL_FORIMAGE)).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .placeholder(R.drawable.camera_upload)
                                .error(R.drawable.camera_upload))
                        .into(imgPanCard);
            }
            //id proof front
            if (UrlConstants.profile.getResult().getAddressFrontUrl() == null ||
                    UrlConstants.profile.getResult().getAddressFrontUrl().equalsIgnoreCase("")) {
                Glide.with(context).load(R.drawable.camera_upload).into(imgAddressFront);
            } else {
                Glide.with(context).load(UrlConstants.profile.getResult().getAddressFrontUrl().replace("~", BuildConfig.BASE_URL_FORIMAGE)).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .placeholder(R.drawable.camera_upload)
                                .error(R.drawable.camera_upload))
                        .into(imgAddressFront);
            }
            //id proof back
            if (UrlConstants.profile.getResult().getAddressBackUrl() == null ||
                    UrlConstants.profile.getResult().getAddressBackUrl().equalsIgnoreCase("")) {
                Glide.with(context).load(R.drawable.camera_upload).into(imgAddressBack);
            } else {
                Glide.with(context).load(UrlConstants.profile.getResult().getAddressBackUrl().replace("~", BuildConfig.BASE_URL_FORIMAGE)).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .placeholder(R.drawable.camera_upload)
                                .error(R.drawable.camera_upload))
                        .into(imgAddressBack);
            }
            if (UrlConstants.profile.getResult().getPanStatus() == 0 ||
                    UrlConstants.profile.getResult().getAddressStatus() == 0) {
                pan_status = "Pending";
                imgPanCard.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.orange));
                approvePan = false;
                imgPanCard.setClickable(true);
            } else {
                pan_status = "Approved";
                approvePan = true;
                imgPanCard.setClickable(false);
                imgPanCard.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.green));
                imgPanCard.setClickable(false);
            }

            if (UrlConstants.profile.getResult().getAddressStatus() == 0) {
                address_status = "Pending";
                imgAddressFront.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.orange));
                imgAddressBack.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.orange));
                approveAddress = false;
                imgAddressFront.setClickable(true);
                imgAddressBack.setClickable(true);
            } else {
                address_status = "Approved";
                approveAddress = true;

                imgAddressFront.setClickable(false);
                imgAddressFront.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.green));
                imgAddressFront.setClickable(false);

                imgAddressBack.setClickable(false);
                imgAddressBack.setLabelBackgroundColor(ContextCompat.getColor(context, R.color.green));
                imgAddressBack.setClickable(false);
            }

            imgPanCard.setLabelText(pan_status);
            imgAddressFront.setLabelText(address_status);
            imgAddressBack.setLabelText(address_status);

            if (approvePan && approveAddress) {
                btnUploadDocuments.setVisibility(View.GONE);
                txtAddressType.setClickable(false);
                disableView(false, addressIdNo);
                disableView(false, etPanCard);
            }


        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    private void disableView(boolean bool, EditText view) {
        view.setClickable(bool);
        view.setFocusable(bool);
        view.setFocusableInTouchMode(bool);
        view.setCursorVisible(bool);
    }


    @OnClick({R.id.imgPanCard, R.id.imgAddressFront, R.id.imgAddressBack, R.id.btnUploadDocuments, R.id.txtAddressType})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgPanCard:
                selection = SELECTION.PAN;
                showDialog();
                break;
            case R.id.imgAddressFront:
                selection = SELECTION.ID_PROOF_FRONT;
                showDialog();
                break;
            case R.id.imgAddressBack:
                selection = SELECTION.ID_PROOF_BACK;
                showDialog();
                break;
            case R.id.txtAddressType:
                PopupMenu popup = new PopupMenu(context, txtAddressType);
                popup.getMenuInflater().inflate(R.menu.menu_address_type, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    txtAddressType.setText(item.getTitle());
                    return true;
                });
                popup.show();
                break;
            case R.id.btnUploadDocuments:
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
                break;
        }
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        //Log.e("RESULT", "= " + pickResult.getPath());
        if (pickResult.getError() == null) {
            switch (selection) {
                case PAN:
                    CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setAspectRatio(16, 9)
                            .start(context);
                    break;
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
                case PAN:
                    if (resultCode == RESULT_OK) {
                        PANFile = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(imgPanCard);

                        pan_pic = "Pan";
                        //Log.e("PANFile ", PANFile.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;
                case ID_PROOF_FRONT:
                    if (resultCode == RESULT_OK) {
                        ID_ProofFile_front = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(imgAddressFront);

                        idProof_pic_front = "Aadhar";
                        //Log.e("idProof_pic_front ", ID_ProofFile_front.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;
                case ID_PROOF_BACK:
                    if (resultCode == RESULT_OK) {
                        ID_ProofFile_back = FileUtils.getFile(context, result.getUri());
                        Glide.with(context).load(result.getUri()).
                                apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                        .placeholder(R.mipmap.ic_launcher_foreground)
                                        .error(R.mipmap.ic_launcher_foreground))
                                .into(imgAddressBack);
                        idProof_pic_back = "Aadhar";
                        //Log.e("idProof_pic_back ", ID_ProofFile_back.getAbsolutePath());
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                        LoggerUtil.logItem(error.getMessage());
                    }
                    break;
            }

        }
    }


    private void uploadDocumentNo(String DOC_TYPE) {
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

