package com.example.btngsn.Retrofit;

import com.example.btngsn.Model.Favorite;
import com.example.btngsn.Model.Listing;
import com.example.btngsn.Model.User;
import com.example.btngsn.Model.viewDirection;
import com.example.btngsn.Model.viewForm;
import com.example.btngsn.Model.viewSpecies;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {

    @Multipart
    @POST("uploadimage.php")
    Call<String> UpLoadImage(@Part MultipartBody.Part image);

    @Multipart
    @POST("postimage.php")
    Call<ResponseBody> uploadMultiple(
            @Part("size") RequestBody size,
            @Part List<MultipartBody.Part> files);


    @FormUrlEncoded
    @POST("postFavorite.php")
    Call<String> PostFavorit(@Field("idUser") String idUser,
                             @Field("idListing") String idListing
    );

    @GET("deleteFavorite.php")
    Call<String> deleteFavorite (@Query("idListing") String idListing,
                                @Query("idUser") String idUser);
    @FormUrlEncoded
    @POST("getFavorite.php")
    Call<List<Favorite>> getFavorite(@Field("idUser") String idUser,
                                     @Field("idListing") String idListing);
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

    @GET("updateInfor.php")
    Call<String> updateInfor (@Query("idUser") String idUser,
                             @Query("fullName") String fullName,
                             @Query("numberPhone") String numberPhone,
                              @Query("Email") String Email,
                              @Query("CMND") String CMND,
                              @Query("address") String address,
                              @Query("linkFace") String linkFace,
                              @Query("linkWeb") String linkWeb,
                              @Query("sex") int sex
    );


    @GET("deleteListing.php")
    Call<String> deleteListing (@Query("idListing") String idListing,
                              @Query("image") String image);

    @FormUrlEncoded
    @POST("getListing.php")
    Call<List<Listing>> getListing(@Field("idForm") String idForm);

    @FormUrlEncoded
    @POST("getListingForId.php")
    Call<List<Listing>> getListingForId(@Field("idUser") String idUser);


    @GET("getListingForAdmin.php")
    Call<List<Listing>> getListingForAdmin();

    @GET("updateStatus.php")
    Call<String> updaeStatus (@Query("idListing") String idListing,
                              @Query("trangthai") String trangthai);

    @GET("getForm.php")
    Call<List<viewForm>> getForm();

    @GET("getSpecies.php")
    Call<List<viewSpecies>> getSpecies();

    @GET("getDirection.php")
    Call<List<viewDirection>> getDirection();

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
                             @Field("emailContact") String emailContact,
                             @Field("idUser") String idUser,
                             @Field("dateStart") String dateStart,
                             @Field("dateEnd") String dateEnd,
                             @Field("loaitin") String loaitin,
                             @Field("urlMap") String urlMap

    );
}
