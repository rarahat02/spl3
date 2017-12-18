package com.rarahat02.rarahat02.spl3.ui.activity.login;

/**
 * Created by Karthik's on 27-02-2016.
 */
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

//import com.karthik.kenburnsview.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.github.rarahat02.instamaterial.R;
import com.rarahat02.rarahat02.spl3.ui.activity.MainActivity;

public class SplashActivity extends Activity
{
    private static final String TAG = "AndroidBash";
    public User user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;

    String projectUrl = "https://console.firebase.google.com/project/instamaterial-master-64217/";
    
    Firebase mRef = new Firebase(projectUrl + "authentication/users");

    LoginButton loginButton;

    
    //FaceBook callbackManager
    private CallbackManager callbackManager;




    //SharedPreferences sharedpreferences;


    // Splash screen timer
    //private static int SPLASH_TIME_OUT = 10000;
    //private KenBurnsView mKenBurns;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setAnimation();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        //mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        //mKenBurns.setImageResource(R.drawable.taylor_concert1);




        /*CircularProgressButton progressButtonSignUp =
                (CircularProgressButton) findViewById(R.id.progress_btn_no_padding_sign_up);
        progressButtonSignUp.setOnClickListener(view ->
        {
            Intent intent = new Intent(this, FiftyShadesActivity.class);
            //startActivity(intent);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {

                progressButtonSignUp.startAnimation();

                Runnable runnable = () ->
                {
                    progressButtonSignUp.stopAnimation();

                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                            this,
                            new Pair<>(findViewById(R.id.progress_btn_no_padding_sign_up), "transition"));

                    startActivity(intent, activityOptions.toBundle());

                };

                new Handler().postDelayed(runnable, 3000);
*//*                Intent intentFeed = new Intent(this, FeedFragment.class);
                startActivity(intentFeed);*//*
            }
        });
*/






     /*   CircularProgressButton progressButtonSignUpFacebook =
                (CircularProgressButton) findViewById(R.id.progress_btn_no_padding_continue_facebook);
        progressButtonSignUpFacebook.setOnClickListener(view -> {
            Intent intent = new Intent(this, FeedFragment.class);
            //startActivity(intent);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                progressButtonSignUpFacebook.startAnimation();

                Runnable runnable = () -> {
                    progressButtonSignUpFacebook.stopAnimation();

                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                            this,
                            new Pair<>(findViewById(R.id.progress_btn_no_padding_continue_facebook), "transition"));

                    startActivity(intent, activityOptions.toBundle());

                };

                new Handler().postDelayed(runnable, 100);
            }
        });*/






        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser != null)
        {
            // User is signed in
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            String uid = mAuth.getCurrentUser().getUid();
            String image=mAuth.getCurrentUser().getPhotoUrl().toString();
            intent.putExtra("user_id", uid);
            if(image != null || image != "")
            {
                intent.putExtra("profile_picture",image);
            }
            startActivity(intent);
            finish();
            Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

        //FaceBook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });


    }
    public void onClick(View v) {
        if (v == findViewById(R.id.fb_button)) {
            loginButton.performClick();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    //FaceBook
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    //

    protected void setUpUser() {
        user = new User();
    }


    private void signInWithFacebook(AccessToken token) {
        Log.d(TAG, "signInWithFacebook:" + token);

        showProgressDialog();


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SplashActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            //Log.v("1stTime", "true");
                            SharedPreferences settings = getSharedPreferences(MainActivity.MyPREFERENCES, MODE_PRIVATE);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                            if(settings.getBoolean("hasLoggedIn", false))
                            {
                                Log.v("1stTime", "true");
                            }
                            else
                            {
                                Log.v("1stTime", "false");
                                String uid =  task.getResult().getUser().getUid();
                                String name=task.getResult().getUser().getDisplayName();
                                String email=task.getResult().getUser().getEmail();
                                String image=task.getResult().getUser().getPhotoUrl().toString();
                                //String phoneNumber = task.getResult().getUser().getPhoneNumber();

                                User user = new User(uid,name,null,email,null);
                                mRef.child(uid).setValue(user);
                                
                                

                                settings = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = settings.edit();

                                editor.putString(MainActivity.UserPicUrl, image);
                                editor.putString(MainActivity.customerFbName, name);
                                editor.putString(MainActivity.customerFbId, uid);
                                editor.commit();

                            }
                            startActivity(intent);
                            finish();


                        }

                        hideProgressDialog();
                    }
                });
    }



    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }




































    private void setAnimation() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();

        //findViewById(R.id.imagelogo).setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        //findViewById(R.id.imagelogo).startAnimation(anim);
    }
    class Test
    {
        public String id;
        public String name;


        public Test()
        {

        }
    }
}
