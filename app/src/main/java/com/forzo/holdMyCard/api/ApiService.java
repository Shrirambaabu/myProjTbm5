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

/**
 * Created by Shriram on 4/6/2018.
 */

public interface ApiService {



    @GET("user/getUserNotes")
    Observable<List<MyNotes>> getUserNotes();

    @GET("user/getUserRemainder")
    Observable<List<MyRemainder>> getUserRemainder();

    @GET("user/getUserLibrary")
    Observable<List<MyLibrary>> getUserLibrary();


    @POST("user/saveBusinessCard")
    Observable<BusinessCard> saveBusinessCard(@Body BusinessCard businessCard);



    @POST("user/saveNotes")
    Observable<MyNotes> saveNotes(@Body MyNotes myNotes);

    @POST("user/saveRemainder")
    Observable<MyRemainder> saveRemainder(@Body MyRemainder myRemainder);


}
