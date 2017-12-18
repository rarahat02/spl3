package com.rarahat02.rarahat02.spl3.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.rarahat02.rubensousa.recyclerviewsnap.R;

import java.util.ArrayList;
import java.util.List;

public class SnapFragment extends Fragment {

    public static final String ORIENTATION = "orientation";

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewSongs;
    private boolean mHorizontal;


    public static SnapFragment newInstance()
    {
        SnapFragment fragment = new SnapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_snap, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerViewSongs = view.findViewById(R.id.recyclerViewSongs);

        //((MainActivity) getActivity()).getSupportActionBar().hide();

/*        Toolbar toolbar = (Toolbar) vifindViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(this);*/

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mRecyclerViewSongs.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewSongs.setHasFixedSize(true);

        if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }
        setupAdapter();

        return view;
    }






    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ORIENTATION, mHorizontal);
    }

    private void setupAdapter()
    {
        List<App> apps = getApps();
        List<AppSong> appSongs = getAppSongs();

        SnapAdapter snapAdapter = new SnapAdapter(getActivity());
        SnapAdapterSongs snapAdapterSongs = new SnapAdapterSongs(getActivity());
        if (mHorizontal)
        {
            snapAdapter.addSnap(new Snap(Gravity.CENTER_HORIZONTAL, "Top Artists", apps));
/*            snapAdapter.addSnap(new SnapSongs(Gravity.CENTER, "Most Recent Songs", appSongs));
            snapAdapter.addSnap(new SnapSongs(Gravity.CENTER, "Top Downloaded Songs", appSongs));*/

            snapAdapterSongs.addSnap(new SnapSongs(Gravity.CENTER, "Most Recent Songs", appSongs));
            snapAdapterSongs.addSnap(new SnapSongs(Gravity.CENTER, "Top Downloaded Songs", appSongs));
            mRecyclerView.setAdapter(snapAdapter);
            mRecyclerViewSongs.setAdapter(snapAdapterSongs);
        }
        else
        {
            Adapter adapter = new Adapter(false, false, apps);
            mRecyclerView.setAdapter(adapter);
            new GravitySnapHelper(Gravity.TOP, false, new GravitySnapHelper.SnapListener() {
                @Override
                public void onSnap(int position) {
                    Log.d("Snapped", position + "");
                }
            }).attachToRecyclerView(mRecyclerView);
        }
    }

    private List<App> getApps() {
        List<App> apps = new ArrayList<>();
        apps.add(new App(R.drawable.hero_alom_profile, "Hero Alom", 20, 200));
        apps.add(new App(R.drawable.mahfuzur, "Mahfuzur Rahman", 20, 100));
        apps.add(new App(R.drawable.chinu, "Shamima Alam Chinu", 20, 100));
        apps.add(new App(R.drawable.imran, "Imran Khan", 20, 100));

        return apps;
    }

    private List<AppSong> getAppSongs() {
        List<AppSong> apps = new ArrayList<>();
        apps.add(new AppSong(10,"By Mahfuzur Rahman", "Gaan Name", "link"));
        apps.add(new AppSong(10,"By Eva Rahman", "ABCD", "link"));
        apps.add(new AppSong(10,"By RA Rahat", "XYZ", "link"));

        return apps;
    }

/*    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.layoutType) {
            mHorizontal = !mHorizontal;
            setupAdapter();
            item.setTitle(mHorizontal ? "Vertical" : "Horizontal");
        } else if (item.getItemId() == R.id.grid) {
            //tartActivity(new Intent(this, GridActivity.class));
        }
        return false;
    }*/
}
