package com.rarahat02.rarahat02.spl3.ui.home.detail_artist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.github.rarahat02.instamaterial.R;


public  class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Item> itemList;

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
/*
        this.context = parent.getContext();
        View var10002 = LayoutInflater.from(this.context).inflate(R.layout.detail_artist_view_row, parent, false);
        return new ItemAdapter.ViewHolder(var10002);*/

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_artist_view_row, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position)
    {

        Item currentItem = this.itemList.get(position);

        holder.getPost().setText(currentItem.getPost());
        holder.getDate().setText(currentItem.getDate());
        holder.getSongTitle().setText(currentItem.getSongTitle());
        //holder.getDownloadButton().setTag(R.id.download_link, currentItem.getDownloadLink());
    }


    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

    public ItemAdapter(ArrayList<Item> itemList)
    {
        this.itemList = itemList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView artistPic;
        private TextView post, songTitle, date;
        private Button downloadButton;

        public ImageView getArtistPic() {
            return artistPic;
        }

        public TextView getPost() {
            return post;
        }

        public TextView getSongTitle() {
            return songTitle;
        }

        public TextView getDate() {
            return date;
        }

        public Button getDownloadButton() {
            return downloadButton;
        }

        public ViewHolder(View itemView)
        {

            super(itemView);

            this.artistPic = itemView.findViewById(R.id.artistPic);
            this.post = itemView.findViewById(R.id.artistPost);
            this.songTitle = itemView.findViewById(R.id.songTitle);
            this.date = itemView.findViewById(R.id.date);
            this.downloadButton = itemView.findViewById(R.id.downloadButton);

        }
    }
}
