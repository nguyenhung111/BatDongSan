package com.example.btngsn.Retrofit;

import com.example.btngsn.Model.Listing;
import com.example.btngsn.Model.User;
import com.example.btngsn.Model.test;
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
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataClient {

    @Multipart
    @POST("uploadimage.php")
    Call<String> UpLoadImage(@Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("loadfile.php")
    Call<String> sendAnh(@Field("hinhanh") String hinhanh);

    @FormUrlEncoded
    @POST("registration.php")
    Call<String> Registration(@Field("fullName") String fullName,
                              @Field("userName") String userName,
                              @Field("passWord") String passWord,
                              @Field("numberPhone") String numberPhone,
                              @Field("Email") String Email,
                              @Field("idspUser") String idspUser,
                              @Field("CMND") String CMND
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<List<User>> Login(@Field("userName") String userName,
                           @Field("passWord") String passWord
    );


    @GET("updatePass.php")
    Call<String> updatePass (@Query("userName") String userName,
                                @Query("passWord") String passWord,
                                @Query("pass") String pass
    );

    @FormUrlEncoded
    @POST("getListing.php")
    Call<List<Listing>> getListing(@Field("idForm") String idForm);

    @GET("getForm.php")
    Call<List<viewForm>> getForm();

    @GET("getSpecies.php")
    Call<List<viewSpecies>> getSpecies();

    @GET("getDirection.php")
    Call<List<viewDirection>> getDirection();

    @GET("image.php")
    Call<List<test>> getAnh();

    @FormUrlEncoded
    @POST("postListing.php")
    Call<String> PostListing(@Field("title") String title,
                             @Field("idForm") String idForm,
                             @Field("idSpecies") String idSpecies,
                             @Field("acreage") String acreage,
                             @Field("price") String price,
                             @Field("address") String address,
                             @Field("addressDetail") String addressDetail,
                             @Field("image") String image,
                             @Field("description") String description,
                             @Field("floors") String floors,
                             @Field("bedroom") String bedroom,
                             @Field("toilet") String toilet,
                             @Field("directionHouse") String directionHouse,
                             @Field("directionBancoly") String directionBancoly,
                             @Field("furniture") String furniture,
                             @Field("juridical") String juridical,
                             @Field("nameContact") String nameContact,
                             @Field("phoneContact") String phoneContact,
                             @Field("idUser") String idUser,
                             @Field("dateStart") String dateStart,
                             @Field("dateEnd") String dateEnd,
                             @Field("emailContact") String emailContact,
                             @Field("loaitin") String loaitin,
                             @Field("urlMap") String urlMap

    );
}
