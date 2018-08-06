package com.forzo.holdMyCard.ui.fragments.myregister;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.forzo.holdMyCard.R;
import com.forzo.holdMyCard.base.FragmentContext;
import com.forzo.holdMyCard.ui.fragments.mylogin.MyLoginPresenter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyRegister extends Fragment implements MyRegisterContract.View {


    @BindView(R.id.textInputEditTextName)
    TextInputEditText textInputEditTextName;
    @BindView(R.id.textInputEditTextEmail)
    TextInputEditText textInputEditTextEmail;
    @BindView(R.id.textInputEditTextPassword)
    TextInputEditText textInputEditTextPassword;
    @BindView(R.id.textInputEditTextConfirmPassword)
    TextInputEditText textInputEditTextConfirmPassword;
    @BindView(R.id.relative_progress)
    RelativeLayout relativeLayout;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avLoadingIndicatorView;

    @Inject
    MyRegisterPresenter myRegisterPresenter;

//    @BindView(R.id.fb_button)
//    LoginButton loginButton;
    CallbackManager callbackManager;
    private String TAG = "Register";


    public MyRegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        View view = inflater.inflate(R.layout.fragment_my_register, container, false);
        ButterKnife.bind(MyRegister.this, view);
        myRegisterPresenter.attach(this);


//        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//                Log.e("Success", "" + loginResult.getAccessToken().getToken());
//                Log.e("Success", "" + loginResult.getAccessToken().getUserId());
//
//
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject me, GraphResponse response) {
//                                if (response.getError() != null) {
//                                    // handle error
//                                } else {
//                                    // get email and id of the user
//                                    String email = me.optString("email");
//                                    String id = me.optString("id");
//                                    Log.e("SuccessEmail", "" + email);
//                                    Log.e("SuccessId", "" + id);
//                                    Log.e("SuccessJSON", "" + me);
//                                    try {
//                                        Log.e("SuccessJSON", "" + me.getString("email"));
//
//                                        textInputEditTextEmail.setText(me.getString("email"));
//                                        textInputEditTextName.setText(me.getString("first_name") + " " + me.getString("last_name"));
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    Bundle bFacebookData = getFacebookData(me);
//
//
//                                }
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
//                request.setParameters(parameters);
//                request.executeAsync();
//
//            }
//
//            @Override
//            public void onCancel() {
//
//                Log.e("Cancelled", "Not Login");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.e("Error", "Error");
//            }
//        });

        // Inflate the layout for this fragment
        return view;
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));


            return bundle;
        } catch (JSONException e) {
            Log.d(TAG, "Error parsing JSON");
        }

        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DaggerMyRegisterComponent.builder()
                .fragmentContext(new FragmentContext(context))
                .build()
                .inject(this);
    }

    @OnClick(R.id.register_side_button)
    public void registerButton() {

        if (textInputEditTextName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please Enter the Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (textInputEditTextEmail.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please Enter the Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (textInputEditTextPassword.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please Enter the Password", Toast.LENGTH_LONG).show();
            return;
        }
        if (textInputEditTextConfirmPassword.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please Enter the Password", Toast.LENGTH_LONG).show();
            return;
        }
        if (!textInputEditTextConfirmPassword.getText().toString().equals(textInputEditTextPassword.getText().toString())) {
            Toast.makeText(getActivity(), "Password mismatch.", Toast.LENGTH_LONG).show();
            return;
        }

        myRegisterPresenter.checkExistingUser(textInputEditTextEmail.getText().toString());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        myRegisterPresenter.detach();
    }

    @Override
    public void showLoader() {
        relativeLayout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void hideLoader() {
        relativeLayout.setVisibility(View.GONE);
        avLoadingIndicatorView.setVisibility(View.GONE);
        avLoadingIndicatorView.hide();
    }

    @Override
    public void loginSuccess() {
        textInputEditTextName.setText("");
        textInputEditTextEmail.setText("");
        textInputEditTextPassword.setText("");
        textInputEditTextConfirmPassword.setText("");

        myRegisterPresenter.regSuccess();

    }

    @Override
    public void existingUserStatus(String status) {

        if (status.equals("false")) {
            myRegisterPresenter.registerToHmc(textInputEditTextName.getText().toString(), textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString(), textInputEditTextConfirmPassword.getText().toString());
        }else {
         //   Toast.makeText(getActivity(),"Email Already Registered",Toast.LENGTH_LONG).show();
            myRegisterPresenter.emailAlreadyRegistered();
        }
    }
}
