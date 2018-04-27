package com.forzo.holdMyCard.api;

import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.models.MyNotes;
import com.forzo.holdMyCard.ui.models.MyRemainder;
import com.forzo.holdMyCard.ui.models.User;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Shriram on 4/6/2018.
 */

public interface ApiService {


    @GET("notes/showAllNotes/{userId}")
    Observable<List<MyNotes>> getUserNotes(@Path("userId") String userId);

    @GET("remainder/showAllRemainder/{userId}")
    Observable<List<MyRemainder>> getUserRemainder(@Path("userId") String userId);

    @GET("profile/showAllLibrary/{userId}")
    Observable<List<MyLibrary>> getUserLibrary(@Path("userId") String userId);

    @GET("profile/{userId}")
    Observable<BusinessCard> getUserProfile(@Path("userId") String userId);


    @POST("profile/registerProfileToLibrary")
    Observable<BusinessCard> saveBusinessCard(@Body BusinessCard businessCard);

    @POST("profile/checkUIUDExists")
    Observable<User> checkUuid(@Body User user);


    @GET("login/registerNewUser")
    Observable<User> newUser();


    @POST("notes/createNotes")
    Observable<MyNotes> saveNotes(@Body MyNotes myNotes);

    @POST("notes/updateNotes")
    Observable<MyNotes> updateNotes(@Body MyNotes myNotes);


    @GET("notes/deleteNotes/{notesId}")
    Observable<MyNotes> deleteUserNotes(@Path("notesId") String userId);



    @POST("remainder/updateFcmToken ")
    Observable<MyRemainder> updateFcm(@Body MyRemainder myRemainder);

    @POST("remainder/createRemainder")
    Observable<MyRemainder> saveRemainder(@Body MyRemainder myRemainder);



    @POST("remainder/updateRemainder")
    Observable<MyRemainder> updateReminder(@Body MyRemainder myRemainder);



    @GET("remainder/deleteRemainder/{remainderId}")
    Observable<MyRemainder> deleteUserReminder(@Path("remainderId") String userId);


    @POST("profile/postImage")
    @Multipart
    Observable<BusinessCard> postUserImage(@Part MultipartBody.Part  file, @Part("userId") int userId, @Part("imageType") String imageType);


}
