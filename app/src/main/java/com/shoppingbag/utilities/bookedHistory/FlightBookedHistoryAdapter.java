package com.shoppingbag.utilities.bookedHistory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppingbag.model.bookedHistory.responseModel.GetBookingHistorydetailItem;
import com.shoppingbag.R;
import com.shoppingbag.utilities.domesticflight.FlightSeatMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlightBookedHistoryAdapter extends RecyclerView.Adapter<FlightBookedHistoryAdapter.ViewHolder> {

    private Activity context;
    private List<GetBookingHistorydetailItem> list;

    private FlightBookedHistoryAdapter.ViewHolder accStatHolder;
    private int mExpandedPosition = -1;


    FlightBookedHistoryAdapter(Activity context, List<GetBookingHistorydetailItem> list) {
        this.context = context;
        this.list = list;
    }

//    public FlightBookedHistoryAdapter(Activity context, List<BookedPNRsItem> bookedPNRs) {
//        this.context = context;
//        this.list = list;
//    }

    @NonNull
    @Override
    public FlightBookedHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_booked_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FlightBookedHistoryAdapter.ViewHolder holder, final int position) {
        Log.e(">>>>>","<<<<<<<<<<<<<<<<<<"+list.get(position).getBoookingDate());
        holder.bookedOn.setText(String.format("Booked On: %s", list.get(position).getBoookingDate()));
        holder.cityCodeFrom.setText(list.get(position).getOrigin());
        holder.cityCodeTwo.setText(list.get(position).getDestination());
        holder.grossAmt.setText(String.format("Amount: %s", list.get(position).getTotalAMount()));
        holder.txtAirline.setText(list.get(position).getAirlineName());
        holder.txtAirlinePNRStatus.setText(String.format("PNR: %s", list.get(position).getAirlinePNR()));// +
//                " (" + list.get(position).getP() + ")");

        final boolean isExpanded = position == mExpandedPosition;
        holder.lvOptions.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.arrowDetails.setRotation(0);
        if (isExpanded) {
            holder.arrowDetails.animate().setDuration(500).rotationBy(180).start();
        }

        holder.arrowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accStatHolder != null) {
                    accStatHolder.lvOptions.setVisibility(View.GONE);
                    notifyItemChanged(mExpandedPosition);
                }
                mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                accStatHolder = isExpanded ? null : holder;
                notifyItemChanged(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void cancelticket(final String hermsPNR, final String airlinePNR) {

        CharSequence[] list = {"Full Cancellation", " Partial Cancellation"};

        new AlertDialog.Builder(context)
                .setSingleChoiceItems(list, 0, null)
                .setPositiveButton("Cancel Ticket", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        switch (selectedPosition) {
                            case 0:
//                                fullCancelTicket(hermsPNR, airlinePNR, "FULL");
                                break;
                            case 1:
//                                fullCancelTicket(hermsPNR, airlinePNR, "PARTIAL");
                                break;
                        }
                    }
                })
                .show();
    }

    /*PARTIAL- for Partial Cancellation
    FULL – for Full Cancellation*/

//    private void fullCancelTicket(final String hermsPNR, final String airlinePNR, final String type) {
//        ((BookedFlightHistory) context).showLoading();
//        RequestFullCancelation accStat = new RequestFullCancelation();
////        Authentication authentication = new Authentication();
////        authentication.setLoginId(Utils.USERNAME);
////        authentication.setPassword(Utils.PASSWORD);
////        accStat.setAuthentication(authentication);
//
//        CancellationInput input = new CancellationInput();
//        input.setAirlinePNR(airlinePNR);
//        input.setHermesPNR(hermsPNR);
//        input.setCancelType(type);
//        accStat.setCancellationInput(input);
//
//        try {
//            String json = gson.toJson(accStat);
//            JSONObject params = new JSONObject(json);
//            new HitApi(BuildConfig.BASE_URL_TRAVEL + "CancelTicket", params, "GET_BOOKED_CANCEL_FULL", false) {
//                @Override
//                public void responseObjectApi(JSONObject response) {
//                    super.responseObjectApi(response);
//                    ((BookedFlightHistory) context).hideLoading();
//                    try {
//                        if (response != null && response.getInt("ResponseStatus") == 1) {
//                            if (response.getInt("ResponseStatus") == 1) {
//
//                                if (type.equalsIgnoreCase("PARTIAL")) {
//                                    ((BookedFlightHistory) context).hideLoading();
//                                    Intent intent = new Intent(context, PartialCancelTicket.class);
//                                    intent.putExtra("response", response.toString());
//                                    context.startActivity(intent);
//                                } else {
//                                    Toast.makeText(context, response.getString("SuccessRemarks"), Toast.LENGTH_LONG).show();
////                                    saveCancelticket(hermsPNR, airlinePNR, response.getString("SuccessRemarks"));
//                                }
//                            }
//                        } else {
//                            ((BookedFlightHistory) context).hideLoading();
//                            Toast.makeText(context, response.getString("FailureRemarks"), Toast.LENGTH_LONG).show();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void errorApi(VolleyError error) {
//                    super.errorApi(error);
//                    ((BookedFlightHistory) context).hideLoading();
//                }
//            };
//        } catch (Exception | Error e) {
//            e.printStackTrace();
//        }
//    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cityCodeFrom)
        TextView cityCodeFrom;
        @BindView(R.id.cityCodeTwo)
        TextView cityCodeTwo;
        @BindView(R.id.txtAirlinePNRStatus)
        TextView txtAirlinePNRStatus;
        @BindView(R.id.txtAirline)
        TextView txtAirline;
        @BindView(R.id.bookedOn)
        TextView bookedOn;
        @BindView(R.id.gross_amt)
        TextView grossAmt;
        @BindView(R.id.txtCancelTicket)
        TextView txtCancelTicket;
        @BindView(R.id.txtPrintTicket)
        TextView txtPrintTicket;
        @BindView(R.id.txtSeatMap)
        TextView txtSeatMap;
        @BindView(R.id.lv_options)
        LinearLayout lvOptions;
        @BindView(R.id.arrowDetails)
        ImageView arrowDetails;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            txtSeatMap.setOnClickListener(this);
            txtCancelTicket.setOnClickListener(this);
            txtPrintTicket.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.txtSeatMap:
                    Intent intent = new Intent(context, FlightSeatMap.class);
                    intent.putExtra("code", list.get(getAdapterPosition()).getAirlineCode());
                    intent.putExtra("pnr", list.get(getAdapterPosition()).getHermesPNR());
                    intent.putExtra("airlinepnr", list.get(getAdapterPosition()).getAirlinePNR());
                    context.startActivity(intent);
                    break;
                case R.id.txtPrintTicket:
                    Intent intentpnr = new Intent(context, PrintTicketLay.class);
                    intentpnr.putExtra("pnr", list.get(getAdapterPosition()).getHermesPNR());
                    context.startActivity(intentpnr);
                    break;
                case R.id.txtCancelTicket:
                    cancelticket(list.get(getAdapterPosition()).getHermesPNR(), list.get(getAdapterPosition()).getAirlinePNR());
                    break;
            }
        }
    }
}
