package com.rarahat02.rarahat02.spl3.ui.activity.musician;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarahat02.rarahat02.spl3.Utils;
import com.rarahat02.rarahat02.spl3.ui.activity.musician.sqlite_login.activities.SqliteUsersListActivity;
import com.rarahat02.rarahat02.spl3.ui.fragments.BaseActivity;

import com.github.rarahat02.instamaterial.R;

import com.rarahat02.rarahat02.spl3.ui.activity.MainActivity;
//import MusicianHome.R;

//import com.afollestad.materialdialogssample.*;

public class MusicianHome extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener
{


    private  String artistId, password, artistName;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String ArtistName = "name";
    public static final String ArtistId = "id";
    public static final String ArtistPassword = "password";


    @Override
    protected void onCreate(Bundle savedIntanceState)
    {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_musician_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();

        if (extras != null)
        {
            this.artistId = extras.getString("artistId");
            this.password = extras.getString("password");
            this.artistName = extras.getString("artistName");


            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(ArtistId, artistId);
            editor.putString(ArtistPassword, password);
            editor.putString(ArtistName, artistName);
            editor.commit();

        }




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);
        TextView artistNameText = (TextView)hView.findViewById(R.id.musicianName);

        ImageView artistProfilePic = (ImageView) hView.findViewById(R.id.musicianProfilePic);

        SharedPreferences receiver = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        artistNameText.setText(receiver.getString(ArtistName, null));

        new Utils.ImageLoadTask(receiver.getString(MainActivity.UserPicUrl, null), artistProfilePic).execute();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.musician_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            // Handle the camera action

        }
        else if (id == R.id.encrypt_file)
        {


            Intent accountsIntent = new Intent(getApplicationContext(), SqliteUsersListActivity.class);
            accountsIntent.putExtra("EMAIL", artistId);
            //emptyInputEditText();
            startActivity(accountsIntent);

            //com.afollestad.materialdialogssample.MyPlayground.encryptDialog(this);

            /* Intent intent = new Intent(getApplicationContext(), com.yarolegovich.slidingrootnav.sample.fragment.audioplayer.StartActivity.class);
            startActivity(intent);*/
        }
/*        else if (id == R.id.upload_song)
        {
            Intent intent = new Intent(getApplicationContext(), PublishActivity.class);
            startActivity(intent);
        }*/
        else if (id == R.id.upload_song)
        {

            SharedPreferences receiver = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

            Intent intent = new Intent(getApplicationContext(), StepActivity.class);
            intent.putExtra("artistId", receiver.getString(ArtistId, null));
            intent.putExtra("password", receiver.getString(ArtistPassword, null));
            startActivity(intent);

        }
        else if (id == R.id.profile)
        {
            /*            Intent intent = new Intent(getApplicationContext(), SqliteLoginActivity.class);
            startActivity(intent);*/
        }
        else if (id == R.id.logout)
        {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
