package com.rarahat02.rarahat02.spl3.ui.home;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.rarahat02.rubensousa.recyclerviewsnap.R;

import java.util.ArrayList;

import com.rarahat02.rarahat02.spl3.ui.home.all_artists.AllArtistActivity;

public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.ViewHolder>
        implements GravitySnapHelper.SnapListener
{

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    Context context;

    private ArrayList<Snap> mSnaps;
    //private ArrayList<SnapSongs> mSnapSongs;


    // Disable touch detection for parent recyclerView if we use vertical nested recyclerViews
    private View.OnTouchListener mTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    };

    public SnapAdapter(Context context)
    {
        this.context = context;
        mSnaps = new ArrayList<>();
        //mSnapSongs = new ArrayList<>();
    }

    public void addSnap(Snap snap)
    {
        mSnaps.add(snap);
    }
    /*public void addSnap(SnapSongs snap)
    {
        mSnapSongs.add(snap);
    }*/

    @Override
    public int getItemViewType(int position)
    {
        Snap snap = mSnaps.get(position);
        switch (snap.getGravity())
        {
            case Gravity.CENTER_VERTICAL:
                return VERTICAL;
            case Gravity.CENTER_HORIZONTAL:
                return HORIZONTAL;
            case Gravity.START:
                return HORIZONTAL;
            case Gravity.TOP:
                return VERTICAL;
            case Gravity.END:
                return HORIZONTAL;
            case Gravity.BOTTOM:
                return VERTICAL;
        }
        return HORIZONTAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = viewType == VERTICAL ? LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_snap_vertical, parent, false)
                : LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_snap, parent, false);

        if (viewType == VERTICAL) {
            view.findViewById(R.id.recyclerView).setOnTouchListener(mTouchListener);
        }

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Snap snap = mSnaps.get(position);
        //SnapSongs snapSongs = mSnapSongs.get(position);

        holder.snapTextView.setText(snap.getText());

            //holder.snapTextView.setText(snapSongs.getText());

        if (snap.getGravity() == Gravity.START || snap.getGravity() == Gravity.END)
        {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            new GravitySnapHelper(snap.getGravity(), false, this).attachToRecyclerView(holder.recyclerView);
        }
        else if (snap.getGravity() == Gravity.CENTER_HORIZONTAL ||
                snap.getGravity() == Gravity.CENTER_VERTICAL)
        {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), snap.getGravity() == Gravity.CENTER_HORIZONTAL ?
                    LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, false));
            new LinearSnapHelper().attachToRecyclerView(holder.recyclerView);
        }
        else if (snap.getGravity() == Gravity.CENTER)
        { // Pager snap
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            new GravityPagerSnapHelper(Gravity.START).attachToRecyclerView(holder.recyclerView);
        }
        else
        { // Top / Bottom
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext()));
            new GravitySnapHelper(snap.getGravity()).attachToRecyclerView(holder.recyclerView);
        }



        holder.recyclerView.setAdapter(new Adapter(snap.getGravity() == Gravity.START
                || snap.getGravity() == Gravity.END
                || snap.getGravity() == Gravity.CENTER_HORIZONTAL,
                snap.getGravity() == Gravity.CENTER, snap.getApps()));

/*        holder.recyclerView.setAdapter(new AdapterSongItem(snapSongs.getGravity() == Gravity.START
                || snapSongs.getGravity() == Gravity.END
                || snapSongs.getGravity() == Gravity.CENTER,
                snapSongs.getGravity() == Gravity.CENTER_HORIZONTAL, snapSongs.getApps()));*/





        holder.moreButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(snap.getText().equals("Top Artists"))
                {

                    //MainActivity.startMoreArtistFragment();

/*                    MoreArtistActivity nextFrag = new MoreArtistActivity();
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.snap_frame_layout, nextFrag,"findThisFragment")
                            .addToBackStack(null)
                            .commit();*/

                    Intent intent = new Intent(context, AllArtistActivity.class);
                    context.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return mSnaps.size();
    }

    @Override
    public void onSnap(int position) {
        Log.d("Snapped: ", position + "");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView snapTextView;
        public RecyclerView recyclerView;
        public Button moreButton;

        public ViewHolder(View itemView)
        {
            super(itemView);
            snapTextView = (TextView) itemView.findViewById(R.id.snapTextView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            moreButton = (Button) itemView.findViewById(R.id.more_button);

        }

    }
}

