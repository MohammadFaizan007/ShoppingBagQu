package com.shoppingbag.retrofit;


import com.shoppingbag.model.bookedHistory.requestModel.RequestPartialCancel;
import com.shoppingbag.model.domesticflight.seatAdapter.RequestSeatMap;
import com.shoppingbag.model.domesticflight.seatAdapter.ResponseSeatMap;
import com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm.RequestSeatConfirm;
import com.shoppingbag.model.mobile_recharge.requestmodel.RequestGetAccountStatement;
import com.shoppingbag.model.mobile_recharge.requestmodel.RequestGetReprintMobile;
import com.shoppingbag.model.mobile_recharge.requestmodel.RequestGetTransactionStatus;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseGetAccountStatement;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseGetReprintMobile;
import com.shoppingbag.model.mobile_recharge.responsemodel.ResponseGetTransactionStatus;
import com.shoppingbag.model.response.ResponsePaypalStatus;
import com.shoppingbag.model.response.bus_response.requestpojo.RequestGetCancellation;
import com.shoppingbag.model.response.bus_response.requestpojo.RequestGetCancellationPenalty;
import com.shoppingbag.model.response.bus_response.requestpojo.RequestGetSeatBlock;
import com.shoppingbag.model.response.bus_response.responsepojo.ResponseGetCancellationPenalty;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServices {

    @POST("Login")
    Call<JsonObject> getLogin(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("GetReferalDetails")
    Call<JsonObject> getReferalNameFronCode(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("ForgotPassword")
    Call<JsonObject> getForgotPassword(@Body JsonObject requestForgotPass, @Header("androidId") String AndroidId);

    @POST("Registration")
    Call<JsonObject> getRegistration(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("OTPRequest")
    Call<JsonObject> getRequestotp(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("EWalletRequest")
    Call<JsonObject> getEWalletRequest(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("PayoutRequest")
    Call<JsonObject> getIncentiveLedger(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("WalletRequest")
    Call<JsonObject> getNewWalletRequest(@Body JsonObject object, @Header("androidId") String AndroidId);

    //    Jio Prepaid
    @POST("ForFetchPlanPrepaid")
    Call<JsonObject> getJioPlan(@Body JsonObject jsonObject, @Header("androidId") String AndroidId);

    @POST("GetStateName")
    Call<JsonObject> getstate(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("ChangePassword")
    Call<JsonObject> ChangePassword(@Body JsonObject object, @Header("androidId") String AndroidId);

    @GET
    Call<JsonObject> getOtherOrders(@Url String url, @Header("androidId") String AndroidId);

    @GET("GetDreamyBank")
    Call<JsonObject> getBankDetails();

    @POST("GetCustomerReferalmobile")
    Call<JsonObject> getReferalName(@Body JsonObject object, @Header("androidId") String AndroidId);


    @POST("IdActivation")
    Call<JsonObject> getActivation(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("TicketsSupport")
    Call<JsonObject> getTicketCall(@Body JsonObject requestTicket, @Header("androidId") String AndroidId);

    @POST("SupportSend")
    Call<JsonObject> getResponseSendQueryCall(@Body JsonObject requestSendQuery, @Header("androidId") String AndroidId);

    @Multipart
    @POST("ImageUpload")
    Call<JsonObject> uploadImage(@Query("Id") String id,
                                 @Part("Action") RequestBody imgType,
                                 @Part("UniqueNo") RequestBody uniqueNo,
                                 @Part() MultipartBody.Part file,
                                 @Header("androidId") String AndroidId);

    @Multipart
    @POST("ImageUpload")
    Call<JsonObject> uploadImageKYC(@Query("Id") String id, @Part("Action") RequestBody imgType, @Part("Type") RequestBody Type, @Part("UniqueNo") RequestBody uniqueNo, @Part() MultipartBody.Part file, @Header("androidId") String AndroidId);

    @POST("BankDetailsUpdate")
    Call<JsonObject> getBankUpdate(@Body JsonObject update, @Header("androidId") String AndroidId);

    @POST("SupportRequest")
    Call<JsonObject> getSupportRequest(@Body JsonObject update, @Header("androidId") String AndroidId);

    @POST("UpdateMemberPersonalDetails")
    Call<JsonObject> getPersonalUpdate(@Body JsonObject update, @Header("androidId") String AndroidId);

    @GET
    @Headers({"Content-Type: application/json"})
    Call<JsonObject> getBankName(@Url String url, @Header("androidId") String AndroidId);

    @GET
    @Headers({"Content-Type: application/json"})
    Call<JsonObject> getStateCity(@Url String url, @Header("androidId") String AndroidId);

    @POST("GetNotificationsList")
    Call<JsonObject> getGetNotificationsList(@Body JsonObject object, @Header("androidId") String AndroidId);

    @GET()
    @Headers({"Content-Type: application/json"})
    Call<JsonObject> getTodayReferral(@Url String url, @Header("androidId") String AndroidId);

    @GET()
    @Headers({"Content-Type: application/json"})
    Call<JsonObject> getKYCStatus(@Url String url, @Header("androidId") String AndroidId);

    @GET()
    @Headers({"Content-Type: application/json"})
    Call<JsonObject> getBusinessDashboard(@Url String url, @Header("androidId") String AndroidId);

    @POST("PaypalToWallet")
    Call<JsonObject> getPayToWallet(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("GatewayFailedTrans")
    Call<JsonObject> getGatewayFailedTrans(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("GetPaymentTransactionDetails")
    Call<JsonObject> getPaymentStatus(@Body JsonObject verification, @Header("androidId") String AndroidId);

    @POST("SignOutAPI")
    Call<JsonObject> getUserSignout(@Body JsonObject object, @Header("androidId") String AndroidId);

    @GET
    Call<JsonObject> getProfile(@Url String url, @Header("androidId") String AndroidId);

    @POST("InsertAmazonlDetails")
    Call<JsonObject> addAmazonProduct(@Body JsonObject add, @Header("androidId") String AndroidId);

    @GET
    Call<JsonObject> getShoppingItemList(@Url String url, @Header("androidId") String AndroidId);

    @GET
    Call<JsonObject> getDashboardDataNew(@Url String s, @Header("androidId") String AndroidId);

    //    Bus Api
    @GET("GetOrigin")
    Call<JsonObject> getBusOrigin();

    @POST("GetDestination")
    Call<JsonObject> getBusDestination(@Body JsonObject object);

    @POST("GetSearch")
    Call<JsonObject> getBusSearch(@Body JsonObject object);

    @POST("GetSeatMap")
    Call<JsonObject> getBusSeatMap(@Body JsonObject object);

    @POST("GetSeatBlock")
    Call<JsonObject> getSeatBlock(@Body RequestGetSeatBlock object);

    @POST("GetBusTransactionStatus")
    Call<JsonObject> getbustransactionstatus(@Body JsonObject object);

    @POST("GetBook")
    Call<JsonObject> getSeatBooked(@Body JsonObject object);

    @POST("GetBookedHistory")
    Call<JsonObject> getBusBookings(@Body JsonObject jsonObject);

    @POST("GetReprint")
    Call<JsonObject> getBusReprint(@Body JsonObject jsonObject);


    @GET()
    Call<JsonObject> getTeamandBusiness(@Url String s, @Header("androidId") String AndroidId);

    @POST("GetCancellationPenalty")
    Call<ResponseGetCancellationPenalty> getCancellationPenalty(@Body RequestGetCancellationPenalty requestGetCancellationPenalty);

    @POST("GetCancellation")
    Call<JsonObject> getCancellation(@Body RequestGetCancellation requestGetCancellation);

    @POST("GetPaymentTransactionDetails")
    Call<ResponsePaypalStatus> getPaymentStatus(@Body JsonObject requestGetCancellation);


    @POST("GetBookingHistory")
    Call<JsonObject> getFlightBookingHistory(@Body JsonObject requestGetCancellation);

    @POST("GetPartialCancellation")
    Call<JsonObject> getPartialCancellation(@Body RequestPartialCancel requestGetCancellation);

    @POST("DownloadTicket")
    Call<JsonObject> getDownloadTicket(@Body JsonObject requestGetCancellation);

    @POST("GetAccountStatement")
    Call<ResponseGetAccountStatement> getAccountStatement(@Body RequestGetAccountStatement requestGetAccountStatement);

    @POST("GetRecentUsers")
    Call<JsonObject> getRechargeHistory(@Body JsonObject jsonObject);

    //TODO MY CGANGE


    @POST("GiftCardCategoryAPI")
    Call<JsonObject> getGiftCardCategories(@Body JsonObject categories, @Header("androidId") String AndroidId);

    @POST("GiftCardCategoryID")
    Call<JsonObject> getGiftCoupons(@Body JsonObject coupons, @Header("androidId") String AndroidId);

    @POST("ProductorBrandAPI")
    Call<JsonObject> getCouponsDetails(@Body JsonObject coupons, @Header("androidId") String AndroidId);

    @POST("SpendAPI")
    Call<JsonObject> createGiftCard(@Body JsonObject card, @Header("androidId") String AndroidId);

    @POST("GetAllProviderStateWise")
    Call<JsonObject> getElectricityProvider(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("ElectricityVerification")
    Call<JsonObject> getElectricityVerification(@Body JsonObject verification, @Header("androidId") String AndroidId);

    @POST("VerifyTransactionPassword")
    Call<JsonObject> verifyTransaction(@Body JsonObject jsonObject, @Header("androidId") String AndroidId);


    @POST("GetBalanceAmount")
    Call<JsonObject> getbalanceAmount(@Body JsonObject amount, @Header("androidId") String AndroidId);

    @POST("InsuranceVerify")
    Call<JsonObject> getInsuranceVerify(@Body JsonObject verification, @Header("androidId") String AndroidId);

    @POST("BroadBandVerify")
    Call<JsonObject> getBroadBandVerify(@Body JsonObject verification, @Header("androidId") String AndroidId);

    @POST("GasPaymentVerification")
    Call<JsonObject> getGasPaymentVerification(@Body JsonObject verification, @Header("androidId") String AndroidId);

    @POST("WaterBillVerification")
    Call<JsonObject> getWaterBillVerification(@Body JsonObject verification, @Header("androidId") String AndroidId);

    @POST("GasBillPayment")
    Call<JsonObject> gasbillPayment(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("WaterBillPayment")
    Call<JsonObject> waterBillPayment(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("Insurance")
    Call<JsonObject> insurancebillPayment(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("BroadBandProvider")
    Call<JsonObject> broadBandBillPayment(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("GetAllProvider")
    Call<JsonObject> getAllProvider(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("ElectricityBillPayment")
    Call<JsonObject> billPayment(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("ForRechargeMobilePrepaid")
    Call<JsonObject> getJioPrepaidRecharge(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("PrepaidRecharge")
    Call<JsonObject> getPrepaidRecharge(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("PostpaidRecharge")
    Call<JsonObject> getPostpaidRecharge(@Body JsonObject object, @Header("androidId") String AndroidId);

    //TODO ABOVE CHANGE

    @POST("GetTransactionStatus")
    Call<ResponseGetTransactionStatus> getTransactionStatus(@Body RequestGetTransactionStatus requestGetTransactionStatus);

    @POST("GetReprint")
    Call<ResponseGetReprintMobile> getReprintMobile(@Body RequestGetReprintMobile requestGetReprint);

    @POST("GetMobileRechargeDone")
    Call<JsonObject> getMobileDthRechargeDone(@Body JsonObject rechargeInput);

    //Dth Recharge
    @POST("DTH")
    Call<JsonObject> getDthRecharge(@Body JsonObject dth, @Header("androidId") String AndroidId);

    // Recent Recharges
    @POST("GetRecentUsers")
    Call<JsonObject> getRecentRecharges(@Body JsonObject jsonObject, @Header("androidId") String AndroidId);

    @POST("GetBillPaymentDoneRequest")
    Call<JsonObject> getPostPaidRec(@Body JsonObject rechargeInput);

    @POST("GetFlightDetailsAPI")
    Call<JsonObject> getFlightDetailsAPI(@Body JsonObject jsonObject);

    @POST("FareRulesAPI")
    Call<JsonObject> getFareRulesAPI(@Body JsonObject jsonObject);

    @POST("BookTicketAPI")
    Call<JsonObject> getBookTicketAPI(@Body JsonObject jsonObject);

    @POST("GetTransactionStatus")
    Call<JsonObject> getTransactionStatusFlightBookingDetail(@Body JsonObject jsonObject);

    @POST("GetSeatMapAfterBooking")
    Call<ResponseSeatMap> getSeatMapAfterBooking(@Body RequestSeatMap requestSeatMap);

    @POST("SeatBook")
    Call<JsonObject> getSeatBook(@Body RequestSeatConfirm requestSeatConfirm);

    @POST("GetTaxAPI")
    Call<JsonObject> getTaxAPI(@Body JsonObject object);

    @POST("get_billing.php")
    Call<JsonObject> getAddress(@Body JsonObject object, @Header("androidId") String AndroidId);

    @GET
    Call<JsonObject> getApi(@Url String url, @Header("androidId") String AndroidId);

    @GET
    Call<JsonObject> getDownline(@Url String url, @Header("androidId") String AndroidId);


    @POST("GetWalletRequestLedger")
    Call<JsonObject> getWalletRequestLedger(@Body JsonObject object, @Header("androidId") String AndroidId);

    //One STore India
    @POST("productgetbycate_id.php")
    Call<JsonObject> getProductDetails(@Body JsonObject object);

    @POST("search.php")
    Call<JsonObject> executesearchProducts(@Body JsonObject object);

    @POST("categories_list.php")
    Call<JsonObject> getCategoryList();


    /*@POST("productbyid_name.php")
    Call<JsonObject> getProductDescription(@Body JsonObject object);*/

    @POST("productby_sku.php")
    Call<JsonObject> getProductDescription(@Body JsonObject object);

    @POST("get_billing.php")
    Call<JsonObject> getAddressList(@Body JsonObject object);

    @POST("shipping-information.php")
    Call<JsonObject> getAddAddress(@Body JsonObject object);

    @POST("updateadd.php")
    Call<JsonObject> getUpdateAddress(@Body JsonObject object);

    @POST("deleteadd.php")
    Call<JsonObject> getRemoveAddress(@Body JsonObject object);

   /* @POST("guest_addtocart.php")
    Call<JsonObject> getGuestAddToCart(@Body JsonObject object);*/

    @POST("guest_addto.php")
    Call<JsonObject> getGuestAddTo(@Body JsonObject object);

   /* @POST("guest_cartitem.php")
    Call<JsonObject> getCartItems(@Body JsonObject object);*/

    @POST("guestcartitem.php")
    Call<JsonObject> getItems(@Body JsonObject object);

    @POST("guest-carts")
    Call<String> getCreateToken();

    @POST("deletecartitem.php")
    Call<JsonObject> getremovecartitems(@Body JsonObject object);

    @POST("guest_cartupdate.php")
    Call<JsonObject> getCartUpdate(@Body JsonObject object);

    @POST("total_order.php")
    Call<JsonObject> getMyOrders(@Body JsonObject object);

    @POST("orderdetail_id.php")
    Call<JsonObject> getMyOrderbyDetails(@Body JsonObject object);

    @POST("dashboard.php")
    Call<JsonObject> getDashboard(@Body JsonObject object);

    @PUT("guest-carts/{token}/order")
    Call<String> executeOrderPlace(@Body JsonObject object, @Path("token") String token);

    @POST("checkout.php")
    Call<JsonObject> executeCheckoutData(@Body JsonObject jsonObject);

    @POST("payment_detail.php")
    Call<String> executePaymentDetails(@Body JsonObject jsonObject);

    @POST("user_register.php")
    Call<JsonObject> userRegistration(@Body JsonObject jsonObject);

    @POST("cancelOrder.php")
    Call<JsonObject> getCancelOrder(@Body JsonObject jsonObject);

    @POST("DebitWallet")
    Call<JsonObject> executeDebitWallet(@Body JsonObject jsonObject, @Header("androidId") String AndroidId);

    @POST("GetWalletLedger")
    Call<JsonObject> getWalletLedger(@Body JsonObject jsonObject, @Header("androidId") String AndroidId);

    @POST("CreateOrder")
    Call<JsonObject> executeCreateOrder(@Body JsonObject jsonObject, @Header("androidId") String AndroidId);

    @POST("CreateOrderV2")
    Call<JsonObject> executeCreateOrderV2(@Body JsonObject jsonObject, @Header("androidId") String AndroidId);


    @POST("GetRechargeHistory")
    Call<JsonObject> getNyRechargeHisory(@Body JsonObject jsonObject);

    @GET
    Call<JsonObject> getCancelParameter(@Url String url);

    @POST()
    Call<JsonObject> trakingLogin(@Url String url);


    @GET()
    Call<JsonObject> trackOrderDetail(@Url String url);


    //DMT

    @POST("CustomerVerification")
    Call<JsonObject> getCustomerVerification(@Body JsonObject requestCustomerVerification, @Header("androidId") String AndroidId);

    @POST("CommissionTransaferToWallet")
    Call<JsonObject> getCommissionTransferToWallet(@Body JsonObject requestCustomerVerification, @Header("androidId") String AndroidId);

    @POST("RemitterRegistration")
    Call<JsonObject> getRemitterRegistration(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("RemitterRegistrationValidationOTP")
    Call<JsonObject> getRemitterRegistrationValidationOTP(@Body JsonObject object, @Header("androidId") String AndroidId);


    @POST("BeneficiaryRegistration")
    Call<JsonObject> getBeneficiaryRegistration(@Body JsonObject object, @Header("androidId") String AndroidId);

    @POST("FundTransfer")
    Call<JsonObject> getFundTransfer(@Body JsonObject transfer, @Header("androidId") String AndroidId);

    @POST("AddBeneficiaryDetils")
    Call<JsonObject> getAddBeneficiaryDetils(@Body JsonObject detils, @Header("androidId") String AndroidId);

    @POST("FundTransferLog")
    Call<JsonObject> getFundTransferLog(@Body JsonObject amount, @Header("androidId") String AndroidId);

    @POST("GetBeneficiaryDetils")
    Call<JsonObject> getBeneficiaryDetils(@Body JsonObject amount, @Header("androidId") String AndroidId);

    @POST("BeneficiaryDelete")
    Call<JsonObject> beneficiarydelete(@Body JsonObject categories, @Header("androidId") String AndroidId);


    @POST("CategoryIDAPI")
    Call<JsonObject> getThemeParkLists(@Body JsonObject categories, @Header("androidId") String AndroidId);

    @POST("ProductAvailability")
    Call<JsonObject> getThemeParkAvailability(@Body JsonObject availability, @Header("androidId") String AndroidId);

    @POST("BookingAPI")
    Call<JsonObject> getThemeParkBooking(@Body JsonObject book, @Header("androidId") String AndroidId);

    @POST("CategoryAPI")
    Call<JsonObject> getThemeParkCategories(@Body JsonObject requestThemePark, @Header("androidId") String AndroidId);

    @POST("GetCustomerReferalmobile")
    Call<JsonObject> getCustomerReferalmobile(@Body JsonObject requestThemePark);


}
