package com.rarahat02.rarahat02.spl3.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rarahat02.rubensousa.recyclerviewsnap.R;

import java.util.List;

public class AdapterSongItem extends RecyclerView.Adapter<AdapterSongItem.ViewHolder> {

    private List<AppSong> mApps;
    private boolean mHorizontal;
    private boolean mPager;

    public AdapterSongItem(boolean horizontal, boolean pager, List<AppSong> apps) {
        mHorizontal = horizontal;
        mApps = apps;
        mPager = pager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (mPager)
        {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_pager_song_item, parent, false));
        } else {
            return mHorizontal ? new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter, parent, false)) :
                    new ViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.adapter_vertical, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        AppSong app = mApps.get(position);

        holder.songNameSuggestion.setText(app.getSongName());
        holder.artistNameSuggestion.setText(app.getArtistName());
        holder.downloadSongSuggestion.setTag(app.getDownloadLink());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public TextView songNameSuggestion;
        public TextView artistNameSuggestion;
        public Button downloadSongSuggestion;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            songNameSuggestion = (TextView) itemView.findViewById(R.id.song_name_suggestion);
            artistNameSuggestion = (TextView) itemView.findViewById(R.id.artist_name_suggestion);
            downloadSongSuggestion = (Button) itemView.findViewById(R.id.download_suggestion);
        }

        @Override
        public void onClick(View v)
        {
            //Log.d("App", mApps.get(getAdapterPosition()).getName());
        }
    }

}

