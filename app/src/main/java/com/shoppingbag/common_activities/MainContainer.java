package com.shoppingbag.common_activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.shoppingbag.BuildConfig;
import com.shoppingbag.R;
import com.shoppingbag.app.AppConfig;
import com.shoppingbag.app.PreferencesManager;
import com.shoppingbag.constants.BaseActivity;
import com.shoppingbag.constants.Cons;
import com.shoppingbag.mlm.fragments.ChangePassword;
import com.shoppingbag.mlm.fragments.SupportMLM;
import com.shoppingbag.mlm.fragments.profile.EditProfileMlm;
import com.shoppingbag.mlm.fragments.profile.ViewProfile;
import com.shoppingbag.mlm.fragments.team.DirectMember;
import com.shoppingbag.mlm.fragments.team.Downline;
import com.shoppingbag.model.activateId.ResponseActivate;
import com.shoppingbag.model.wallet.ResponseWalletBalance;
import com.shoppingbag.one_india_shopping.activity.ShopIndMakePayment;
import com.shoppingbag.one_india_shopping.activity.ShoppingIndiaActivity;
import com.shoppingbag.retrofit.Dialog_dismiss;
import com.shoppingbag.shopping.fragment.Dashboard;
import com.shoppingbag.shopping.fragment.myorders_all.MyOrders;
import com.shoppingbag.utilities.activities.BusActivity;
import com.shoppingbag.utilities.activities.DTH_Recharge;
import com.shoppingbag.utilities.activities.FlightActivity;
import com.shoppingbag.utilities.activities.MobileRechargeActivity;
import com.shoppingbag.utilities.bookedHistory.BookedFlightHistory;
import com.shoppingbag.utilities.bus.BusBookings;
import com.shoppingbag.utils.LoggerUtil;
import com.shoppingbag.utils.NetworkUtils;
import com.shoppingbag.wallet.AddMoney;
import com.shoppingbag.wallet.CashBackWallet;
import com.shoppingbag.wallet.CommissionWallet;
import com.shoppingbag.wallet.DreamyWallet;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.listeners.IPickResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class MainContainer extends BaseActivity implements Dialog_dismiss, IPickResult, BottomNavigationView.OnNavigationItemSelectedListener, PaymentResultWithDataListener {

    public String bottomTitle = "";
    public String bottomLink = "";
    @BindView(R.id.notification_bell)
    ImageView notificationBell;
    @BindView(R.id.img_search_downline_direct)
    public ImageView img_search_downline_direct;
    @BindView(R.id.title)
    public TextView title_tv;
    @BindView(R.id.bot_nav_view)
    BottomNavigationView botNavView;
    @BindView(R.id.img_referal)
    public ImageView img_referal;
    @BindView(R.id.tv_noti_count)
    TextView tv_noti_count;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    public Fragment currentFragment;
    private SharedPreferences pref;
    private LinearLayout my_team_lo;
    private LinearLayout my_wallet_lo;
    private LinearLayout my_history_lo;
    private TextView selectedText;

    private TextView my_team, nav_terms, nav_about_us, nav_business_dash;
    private TextView my_wallet;
    private TextView book_history;
    private TextView dreamy_wallet;
    private TextView cashBag_wallet;
    private TextView commission_wallet;
    private TextView txtactivateId;
    private TextView tv_Status;


    private LinearLayout visibleLayout = null;
    private AppUpdateManager mAppUpdateManager;
    private static int RC_APP_UPDATE = 10;
    private static final String TAG = ShopIndMakePayment.class.getSimpleName();
    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackBarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED) {
                        if (mAppUpdateManager != null) {
                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i("MainContainer", "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);
        ButterKnife.bind(this);
        botNavView.setOnNavigationItemSelectedListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref = getApplicationContext().getSharedPreferences(AppConfig.SHARED_PREF, 0);

        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View hView = navView.getHeaderView(0);
        findIDs(hView);

        if (!PreferencesManager.getInstance(context).gettracking_bool()) {
            PreferencesManager.getInstance(context).settracking_bool(true);
            showPopUp();
        }
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) botNavView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            View activeLabel = item.findViewById(R.id.largeLabel);
            if (activeLabel instanceof TextView) {
                activeLabel.setPadding(0, 0, 0, 0);
            }
        }
        checkUpdate();
    }

    public void findIDs(View view) {
        try {

            txtactivateId = view.findViewById(R.id.txt_activateId);
            TextView tv_Name = view.findViewById(R.id.tv_Name);
            tv_Status = view.findViewById(R.id.tv_Status);
            ImageView profile_image = view.findViewById(R.id.main_profile);
            TextView dashboard = view.findViewById(R.id.nav_dashboard);
            TextView my_register = view.findViewById(R.id.nav_register);
            my_team = view.findViewById(R.id.nav_team);
            TextView direct_member = view.findViewById(R.id.direct_member);
            TextView downLine = view.findViewById(R.id.downline);
            TextView profile = view.findViewById(R.id.nav_profile);
            my_wallet = view.findViewById(R.id.nav_wallet);
            book_history = view.findViewById(R.id.nav_book_history);
            TextView bus_booking = view.findViewById(R.id.tv_bus_book);
            TextView flight_booking = view.findViewById(R.id.tv_flight_book);
            TextView request_wallet = view.findViewById(R.id.request_wallet);
            dreamy_wallet = view.findViewById(R.id.dreamy_wallet);
            cashBag_wallet = view.findViewById(R.id.cashbag_wallet);
            commission_wallet = view.findViewById(R.id.commission_wallet);
            TextView nav_cashBack_wallet = view.findViewById(R.id.nav_cashback_wallet);
            TextView change_pass = view.findViewById(R.id.nav_change_pass);
            TextView support = view.findViewById(R.id.nav_support);
            TextView refer_earn = view.findViewById(R.id.nav_refer_earn);
            TextView logout = view.findViewById(R.id.nav_logout);
            TextView nav_business_plan = view.findViewById(R.id.nav_business_plan);
            my_team_lo = view.findViewById(R.id.my_team_lo);
            my_wallet_lo = view.findViewById(R.id.my_wallet_lo);
            nav_terms = view.findViewById(R.id.nav_terms);
            nav_about_us = view.findViewById(R.id.nav_about_us);
            nav_business_dash = view.findViewById(R.id.nav_business_dash);
            my_history_lo = view.findViewById(R.id.my_history_lo);

            nav_about_us.setOnClickListener(this);
            nav_business_dash.setOnClickListener(this);
            nav_terms.setOnClickListener(this);
            profile_image.setOnClickListener(this);
            profile.setOnClickListener(this);
            dashboard.setOnClickListener(this);
            my_register.setOnClickListener(this);
            my_team.setOnClickListener(this);
            direct_member.setOnClickListener(this);
            downLine.setOnClickListener(this);
            my_wallet.setOnClickListener(this);
            book_history.setOnClickListener(this);
            bus_booking.setOnClickListener(this);
            flight_booking.setOnClickListener(this);
            request_wallet.setOnClickListener(this);
            dreamy_wallet.setOnClickListener(this);
            commission_wallet.setOnClickListener(this);
            cashBag_wallet.setOnClickListener(this);
            nav_cashBack_wallet.setOnClickListener(this);
            change_pass.setOnClickListener(this);
            support.setOnClickListener(this);
            refer_earn.setOnClickListener(this);
            nav_business_plan.setOnClickListener(this);
            logout.setOnClickListener(this);
            tv_Status.setOnClickListener(this);

            tv_Name.setText(Cons.decryptMsg(PreferencesManager.getInstance(context).getNAME(), cross_intent));
            tv_Status.setText(PreferencesManager.getInstance(context).getStatus());
            if (PreferencesManager.getInstance(context).getStatus().equalsIgnoreCase("Active")) {
                txtactivateId.setVisibility(View.GONE);
                tv_Status.setBackground(ContextCompat.getDrawable(context, R.drawable.status_green_drawable));
            } else {
                txtactivateId.setVisibility(View.VISIBLE);
                tv_Status.setBackground(ContextCompat.getDrawable(context, R.drawable.status_red_drawable));
            }

            Log.e("============", "=====================================" + BuildConfig.BASE_URL_FORIMAGE + PreferencesManager.getInstance(context).getPROFILEPIC());
            Glide.with(context).load(PreferencesManager.getInstance(context).getPROFILEPIC().replace("~", BuildConfig.BASE_URL_FORIMAGE))
                    .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.user).error(R.drawable.user)).circleCrop()
                    .into(profile_image);
            Glide.with(context).load(PreferencesManager.getInstance(context).getPROFILEPIC().replace("~", BuildConfig.BASE_URL_FORIMAGE))
                    .apply(new RequestOptions().diskCacheStrategy(AUTOMATIC).placeholder(R.drawable.user).error(R.drawable.user)).circleCrop()
                    .into(img_referal);
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }


    private void checkUpdate() {
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.registerListener(installStateUpdatedListener);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, MainContainer.this, RC_APP_UPDATE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                Log.e("MainContainer", "Update avalaible");

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            } else {
                ReplaceFragmentWithHome(new Dashboard(), "Dashboard");
                Log.e("MainContainer", "checkForAppUpdateAvailability: something else");
            }
        });
        mAppUpdateManager.getAppUpdateInfo().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                ReplaceFragmentWithHome(new Dashboard(), "Dashboard");
                Log.e("MainContainer", "checkForAppUpdateAvailability: something else1");
            }
        });
    }

    private void popupSnackBarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.frame),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null) {
                mAppUpdateManager.completeUpdate();
            }
        });


        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    @Override
    public void onClick(View v) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        switch (v.getId()) {
            case R.id.txtWelcome:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("profile_name");
                break;
            case R.id.main_profile:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("main_profile");
                break;
            case R.id.nav_dashboard:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_dashboard");
                break;
            case R.id.nav_register:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_register");
                break;
            case R.id.nav_team:
                checkLogin("nav_team");
                break;
            case R.id.direct_member:
                drawerLayout.closeDrawer(GravityCompat.START);
                img_referal.setVisibility(View.GONE);
                ReplaceFragment(new DirectMember(), "Direct Member");
                break;
            case R.id.downline:
                drawerLayout.closeDrawer(GravityCompat.START);
                img_referal.setVisibility(View.GONE);
                ReplaceFragment(new Downline(), "DownLine");
                break;
            case R.id.nav_profile:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_profile");
                break;
            case R.id.tv_Status:
                if (tv_Status.getText().toString().equalsIgnoreCase("Active")) {

                } else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startPayment();
                }

                break;
            case R.id.nav_wallet:
                //drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_wallet");
                break;
            case R.id.request_wallet:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(this, AddMoney.class, null);
                break;
            case R.id.dreamy_wallet:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(this, DreamyWallet.class, null);
                break;
            case R.id.cashbag_wallet:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(this, CashBackWallet.class, null);
                break;
            case R.id.commission_wallet:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(this, CommissionWallet.class, null);
                break;
            case R.id.nav_cashback_wallet:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav__cash_wallet");
                break;
            case R.id.nav_change_pass:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_change_pass");
                break;
            case R.id.nav_terms:
                drawerLayout.closeDrawer(GravityCompat.START);
                Bundle bundleN = new Bundle();
                bundleN.putString("title", "Terms & Conditions");
                bundleN.putString("link", "http://shoppingbag.co.in/Home-TermsCondition");
                goToActivity(context, NormalWebViewActivity.class, bundleN);
                break;
            case R.id.nav_about_us:
                drawerLayout.closeDrawer(GravityCompat.START);
                Bundle bundle = new Bundle();
                bundle.putString("title", "AboutUs");
                bundle.putString("link", "http://dreamydroshky.in/Home-AboutUs");
                goToActivity(context, NormalWebViewActivity.class, bundle);
                break;
            case R.id.nav_business_dash:
                drawerLayout.closeDrawer(GravityCompat.START);
                ReplaceFragment(new BusinessDashboard(), "Business Dashboard");
                break;
            case R.id.nav_support:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_support");
                break;
            case R.id.nav_refer_earn:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_refer_earn");
                break;
            case R.id.nav_book_history:
                // drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_book_history");
                break;
            case R.id.tv_bus_book:
                drawerLayout.closeDrawer(GravityCompat.START);
                goToActivity(this, BusBookings.class, null);
                break;
            case R.id.tv_flight_book:
                goToActivity(this, BookedFlightHistory.class, null);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_business_plan:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_business_plan");
                break;
            case R.id.nav_logout:
                drawerLayout.closeDrawer(GravityCompat.START);
                checkLogin("nav_logout");
                break;
            default:
                drawerLayout.closeDrawer(GravityCompat.START);
                LoggerUtil.logItem("No selected");
                break;
        }
    }

    private void hideOpenedLayout(LinearLayout ll, TextView tv, int drawable) {
        if (visibleLayout != null && visibleLayout != ll) {
            visibleLayout.setVisibility(View.GONE);
            selectedText.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, R.drawable.right_w, 0);
        }
        visibleLayout = ll;
        selectedText = tv;
    }

    public void checkLogin(String bottom_title) {
        bottomTitle = bottom_title;
        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            if (PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
//                FullScreenLogin dialog = new FullScreenLogin();
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                dialog.show(ft, FullScreenLogin.TAG);
            } else {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                switch (bottom_title) {
                    case "profile_name":
                    case "main_profile":
                    case "nav_profile":
                        img_referal.setVisibility(View.GONE);
                        ReplaceFragment(new ViewProfile(), "Profile");
                        break;
                    case "nav_dashboard":
                        img_referal.setVisibility(View.VISIBLE);
                        ReplaceFragment(new Dashboard(), "Dashboard");
                        break;
                    case "nav_register":
                        goToActivity(this, TeamRegisterActivity.class, null);
                        break;
                    case "nav_team":
                        hideOpenedLayout(my_team_lo, my_team, R.drawable.ic_team);
                        if (my_team_lo.getVisibility() == View.GONE) {
                            my_team_lo.setVisibility(View.VISIBLE);
                            my_team.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_team, 0, R.drawable.down_w, 0);
                        } else {
                            my_team_lo.setVisibility(View.GONE);
                            my_team.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_team, 0, R.drawable.right_w, 0);
                        }
                        break;
                    case "nav_wallet":
                        //  goToActivity(context, DreamyWallet.class, null);
                        hideOpenedLayout(my_wallet_lo, my_wallet, R.drawable.ic_wallet);
                        if (my_wallet_lo.getVisibility() == View.GONE) {
                            my_wallet_lo.setVisibility(View.VISIBLE);
                            my_wallet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wallet, 0, R.drawable.down_w, 0);
                        } else {
                            my_wallet_lo.setVisibility(View.GONE);
                            my_wallet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wallet, 0, R.drawable.right_ww, 0);
                        }
                        break;
                    case "nav__cash_wallet":
                        // Cashback Wallet transaction
                        break;
                    case "nav_change_pass":
                        img_referal.setVisibility(View.GONE);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("through", "loginpwd");
                        ChangePassword change = new ChangePassword();
                        change.setArguments(bundle1);
                        ReplaceFragment(change, "Change Password");
                        break;
                    case "nav_support":
                        img_referal.setVisibility(View.GONE);
                        goToActivity(this, SupportActivity.class, null);
                        // ReplaceFragment(new SupportMLM(), "Support");
                        break;
                    case "nav_refer_earn":
                        goToActivity(this, ReferEarn.class, null);
                        break;
                    case "nav_book_history":
                        hideOpenedLayout(my_history_lo, book_history, R.drawable.ic_ticket);
                        if (my_history_lo.getVisibility() == View.GONE) {
                            my_history_lo.setVisibility(View.VISIBLE);
                            book_history.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ticket, 0, R.drawable.down_w, 0);
                        } else {
                            my_history_lo.setVisibility(View.GONE);
                            book_history.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ticket, 0, R.drawable.right_ww, 0);
                        }
                        break;
                    case "nav_business_plan":
                        goToActivity(context, BusinessPlan.class, null);
                        break;
                    case "nav_logout":
                        logoutDialog(context, VerifyMobile.class);
                        break;
                    default:
                        // For business and other click option
                        goToActivityWithFinish(LoginActivity.class, null);
                        break;
                }
            }
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @OnClick({R.id.notification_bell, R.id.img_search, R.id.img_referal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notification_bell:
                goToActivity(context, NotificationList.class, null);
                break;
            case R.id.img_referal:
                ReplaceFragment(new ViewProfile(), "Profile");
                //goToActivity(context, ReferralActivity.class, null);
                break;
            case R.id.img_search:
                goToActivity(context, SearchActivity.class, null);
                break;
        }
    }

    public void logoutDialog(Context context, Class activity) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Logout");
        builder1.setMessage("Do you really want to logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", (dialog, id) -> {
            try {
                clearPrefrenceforTokenExpiry();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("token", "");
                editor.apply();
                Intent intent1 = new Intent(context, activity);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
                finish();
                dialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        builder1.setNegativeButton("No", (dialog, id) -> dialog.dismiss());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void ReplaceFragmentWithHome(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, setFragment, title);
        notificationBell.setVisibility(View.GONE);
        img_referal.setVisibility(View.VISIBLE);
        img_search_downline_direct.setVisibility(View.GONE);
        title_tv.setText(Html.fromHtml("<medium><b>" + title.toUpperCase() + "</b></medium>"));
        title_tv.setVisibility(View.VISIBLE);
        transaction.commit();
    }

    public void ReplaceFragment(Fragment setFragment, String title) {
        currentFragment = setFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().addToBackStack(title);
        transaction.replace(R.id.frame, setFragment, title);
        notificationBell.setVisibility(View.GONE);
        title_tv.setText(Html.fromHtml("<medium><b>" + title.toUpperCase() + "</b></medium>"));
        title_tv.setVisibility(View.VISIBLE);
        if (currentFragment instanceof DirectMember || currentFragment instanceof Downline) {
            img_search_downline_direct.setVisibility(View.VISIBLE);
        } else {
            img_search_downline_direct.setVisibility(View.GONE);
        }
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_dashboard:
                if (!(currentFragment instanceof Dashboard))
                    ReplaceFragmentWithHome(new Dashboard(), "Dashboard");
                return true;
            case R.id.nav_team:
                if (!(currentFragment instanceof Downline))
                    ReplaceFragmentWithHome(new Downline(), "DownLine");
                return true;
//            case R.id.nav_dreamy:
//                goToActivity(MainContainer.this, ShoppingIndiaActivity.class, null);
//                return true;
            case R.id.nav_business:
                ReplaceFragmentWithHome(new BusinessDashboard(), "Business Dashboard");
                return true;
            case R.id.nav_top:
                ReplaceFragmentWithHome(new ReferralActivity(), "Today's top 10 referrals");
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        img_search_downline_direct.setVisibility(View.GONE);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() < 1) {
            if (currentFragment instanceof Dashboard) {
                super.onBackPressed();
                finish();
            } else {
                botNavView.setSelectedItemId(R.id.nav_dashboard);
                ReplaceFragmentWithHome(new Dashboard(), "Dashboard");
            }
        } else {
            botNavView.setSelectedItemId(R.id.nav_dashboard);
            fm.popBackStack();
        }
    }


    @Override
    public void onDismiss() {
        try {
            LoggerUtil.logItem(bottomTitle);
            if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
                switch (bottomTitle) {
                    case "Mobile Recharge":
                        goToActivity(context, MobileRechargeActivity.class, null);
                        break;
                    case "DTH":
                        goToActivity(context, DTH_Recharge.class, null);
                        break;
                    case "Flight":
                        goToActivity(context, FlightActivity.class, null);
                        break;
                    case "Bus":
                        goToActivity(context, BusActivity.class, null);
                        break;
                    case "Shopping":
                        goToActivitySingle(MainContainer.this, ShoppingIndiaActivity.class, null);
                        break;
                    case "Utility": {
                        Bundle bundle = new Bundle();
                        bundle.putString("from", "Main");
                        bundle.putString("link", bottomLink + Cons.decryptMsg(PreferencesManager.getInstance(context).getLoginID(), cross_intent));
                        ((MainContainer) context).goToActivity(context, NormalWebViewActivity.class, bundle);
                        break;
                    }
                    case "profile_name":
                    case "main_profile":
                    case "nav_profile":
                        goToActivity(context, ViewProfile.class, null);
                        break;
                    case "nav_dashboard":
                        goToActivity(context, Dashboard.class, null);
                        break;
                    case "nav_team":
                        // Dreamy Team
                        break;
                    case "nav_wallet":
                        goToActivity(context, SearchActivity.class, null);
                        break;
                    case "nav__cash_wallet":
                        // Cashback Wallet transaction
                        break;
                    case "nav_change_pass":
                        img_referal.setVisibility(View.GONE);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("through", "loginpwd");
                        ChangePassword change = new ChangePassword();
                        change.setArguments(bundle1);
                        ReplaceFragment(change, "Change Password");
                        break;
                    case "nav_support":
                        goToActivity(context, SupportMLM.class, null);
                        break;
                    case "nav_logout":
                        logoutDialog(context, VerifyMobile.class);
                        break;

                    default:
                        break;
                }
            }
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PreferencesManager.getInstance(context).getUSERID().equalsIgnoreCase("")) {
            getWalletBalance();
        }
        mAppUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    mAppUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            this,
                                            RC_APP_UPDATE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }

    private void getWalletBalance() {
        //http://dreamydroshky.in/webapi/api/Web/GetWallet?MemberId=+/rsYtyXxjMp5KsWywOssA==

        String url = BuildConfig.BASE_URL_MLM + "GetWallet?MemberId=" + PreferencesManager.getInstance(context).getUSERID();
        LoggerUtil.logItem("WALLET - " + url);
        Call<JsonObject> call = apiServices.getBankName(url, PreferencesManager.getInstance(context).getANDROIDID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                try {
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    if (response.isSuccessful()) {
                        ResponseWalletBalance responseWalletBalance = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), ResponseWalletBalance.class);
                        if (response.body() != null && responseWalletBalance.getResponse().equalsIgnoreCase("Success")) {
                            dreamy_wallet.setText(String.format("Shopping Bag ₹ %s", responseWalletBalance.getResult().getDreamyWalletAmount()));
                            cashBag_wallet.setText(String.format("CashBack ₹ %s", responseWalletBalance.getResult().getCashbackWalletAmount()));
                            commission_wallet.setText(String.format("Commission ₹ %s", responseWalletBalance.getResult().getCommisionWalletAmount()));
                        } else {
                            dreamy_wallet.setText("Shopping Bag ₹ 0");
                            cashBag_wallet.setText("CashBack ₹ 0");
                            commission_wallet.setText("Commission ₹ 0");
                        }
                    } else {
                        Log.e("WALLET", "ERROR");
                        dreamy_wallet.setText("Shoppig Bag ₹ 0");
                        cashBag_wallet.setText("CashBack ₹ 0");
                        commission_wallet.setText("Commission ₹ 0");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                Log.e("WALLET", "ERROR" + t.getMessage());
            }
        });
    }

    private void getSignOut(Class activity) {
        try {
            showLoading();
            JsonObject object = new JsonObject();
            object.addProperty("Fk_MemId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            object.addProperty("AndroidId", PreferencesManager.getInstance(context).getANDROIDID());
            object.addProperty("DeviceId", pref.getString("regId", ""));

            LoggerUtil.logItem(object);

            Call<JsonObject> call = apiServices.getUserSignout(bodyParam(object), PreferencesManager.getInstance(context).getANDROIDID());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {

                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    JsonObject response_new;
                    try {
                        if (response.isSuccessful()) {
                            response_new = new Gson().fromJson(Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent), JsonObject.class);
                            if (response.body() != null && response_new.get("response").getAsString().equalsIgnoreCase("Success")) {
                                clearPrefrenceforTokenExpiry();
                                Intent intent1 = new Intent(context, activity);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent1);
                                finish();
                            } else {
                                showMessage("Something went wrong");
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
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    showMessage("Something went wrong");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e("MainContainer", "onActivityResult: app download failed");
            }
        }
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (currentFragment instanceof EditProfileMlm) {
            ((EditProfileMlm) currentFragment).onPickResult(pickResult);
        } else {
            ((MyOrders) currentFragment).onPickResult(pickResult);
        }
    }

    private void showPopUp() {
        Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.popup_lay
                , null));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(settingsDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        settingsDialog.getWindow().setAttributes(lp);
        ImageView imageView = settingsDialog.findViewById(R.id.launch_img);
        Glide.with(context).load(R.drawable.lockdownvonus).into(imageView);
        settingsDialog.show();
        settingsDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
//                showPopUpHindi();
            }
        });

        imageView.setOnClickListener(v -> {
            settingsDialog.dismiss();

        });

    }

    private void showPopUpHindi() {
        Dialog settingsDialog = new Dialog(this);
        ImageView imageView;
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.popup_lay
                , null));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(settingsDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        settingsDialog.getWindow().setAttributes(lp);
        imageView = settingsDialog.findViewById(R.id.launch_img);
//        imageView.setImageResource(R.drawable.launching_popup);
        Glide.with(context).load(R.drawable.businessplan).into(imageView);

        settingsDialog.show();
        settingsDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

        final Activity activity = this;

        final Checkout co = new Checkout();
        String email = "", mobile = "";
        try {
            email = Cons.decryptMsg(PreferencesManager.getInstance(context).getEMAIL(), cross_intent);
            mobile = Cons.decryptMsg(PreferencesManager.getInstance(context).getMOBILE(), cross_intent);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            JSONObject options = new JSONObject();
            options.put("name", "Shopping Bag");
            options.put("description", "Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            /*
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", String.valueOf(100 * 100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", mobile);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            // RazorPaymentID = paymentData.getPaymentId();
            //hitCreateOrderV2(RazorCashwalletAmt, RazorDreamywalletAmt, RazorPaymentAmt, RazorPaymentID, "checkmo");
            hitActivateIdApi(paymentData.getPaymentId());
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
            Toast.makeText(this, "Payment failed: " + i, Toast.LENGTH_SHORT).show();
            // hitFailedTransaction(Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent), String.valueOf(RazorPaymentAmt), paymentData.getPaymentId());
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    private void hitActivateIdApi(String id) {
        showLoading();
        try {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Amount", "100");
            jsonObject.addProperty("TransactionId", id);
            jsonObject.addProperty("Fk_memId", Cons.decryptMsg(PreferencesManager.getInstance(context).getUSERID(), cross_intent));
            LoggerUtil.logItem(jsonObject);
            Call<JsonObject> jsonObjectCall = apiServices.getActivation(bodyParam(jsonObject), PreferencesManager.getInstance(context).getANDROIDID());
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideLoading();
                    LoggerUtil.logItem(response.body());
                    LoggerUtil.logItem(response.code());
                    LoggerUtil.logItem(call.request().url());
                    try {
                        if (response.isSuccessful()) {
                            String param = Cons.decryptMsg(response.body().get("body").getAsString(), cross_intent);
                            LoggerUtil.log("================================" + param);
                            Gson gson = new GsonBuilder().create();
                            ResponseActivate activate = gson.fromJson(param, ResponseActivate.class);
                            if (activate.getResponse().equalsIgnoreCase("success")) {
                                checkUpdate();
                                showMessage(activate.getMessage());
                                PreferencesManager.getInstance(context).setStatus(activate.getStatus());


                            } else {
                                showMessage(activate.getMessage());
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
