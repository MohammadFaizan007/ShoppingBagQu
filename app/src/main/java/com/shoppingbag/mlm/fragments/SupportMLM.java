package com.shoppingbag.mlm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseFragment;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.model.response.ResponseSendQuery;
import com.shoppingbag.retrofit.ApiServices;
import com.shoppingbag.retrofit.MvpView;
import com.shoppingbag.retrofit.ServiceGenerator;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.R;
import com.shoppingbag.common_activities.LoginActivity;
import com.shoppingbag.common_activities.MainContainer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

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

public class SupportMLM extends BaseFragment implements MvpView, IPickCancel, IPickResult {
    Unbinder unbinder;
    @BindView(R.id.et_subject)
    EditText etSubject;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.view_ticket)
    Button viewTicket;
    PopupMenu popup_support;
    @BindView(R.id.txtFileName)
    TextView txtFileName;
    ArrayList<String> fileList = new ArrayList<>();
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    private PickImageDialog dialog;
    private File attachFile;

    public SupportMLM() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_support, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
//            getSupportContactDetails();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @OnClick({R.id.et_subject, R.id.submit, R.id.cancel, R.id.view_ticket, R.id.add_atachment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (Validation()) {
                    if (NetworkUtils.getConnectivityStatus(context) != 0) {
                        hideKeyboard();
                        sendQuery();
                    } else {
                        showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
                    }
                }
                break;
            case R.id.cancel:
                etSubject.setText("");
                etMessage.setText("");
                break;
            case R.id.view_ticket:
                ((MainContainer) context).ReplaceFragment(new TicketListMLM(), "Ticket's History");
                break;

            case R.id.et_subject:
                hideKeyboard();
                popup_support = new PopupMenu(context, view);
                popup_support.getMenuInflater().inflate(R.menu.menu_support, popup_support.getMenu());
                popup_support.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.action_general:
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_profile:
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_referral:
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_shopping:
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_recharge:
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        case R.id.action_bill:
                            etSubject.setText(menuItem.getTitle());
                            return true;
                        default:
                            return false;


                    }
                });
                popup_support.show();
                break;
            case R.id.add_atachment:
                showDialog();
                break;


        }
    }

    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        pickSetup.setTitle("Add Attachment");
        pickSetup.setGalleryIcon(R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);

        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getFragmentManager());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void sendQuery() {
        try {
            JsonObject param = new JsonObject();
            param.addProperty("Fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            param.addProperty("Subject", etSubject.getText().toString().trim());
            param.addProperty("Message", etMessage.getText().toString().trim());


            if (attachFile != null) {
                param.addProperty("IsAttachment", "1");
            } else {
                param.addProperty("IsAttachment", "0");
            }

            LoggerUtil.logItem(param);
            showLoading();
            Call<JsonObject> call = apiServices.getResponseSendQueryCall(bodyParam(param), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    try {
                        if (response.isSuccessful()) {
                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            ResponseSendQuery convertedObject = new Gson().fromJson(paramResponse, ResponseSendQuery.class);
                            LoggerUtil.logItem(convertedObject);

                            if (response.isSuccessful()) {
                                if (response.body() != null && convertedObject.getResponse().equalsIgnoreCase("Success")) {
                                    if (attachFile != null) {
                                        showMessage("Please wait...");
                                        uploadFile(attachFile, convertedObject.getMessageId());
                                    } else {
                                        etSubject.setText("");
                                        etMessage.setText("");
                                    }
                                }
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
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    hideLoading();
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

    private boolean Validation() {
        try {
            if ((etSubject.getText().toString().trim()).length() == 0) {
                showAlert("Subject can't be empty.", R.color.red, R.drawable.error);
                return false;
            } else if ((etMessage.getText().toString().trim()).length() == 0) {
                showAlert("Please provide some detailed information inorder to serve you better.", R.color.red, R.drawable.error);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onCancelClick() {
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            fileList = new ArrayList<>();
            attachFile = new File(pickResult.getPath());
            txtFileName.setText(attachFile.getName());
            fileList.add(attachFile.getAbsolutePath());

        }
    }

    private void uploadFile(File file, String message_id) {
        try {
            ////Log.e("***********", "attachFile is : " + file.length());
            showLoading();
            //creating request body for Profile attachFile
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), "SupportFileUpload");
            RequestBody uniqueNoBody = RequestBody.create(MediaType.parse("text/plain"), "");

            MultipartBody.Part body = MultipartBody.Part.createFormData("", file.getName(), requestBody);
            //creating our api
            //creating a call and calling the upload image method
            ApiServices apiServices = ServiceGenerator.createServiceFile(ApiServices.class);
            Call<JsonObject> call = apiServices.uploadImage(message_id, imgType, uniqueNoBody, body, PreferencesManager.getInstance(getActivity()).getANDROIDID());
            //finally performing the call
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                    LoggerUtil.logItem(response.body());
                    hideLoading();
                    try {
                        attachFile = null;
                        txtFileName.setText("");
                        etSubject.setText("");
                        etMessage.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                    hideLoading();
                    ////Log.e("***********", call.request().url().toString());
                    ////Log.e("***********", "= " + t.getMessage());
                    showMessage(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void getSupportContactDetails() {
//        try {
//            showLoading();
//            String url = BuildConfig.BASE_URL_UTILITY_V2 + "getCashbagSupport";
//            Call<JsonObject> call = apiServices_utility.getSupportData(url, PreferencesManager.getInstance(context).getANDROIDID());
//            call.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
//                    try {
//                        if (response.isSuccessful()) {
//                            String paramResponse = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
//                            JsonObject convertedObject = new Gson().fromJson(paramResponse, JsonObject.class);
//                            LoggerUtil.logItem(convertedObject);
//
//                            hideLoading();
//                            LoggerUtil.logItem(response.body());
//                            if (response.body() != null && convertedObject.get("Response").getAsString().equalsIgnoreCase("Success")) {
//                                tvEmail.setText(String.format("Mail Us : %s", convertedObject.get("Email").getAsString()));
//                                tvNumber.setText(String.format("Call Us : %s", convertedObject.get("Mobile").getAsString()));
//                            } else {
//                                tvEmail.setText(String.format("Mail Us : %s", "support@cashbag.co.in"));
//                                tvNumber.setText(String.format("Call Us : %s", "1800120000044"));
//                            }
//                        } else {
//                            clearPrefrenceforTokenExpiry();
//                            getAndroidId();
//                            goToActivityWithFinish(LoginActivity.class, null);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
//                    hideLoading();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}

