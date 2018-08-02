package com.forzo.holdMyCard.api;

import com.forzo.holdMyCard.ui.models.BusinessCard;
import com.forzo.holdMyCard.ui.models.Groups;
import com.forzo.holdMyCard.ui.models.MyGroups;
import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.models.MyNotes;
import com.forzo.holdMyCard.ui.models.MyRemainder;
import com.forzo.holdMyCard.ui.models.User;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
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

    @GET("remainder/showRemainderDetail/{remainderId}")
    Observable<MyRemainder> getUserRemainderDetails(@Path("remainderId") String userId);

    @GET("remainder/showAllRemainder/{userId}")
    Observable<List<MyRemainder>> getUserRemainder(@Path("userId") String userId);

    @GET("profile/showMyGroup/{userId}")
    Observable<List<MyGroups>> getMyGroup(@Path("userId") String userId);

    @GET("profile/deleteGroup/{libraryGroupNameId} ")
    Observable<MyGroups> deleteMyGroup(@Path("libraryGroupNameId") String libraryGroupNameId);

    @GET("profile/renameGroup/{groupId}/{newGroupName} ")
    Observable<MyGroups> renameMyGroup(@Path("groupId") String userId, @Path("newGroupName") String groupName);

    @GET("profile/deleteGroupMember/{libraryContactId}/{libraryGroupNameId} ")
    Observable<MyGroups> deleteGroupMember(@Path("libraryContactId") String libraryContactId, @Path("libraryGroupNameId") String libraryGroupNameId);

    @GET("profile/addGroupMember/{libraryOwnerId}/{libraryContactId}/{libraryGroupNameId}")
    Observable<MyGroups> addGroupMember(@Path("libraryOwnerId") String libraryOwnerId, @Path("libraryContactId") String libraryContactId, @Path("libraryGroupNameId") String libraryGroupNameId);

    @GET("profile/showAllLibrary/{userId}")
    Observable<List<MyLibrary>> getUserLibrary(@Path("userId") String userId);

    @GET("profile/getEnabled/{userId}")
    Observable<User> getUserStatus(@Path("userId") String userId);

    @GET("profile/{userId}")
    Observable<BusinessCard> getUserProfile(@Path("userId") String userId);

    @GET("profile/{userId}")
    Single<BusinessCard> getUserProfileData(@Path("userId") String userId);

    @GET("profile/showProfileImagesByImageType/{userId}/{imageType}")
    Observable<List<BusinessCard>> getUserProfileImages(@Path("userId") String userId, @Path("imageType") String imageType);

    @GET("profile/showProfileImagesByImageType/{userId}/{imageType}")
    Observable<List<Groups>> getUserProfileImagesGroup(@Path("userId") String userId, @Path("imageType") String imageType);


    @POST("login/registerUserUsingUserId")
    Observable<User> registerToHmc(@Body User user);

    @POST("login/checkRegisteredUsingEmail")
    @FormUrlEncoded
    Observable<User> checkExistingUser(@Field("email") String email);

    @POST("login/forgotPassword")
    @FormUrlEncoded
    Observable<User> forgetPassword(@Field("email") String email);

    @POST("login/loginUsingUserId")
    @FormUrlEncoded
    Observable<User> loginToHmc(@Field("userId") String userId, @Field("email") String email, @Field("password") String password);

    @POST("login/loginUsingUserEmail")
    @FormUrlEncoded
    Observable<User> loginToApp(@Field("email") String email, @Field("password") String password);

    @POST("profile/registerProfileToLibrary")
    Observable<BusinessCard> saveBusinessCard(@Body BusinessCard businessCard);

    @POST("profile/updateProfile")
    Observable<BusinessCard> updateBusinessCard(@Body BusinessCard businessCard);

    @GET("profile/deleteProfile/{profileId}")
    Observable<BusinessCard> deleteBusinessCard(@Path("profileId") String userId);

    @POST("profile/checkUIUDExists")
    Observable<User> checkUuid(@Body User user);


    @GET("login/registerNewUser")
    Observable<User> newUser();


    @POST("login/registerUser")
    Observable<User> registerUser(@Body User user);

    @POST("notes/createNotes")
    Observable<MyNotes> saveNotes(@Body MyNotes myNotes);

    @POST("notes/updateNotes")
    Observable<MyNotes> updateNotes(@Body MyNotes myNotes);


    @POST("profile/registerProfileToOurLibrary ")
    Observable<Groups> createGroupName(@Body Groups groups);

    @POST("profile/createGroup")
    Observable<Groups> createGroup(@Body Groups groups);


    @GET("profile/showMyGroupWithDetail/{libraryGroupNameId}")
    Observable<List<MyLibrary>> showMyGroupWithDetail(@Path("libraryGroupNameId") String userId);


    @GET("notes/deleteNotes/{notesId}")
    Observable<MyNotes> deleteUserNotes(@Path("notesId") String userId);


    @POST("remainder/updateFcmToken")
    @FormUrlEncoded
    Observable<Response<MyRemainder>> updateFcm(@Field("userId") String userId, @Field("token") String token);

    @POST("profile/postImageByName")
    @FormUrlEncoded
    Observable<BusinessCard> postQRImage(@Field("userId") String userId, @Field("imageType") String imageType, @Field("imageName") String imageName);

    @POST("remainder/createRemainder")
    Observable<MyRemainder> saveRemainder(@Body MyRemainder myRemainder);


    @POST("remainder/updateRemainder")
    Observable<MyRemainder> updateReminder(@Body MyRemainder myRemainder);


    @GET("remainder/deleteRemainder/{remainderId}")
    Observable<MyRemainder> deleteUserReminder(@Path("remainderId") String userId);


    @POST("profile/postImage")
    @Multipart
    Observable<BusinessCard> postUserImage(@Part MultipartBody.Part file, @Part("userId") int userId, @Part("imageType") String imageType);

    @POST("profile/updateImage")
    @Multipart
    Observable<BusinessCard> updateUserImage(@Part("userId") int userId, @Part("imageType") String imageType, @Part MultipartBody.Part file);


}
