package com.rarahat02.rarahat02.spl3.ui.fragments;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindDimen;
import butterknife.BindString;
import com.github.rarahat02.instamaterial.R;
import com.rarahat02.rarahat02.spl3.ui.activity.UserProfileActivity;
import com.rarahat02.rarahat02.spl3.ui.activity.musician.sqlite_login.activities.SqliteLoginActivity;
import com.yarolegovich.slidingrootnav.sample.*;

import static com.afollestad.materialdialogssample.MyPlayground.purchasePermissionDialog;
import static com.afollestad.materialdialogssample.MyPlayground.syncPermissionDialog;

/**
 * Created by Miroslaw Stanek on 15.07.15.
 */
public class BaseDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    //@BindView(R.id.drawerLayout)
    public  static DrawerLayout drawerLayout;
    @BindView(R.id.vNavigation)
    NavigationView vNavigation;

    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;
    @BindString(R.string.user_profile_photo)
    String profilePhoto;

    //Cannot be bound via Butterknife, hosting view is initialized later (see setupHeader() method)
    protected ImageView ivMenuUserPhoto;
    protected TextView userFullName;


    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentViewWithoutInject(R.layout.activity_drawer);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        bindViews();
        setupHeader();

        setupOtherMenuItems();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    private void setupHeader() {
        View headerView = vNavigation.getHeaderView(0);
        ivMenuUserPhoto = (ImageView) headerView.findViewById(R.id.ivMenuUserPhoto);
        userFullName = (TextView) headerView.findViewById(R.id.userFullName);

        headerView.findViewById(R.id.vGlobalMenuHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGlobalMenuHeaderClick(v);
            }
        });

/*        Picasso.with(this)
                .load(profilePhoto)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivMenuUserProfilePhoto);*/
    }

    public void setupOtherMenuItems()
    {

        NavigationView navigationView = (NavigationView) findViewById(R.id.vNavigation);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_search_musicians)
        {

            //drawerLayout.closeDrawer(Gravity.LEFT);
            Intent intent = new Intent(getApplicationContext(), com.stone.transition.StartActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.musician_mode)
        {

            Intent intent = new Intent(getApplicationContext(), SqliteLoginActivity.class);
            //Intent intent = new Intent(getApplicationContext(), MusicianHome.class);
            //Intent intent = new Intent(getApplicationContext(), TestActivity.class);
            startActivity(intent);
            //Toast.makeText(this, " popular clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.menu_song_search)
        {

/*            Intent intent = new Intent(getApplicationContext(), MoreArtistActivity.class);
            startActivity(intent);*/
        }
        else if (id == R.id.menu_buy_new_song)
        {
            purchasePermissionDialog(this);
            //com.yarolegovich.slidingrootnav.sample.OpenFileExplorer.openExplorer(this, "empty jinish", ConstantClass.BUY_PURPOSE);
        }
        else if (id == R.id.menu_verify_song_development_purpose)
        {
            com.yarolegovich.slidingrootnav.sample.OpenFileExplorer.openExplorer(this, "empty jinish", ConstantClass.VERIFY_PURPOSE);
        }
        else if (id == R.id.sync_files)
        {
            syncPermissionDialog(this);
            //com.yarolegovich.slidingrootnav.sample.OpenFileExplorer.backgroundOpenExplorer(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onGlobalMenuHeaderClick(final View v)
    {
        drawerLayout.closeDrawer(Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                overridePendingTransition(0, 0);
            }
        }, 200);
    }




}
