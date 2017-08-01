package com.zzu.ehome.ehomefordoctor.service;

import android.provider.ContactsContract;

import com.zzu.ehome.ehomefordoctor.app.Constans;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Mersens on 2016/9/28.
 */

public interface ServiceStore {
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/MSDoctorLogin"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> login(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/GetToken"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getToken(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/MSUsersBySignDoctorInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getUsersBySign(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/MSDoctorStartOLTime"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> postOnline(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/MSDoctorEndOLTime"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> postOffline(@retrofit2.http.Body String str);


    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/BaseDataInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getBaseData(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/HolterPDFInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getDynamicData(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/GetElectrocardio"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getStaticData(@retrofit2.http.Body String str);


    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/UserInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getUserDate(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/BloodSugarInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getSuggarDate(@retrofit2.http.Body String str);




    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/TreatmentInquirywWithPage"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getJzjlData(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/HealthDataInquiryWithPage"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getSuggarHistory(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/BloodPressureInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getPressDate(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/HealthDataInquiryWithPage"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getPressHistory(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/LithicAcidInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getUaData(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/CholestenoneInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getCholestenoneData(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/HealthDataInquiryWithPage"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getChHistory(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/SendAuthCodeForDoctor"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getCoke(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/MSDoctorFindPass"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> findPsd(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/OCRRecordInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getInspectionReport(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/BloodRoutineInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getInspectionReportDetial(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/VersionInquiryForDoctor"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getUpdata(@retrofit2.http.Body String str);
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/MeidicalReportInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getMeidicalReport(@retrofit2.http.Body String str);

    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/SignInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Call<ResponseBody> getSignData(@retrofit2.http.Body String str);
}
