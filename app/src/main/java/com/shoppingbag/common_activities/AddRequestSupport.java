package com.shoppingbag.common_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.support.ItemItem;
import com.shoppingbag.model.support.ResponseAddSupport;
import com.shoppingbag.model.support.ResponseGetSupportType;
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

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRequestSupport extends BaseActivity implements IPickCancel, IPickResult {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;
    @BindView(R.id.edtisssue)
    TextInputEditText edtisssue;
    @BindView(R.id.textInputLayout4)
    TextInputLayout textInputLayout4;
    @BindView(R.id.edtremark)
    TextInputEditText edtremark;
    @BindView(R.id.textInputLayout7)
    TextInputLayout textInputLayout7;
    @BindView(R.id.edttransactionno)
    TextInputEditText edttransactionno;
    @BindView(R.id.ti6)
    TextInputLayout ti6;
    @BindView(R.id.edtbankname)
    TextInputEditText edtbankname;
    @BindView(R.id.ti5)
    TextInputLayout ti5;
    @BindView(R.id.edttransactiondate)
    TextInputEditText edttransactiondate;
    @BindView(R.id.ti8)
    TextInputLayout ti8;
    @BindView(R.id.imageViewOrder)
    ImageView imageViewOrder;
    @BindView(R.id.front_cam_constraint)
    ConstraintLayout frontCamConstraint;
    @BindView(R.id.btn_addproduct)
    Button btnAddproduct;

    private String request_pic = "",TypeId="",TypeName="";
    private ProgressDialog pd;
    private File requestFile;
    private PopupMenu supportTypeMenu;
    List<ItemItem> items = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_support_request_layout);
        ButterKnife.bind(this);
        title.setText("Support");
        supportTypeMenu = new PopupMenu(context, edtisssue);
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getSupportType();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }

    }

    @OnClick({R.id.side_menu, R.id.front_cam_constraint, R.id.btn_addproduct, R.id.edtisssue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.side_menu:
                onBackPressed();
                break;
            case R.id.front_cam_constraint:
                showDialog();
                break;
            case R.id.edtisssue:
                supportTypeMenu.setOnMenuItemClickListener(menuItem -> {
                    TypeId = String.valueOf(menuItem.getItemId());
                    edtisssue.setText(menuItem.getTitle());
                    return true;
                });
                supportTypeMenu.show();
                break;
            case R.id.btn_addproduct:
                if (Validation()) {
                    addSupport();
                }
                break;
        }
    }

    private void addSupport() {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("Fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("Fk_SupportId", TypeId);
            param.addProperty("Message", edtremark.getText().toString());

            LoggerUtil.logItem(param);
            showLoading();

            Call<JsonObject> call = apiServices.getSupportRequest(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            ResponseAddSupport convertedObject = new Gson().fromJson(paramResponse, ResponseAddSupport.class);
                            LoggerUtil.logItem(convertedObject);

                            if (convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                showMessage("Request submitted successfully");
                                if (requestFile != null) {
                                    showProgressDialog();
                                    uploadFile(requestFile, "Support",convertedObject.getTicketNo());
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
            RequestBody Type = RequestBody.create(MediaType.parse("text/plain"), "");
            MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
            //creating our api
            ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
            //creating a call and calling the upload image method
            Call<JsonObject> call = apiServices.uploadImageKYC(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), imgType, Type, uniquenoBody, body, PreferencesManager.getInstance(this).getANDROIDID());
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

    private void getSupportType() {
        try {
            showLoading();
            String url = BuildConfig.BASE_URL_MLM + "SupportType";
            LoggerUtil.logItem(url);

            Call<JsonObject> notificationCall = apiServices.getBusinessDashboard(url, PreferencesManager.getInstance(context).getANDROIDID());
            notificationCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem("<><" + response.body());
                    ResponseGetSupportType response_new;
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.logItem(paramResponse);
                            response_new = new Gson().fromJson(paramResponse, ResponseGetSupportType.class);
                            LoggerUtil.logItem("<><" + response_new.toString());

                            if (response.body() != null && response_new.getResponse().equalsIgnoreCase("Success")) {
                                items.addAll(response_new.getItem());

                                for (int i = 0; i < items.size(); i++) {
                                    supportTypeMenu.getMenu().add(0, Integer.valueOf(items.get(i).getPKSupportId()), i, items.get(i).getSupportName());
                                }

                            }
                        } else {
                            showMessage("Something went wrong");
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showDialog() {
        PickSetup pickSetup = new PickSetup();
        pickSetup.setTitle("Choose Image/Screenshot");
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

            CropImage.activity(pickResult.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setFixAspectRatio(false)
                    .setMaxCropResultSize(500,500)
                    .start(context);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                requestFile = FileUtils.getFile(context, result.getUri());
                Glide.with(context).load(result.getUri()).
                        apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .placeholder(R.mipmap.ic_launcher_foreground)
                                .error(R.mipmap.ic_launcher_foreground))
                        .into(imageViewOrder);

                request_pic = "request";
                //Log.e("idProof_pic_front ", ID_ProofFile_front.getAbsolutePath());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                LoggerUtil.logItem(error.getMessage());
            }

        }
    }

    private boolean Validation() {
        if (TypeId.equalsIgnoreCase("")) {
            edtisssue.setError("Select Issue");
            return false;
        } else if (edtremark.getText().toString().trim().length() < 1) {
            edtremark.setError("Enter Description");
            return false;
        } else if (requestFile == null) {
            showMessage("Please upload pic");
            return false;
        }
        return true;
    }
}
