package com.rarahat02.rarahat02.spl3.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.afollestad.materialdialogssample.MyPlayground;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.rarahat02.rarahat02.spl3.Utils;
import com.rarahat02.rarahat02.spl3.ui.fragments.BaseDrawerActivity;
import com.rarahat02.rarahat02.spl3.ui.home.SnapFragment;
import com.rarahat02.rarahat02.spl3.ui.home_tab_layout.ViewPager.CustomTabActivity;
import com.rarahat02.rarahat02.spl3.ui.song_search.fragment.BaseExampleFragment;
import com.rarahat02.rarahat02.spl3.ui.song_search.fragment.SlidingSearchResultsExampleFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.rarahat02.instamaterial.R;

import com.rarahat02.rarahat02.spl3.ui.fragments.FeedFragment;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by iiro on 7.6.2016.
 */
public class MainActivity extends BaseDrawerActivity implements BaseExampleFragment.BaseExampleFragmentCallbacks
{


    private FirebaseAuth mAuth;
    private Firebase myFirebaseRef;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;


    private String fbId = "";
    private String fbName = "";

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String UserPicUrl = "userPicUrl";


    public static final String customerFbName = "customerFbName";
    public static final String customerFbId = "customerFbId";

/*    public static final String customerPhoneNumber = "phoneNoCustomer";
    public static final String customerPassword = "customerPassword";*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_changing_tabs);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);




        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener()
        {
            @Override
            public void onTabSelected(@IdRes int tabId)
            {

                Fragment selectedFragment = null;
                switch (tabId)
                {

                    case R.id.tab_home:
                        selectedFragment = SnapFragment.newInstance();
                        break;
/*                    case R.id.tab_home:
                        selectedFragment = HorizontalActivity.newInstance();
                        break;*/
                    case R.id.tab_feed:
                        selectedFragment = FeedFragment.newInstance();
                        break;
                    case R.id.tab_player:
                        selectedFragment = CustomTabActivity.newInstance();
                        break;
                    case R.id.tab_search:
                        //selectedFragment = MoreArtistActivity.newInstance();
                        selectedFragment = SlidingSearchResultsExampleFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
            }
        });



        myFirebaseRef = new Firebase("https://console.firebase.google.com/project/instamaterial-master-64217/authentication/users");
        mAuth = FirebaseAuth.getInstance();
    }


    protected void onStart()
    {
        super.onStart();


        SharedPreferences receiver = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        if(receiver.getBoolean("hasLoggedIn", false))   //previously logged in
        {

        }
        else
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("hasLoggedIn", true);
            editor.commit();


            this.fbId = receiver.getString(customerFbId, null);
            this.fbName = receiver.getString(customerFbName, null);

            if (checkAndRequestPermissions())
                MyPlayground.getCustomerInitialInfoDialog(this, this.fbId, this.fbName);


        }

        new Utils.ImageLoadTask(receiver.getString(UserPicUrl, null), ivMenuUserPhoto).execute();
        userFullName.setText(receiver.getString(customerFbName, null));

    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.menu_logout)
        {
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            finish();
/*            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);*/
        }
        super.onNavigationItemSelected(item);
        return true;
    }

    @Override
    public void onAttachSearchViewToDrawer(FloatingSearchView searchView)
    {
        searchView.attachNavigationDrawerToMenuButton(drawerLayout);
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

    private boolean checkAndRequestPermissions()
    {
        if (SDK_INT >= Build.VERSION_CODES.M)
        {
            int permissionReadPhoneState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
            int permissionStorage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            List<String> listPermissionsNeeded = new ArrayList<>();

            if (permissionReadPhoneState != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(android.Manifest.permission.READ_PHONE_STATE);
            }

            if (permissionStorage != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty())
            {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
            else
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        String TAG = "LOG_PERMISSION";
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions

                    if (perms.get(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            )
                    {
                        Log.d(TAG, "Phone state and storage permissions granted");

                        MyPlayground.getCustomerInitialInfoDialog(this, this.fbId, this.fbName);
                    }
                    else
                    {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                      //shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE)) {
                            showDialogOK("Phone state and storage permissions required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }
    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

}