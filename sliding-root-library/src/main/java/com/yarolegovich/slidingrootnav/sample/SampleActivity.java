package com.yarolegovich.slidingrootnav.sample;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.sample.fragment.BuyNewSongFragment;
import com.yarolegovich.slidingrootnav.sample.fragment.EncryptFragment;
import com.yarolegovich.slidingrootnav.sample.menu.DrawerAdapter;
import com.yarolegovich.slidingrootnav.sample.menu.DrawerItem;
import com.yarolegovich.slidingrootnav.sample.menu.SimpleItem;
import com.yarolegovich.slidingrootnav.sample.menu.SpaceItem;
import com.yarolegovich.slidingrootnav.sample.fragment.CenteredTextFragment;

import java.util.Arrays;
import com.yarolegovich.slidingrootnav.sample.R;

/**
 * Created by yarolegovich on 25.03.2017.
 */

public class SampleActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_ENCRYPT = 1;
    private static final int POS_PUBLISH = 2;
    private static final int POS_PLAYER = 3;
    private static final int POS_SETTINGS = 4;
    private static final int POS_LOGOUT = 5;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ENCRYPT),
                createItemFor(POS_PUBLISH),
                createItemFor(POS_PLAYER),
                createItemFor(POS_SETTINGS),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            finish();
        }
        /*else if(position == POS_ENCRYPT)
        {

            Fragment encryptScreen = EncryptFragment.createFor(screenTitles[position]);
            //showFragment(encryptScreen);
        }
        else if(position == POS_PLAYER)
        {
*//*            Intent intent = new Intent(SampleActivity.this, com.yarolegovich.slidingrootnav.sample.fragment.audioplayer.MainActivity.class);
            startActivity(intent);*//*
            //Fragment selectedScreen = com.yarolegovich.slidingrootnav.sample.fragment.audioplayer.MainActivity.createFor(screenTitles[position]);


            Fragment playerScreen = com.yarolegovich.slidingrootnav.sample.fragment.audioplayer.MainFragment.createFor(screenTitles[position]);
            //showFragment(playerScreen);
        }
        else if(position == POS_PUBLISH)
        {
            Fragment buySongScreen = BuyNewSongFragment.createFor(screenTitles[position]);
            //showFragment(buySongScreen);
        }
        else
        {
            Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
            //showFragment(selectedScreen);
        }*/
    }

    private void showFragment(Fragment fragment)
    {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        // ekhane problem er karone sob kichu nosto. eita thik korte parle sob ok



    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                //.withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
}
