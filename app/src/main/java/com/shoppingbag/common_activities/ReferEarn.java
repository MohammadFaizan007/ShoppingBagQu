package com.shoppingbag.common_activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.utils.LoggerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReferEarn extends BaseActivity {
    @BindView(R.id.side_menu)
    ImageView sideMenu;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bell_icon)
    ImageView bellIcon;
    @BindView(R.id.img_refer)
    ImageView imgRefer;
    @BindView(R.id.cv_image)
    RelativeLayout cvImage;
    @BindView(R.id.cv_share)
    CardView cvShare;
    @BindView(R.id.btn_copy_code)
    TextView btnCopyCode;
    @BindView(R.id.cv_copy)
    CardView cvCopy;
    @BindView(R.id.cv_one)
    CardView cvOne;

    private Uri mInvitationUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);
        ButterKnife.bind(this);
        btnCopyCode.setText(String.format("Copy Referral Code :%s", PreferencesManager.getInstance(context).getInviteCode()));
        sideMenu.setOnClickListener(v -> onBackPressed());
        title.setText("Refer & Earn");
    }

    @OnClick({R.id.cv_share, R.id.cv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_share:
                shareDynamicLink();
                break;
            case R.id.cv_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Referral Code", PreferencesManager.getInstance(context).getInviteCode());
                clipboard.setPrimaryClip(clip);
                showMessage("Copied to clipboard");
                break;
        }
    }

    private void shareDynamicLink() {
        try {
            LoggerUtil.logItem("Invited Code" + PreferencesManager.getInstance(context).getInviteCode());
            String link = "https://" + BuildConfig.DYNAMIC_LINK + ".page.link/?invitedby=" + PreferencesManager.getInstance(context).getInviteCode();
            try {
                LoggerUtil.logItem("link" + link);
                FirebaseDynamicLinks.getInstance().createDynamicLink().setLink(Uri.parse(link))
                        .setDynamicLinkDomain(BuildConfig.DYNAMIC_LINK + ".page.link")
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com." + BuildConfig.DYNAMIC_LINK).build())
                        .buildShortDynamicLink().addOnSuccessListener(shortDynamicLink -> {

                    try {
                        hideLoading();
                        mInvitationUrl = shortDynamicLink.getShortLink();


                        String subject = "Welcome Shopping Bag";

//                        String msg = " Hi,\nInviting you to join Dreamy\nan interesting app which provides you\n" +
//                                " incredible offers on Recharge, Flight booking, Shopping & many more.\n\n" +
//                                "Use my referrer code :\n\n " + PreferencesManager.getInstance(context).getInviteCode() +
//                                "\n\nDownload app from link : " + mInvitationUrl.toString();


//                        String msg1 = " नमस्कार,\nशॉपिंग बैग परिवार में आपका स्वागत है...\n\n" +
//                                "Shopping Bag ऐप्स भारतवासियों की विश्वसनीय सुपर ऐप ! घर बैठे बैठे ढेरों कमाएं और अपने कमाई का उपयोग करें दैनिक ज़रूरतों का सामान खरीदने में, रीचार्ज/बिल्स भुगतान में या किसी भी दूकान पर पेमेंट करने में !\n\n" +
//                                "अब कमाना है बड़ा आसान - बस अपने दोस्तों  और रिश्तेदारों को शेयर करके, हर खरीद पर आप पाएंगे अलग से कमीशन ! Shopping Bag ऐप्स उपयोग करें और बनाएं अपने दैनिक जीवन को और भी आसान\n" +
//                                " यहाँ से डाउनलोड करें और हमारे रेफरल कोड " + PreferencesManager.getInstance(context).getInviteCode() + " का उपयोग करें !" +
//                                "\n\nDownload app from link : " + mInvitationUrl.toString() +
//                                "\n\nएक कदम आर्थिक आजादी की ओर..\uD83C\uDFC3\u200D" +
//                                "\nThank you so much!";


                        String msg1 = "Download app from link : " + mInvitationUrl.toString();


                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        intent.putExtra(Intent.EXTRA_TEXT, msg1);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Local Share Via Intent..
//    private void share() {
//        String subject = String.format("%s Welcome to Dreamy", "");
//        String invitationLink = "https://play.google.com/store/apps/details?id=com.shoppingbagdrosky";
//        String msg = " Hi,\nInviting you to join Dreamy\nan interesting app which provides you\n" +
//                " incredible offers on Recharge, Flight booking, Shopping & many more.\n\n" +
//                "Use my referrer code :\n\n " + PreferencesManager.getInstance(context).getInviteCode() +
//                "\n\nDownload app from link : " + invitationLink;
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        intent.putExtra(Intent.EXTRA_TEXT, msg);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }
}
