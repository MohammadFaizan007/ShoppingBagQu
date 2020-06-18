package com.shoppingbag.retrofit;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Date;

/**
 * Created by Vivek on 1/8/18.
 */

public interface MvpView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    void getClickPosition(int position, String tag);

    void getMyClickPosition(String name,String tag);

    void getClickPositionDirectMember(int position, String tag, String memberId);

    void openSearchCategory(String searchItemId, String searchName);

    void getClickChildPosition(String name, String tag, Bundle bundle);

    void getProviderHint(String providerName,String hint);

    void checkAvailability(String id, String date, String name, String amount);

    void getGiftCardCategoryId(String id, String name);


}
