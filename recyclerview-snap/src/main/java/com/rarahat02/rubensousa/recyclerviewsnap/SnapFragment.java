package com.rarahat02.rubensousa.recyclerviewsnap;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

public class SnapFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    public static final String ORIENTATION = "orientation";

    private RecyclerView mRecyclerView;
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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

/*        Toolbar toolbar = (Toolbar) vifindViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(this);*/

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

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

    private void setupAdapter() {
        List<App> apps = getApps();

        SnapAdapter snapAdapter = new SnapAdapter(getActivity());
        if (mHorizontal) {
            snapAdapter.addSnap(new Snap(Gravity.CENTER_HORIZONTAL, "Snap center", apps));
            snapAdapter.addSnap(new Snap(Gravity.START, "Snap start", apps));
            snapAdapter.addSnap(new Snap(Gravity.END, "Snap end", apps));
            snapAdapter.addSnap(new Snap(Gravity.CENTER, "GravityPager snap", apps));
            mRecyclerView.setAdapter(snapAdapter);
        } else {
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
        apps.add(new App("Google+", R.drawable.ic_google_48dp, 4.6f));
        apps.add(new App("Gmail", R.drawable.ic_gmail_48dp, 4.8f));
        apps.add(new App("Inbox", R.drawable.ic_inbox_48dp, 4.5f));
        apps.add(new App("Google Keep", R.drawable.ic_keep_48dp, 4.2f));
        apps.add(new App("Google Drive", R.drawable.ic_drive_48dp, 4.6f));
        apps.add(new App("Hangouts", R.drawable.ic_hangouts_48dp, 3.9f));
        apps.add(new App("Google Photos", R.drawable.ic_photos_48dp, 4.6f));
        apps.add(new App("Messenger", R.drawable.ic_messenger_48dp, 4.2f));
        apps.add(new App("Sheets", R.drawable.ic_sheets_48dp, 4.2f));
        apps.add(new App("Slides", R.drawable.ic_slides_48dp, 4.2f));
        apps.add(new App("Docs", R.drawable.ic_docs_48dp, 4.2f));
        apps.add(new App("Google+", R.drawable.ic_google_48dp, 4.6f));
        apps.add(new App("Gmail", R.drawable.ic_gmail_48dp, 4.8f));
        apps.add(new App("Inbox", R.drawable.ic_inbox_48dp, 4.5f));
        apps.add(new App("Google Keep", R.drawable.ic_keep_48dp, 4.2f));
        apps.add(new App("Google Drive", R.drawable.ic_drive_48dp, 4.6f));
        apps.add(new App("Hangouts", R.drawable.ic_hangouts_48dp, 3.9f));
        apps.add(new App("Google Photos", R.drawable.ic_photos_48dp, 4.6f));
        apps.add(new App("Messenger", R.drawable.ic_messenger_48dp, 4.2f));
        apps.add(new App("Sheets", R.drawable.ic_sheets_48dp, 4.2f));
        apps.add(new App("Slides", R.drawable.ic_slides_48dp, 4.2f));
        apps.add(new App("Docs", R.drawable.ic_docs_48dp, 4.2f));
        return apps;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.layoutType) {
            mHorizontal = !mHorizontal;
            setupAdapter();
            item.setTitle(mHorizontal ? "Vertical" : "Horizontal");
        } else if (item.getItemId() == R.id.grid) {
            //tartActivity(new Intent(this, GridActivity.class));
        }
        return false;
    }
}
