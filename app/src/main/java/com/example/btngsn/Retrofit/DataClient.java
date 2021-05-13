package com.example.btngsn.Retrofit;

import com.example.btngsn.Model.Listing;
import com.example.btngsn.Model.User;
import com.example.btngsn.Model.viewDirection;
import com.example.btngsn.Model.viewForm;
import com.example.btngsn.Model.viewSpecies;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {

    @Multipart
    @POST("uploadimage.php")
    Call<String> UpLoadImage(@Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("loadfile.php")
    Call<String> sendAnh (@Field("hinhanh") String hinhanh);

    @FormUrlEncoded
    @POST("registration.php")
    Call<String> Registration (@Field("fullName") String fullName,
                                    @Field("userName") String userName,
                                    @Field("passWord") String passWord,
                                    @Field("numberPhone") String numberPhone,
                                    @Field("Email") String Email,
                                    @Field("idspUser") String idspUser,
                                    @Field("CMND") String CMND
        );

    @FormUrlEncoded
    @POST("login.php")
    Call<List<User>> Login (@Field("userName") String userName,
                            @Field("passWord") String passWord
    );

    @FormUrlEncoded
    @POST("getListing.php")
    Call<List<Listing>> getListing (@Field("idForm") String idForm );

    @GET("getForm.php")
    Call<List<viewForm>> getForm();

    @GET("getSpecies.php")
    Call<List<viewSpecies>> getSpecies();

    @GET("getDirection.php")
    Call<List<viewDirection>> getDirection();


}
