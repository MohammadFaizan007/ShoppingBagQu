package com.shoppingbag.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.shoppingbag.R;
import pl.droidsonroids.gif.GifImageView;


public class DialogUtil {
    private static final String TAG = "DialogUtil";

    public static ProgressDialog progressDialog;

    public DialogUtil(Activity activity) {
        // This utility class is not publicly instantiable
//        progressDialog = new ProgressDialog(activity);
    }

    public static ProgressDialog showLoadingDialog(Activity activity, String callingPlace) {
        //Log.d(TAG, "showLoadingDialog: " + callingPlace);
//        if (!progressDialog.isShowing())
        progressDialog = new ProgressDialog(activity);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.setOnCancelListener(dialog -> dialog.dismiss());

        return progressDialog;


    }

    public static void showInfoDialog(Context context, String title, String info) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(info);
        dialog.setCancelable(true);
        dialog.setPositiveButton("OK", (dialog1, which) -> dialog1.dismiss());

        dialog.show();


    }

    public static void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    public static Dialog showFlightDialog(Context context) {

        Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.flight_loader);
        GifImageView img = dialog.findViewById(R.id.planImg);
        img.setImageResource(R.drawable.plan_gif);

//        int width = Math.round(context.getResources().getDisplayMetrics().widthPixels * 0.98f);
//        int height = Math.round(context.getResources().getDisplayMetrics().heightPixels * 0.94f);

//        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
//        dialog.getWindow().setLayout(width, height);
        dialog.show();
        return dialog;
    }
}