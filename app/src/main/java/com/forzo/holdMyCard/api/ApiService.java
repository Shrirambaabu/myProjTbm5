package com.forzo.holdMyCard.api;

import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.models.MyNotes;
import com.forzo.holdMyCard.ui.models.MyRemainder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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


    @POST("profile/registerProfile")
    Observable<BusinessCard> saveBusinessCard(@Body BusinessCard businessCard);



    @POST("notes/createNotes")
    Observable<MyNotes> saveNotes(@Body MyNotes myNotes);

    @POST("remainder/createRemainder")
    Observable<MyRemainder> saveRemainder(@Body MyRemainder myRemainder);

    @GET("user/getPaymentCurrentFromUserId/{userId}")
    Observable<List<MyLibrary>> getMyLibrary(@Path("userId") String userId);


}
