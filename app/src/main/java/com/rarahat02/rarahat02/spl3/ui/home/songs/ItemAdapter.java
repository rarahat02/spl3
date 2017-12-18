package com.rarahat02.rarahat02.spl3.ui.home.songs;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hendraanggrian.widget.ExpandableItem;
import com.hendraanggrian.widget.ExpandableRecyclerView;

import java.util.ArrayList;

import com.github.rarahat02.instamaterial.R;


public  class ItemAdapter extends ExpandableRecyclerView.Adapter<ItemAdapter.ViewHolder>
{
    private Context context;
    //private final Item[] items;
    private ArrayList<Item> itemList;
    
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
     
        this.context = parent.getContext();
        View var10002 = LayoutInflater.from(this.context).inflate(R.layout.all_songs_view_row, parent, false);
        return new ItemAdapter.ViewHolder(var10002);
    }


    public void onBindViewHolder(ViewHolder holder, int position)
    {
       
        super.onBindViewHolder(holder, position);

        Item currentItem = this.itemList.get(position);
        int drawable = currentItem.getDrawable();

        String songName = currentItem.getSongTitle();
        String artistName = currentItem.getArtistTitle();
        int downloadCount = currentItem.getdownloadedCount();

        String downloadUrl = currentItem.getSongUrl();


        Log.v("test", "onBindViewHolder: " + songName);
        holder.getImageView().setImageDrawable(ContextCompat.getDrawable(this.context, drawable));

        holder.getSongName().setText(songName);
        holder.getArtistName().setText(artistName);

        holder.getDownloadedCount().setText(Integer.toString(downloadCount));

        holder.getToFollowText().setText("Download and purchase this song once and play it across 3 of your devices");
/*        holder.getButton().setOnClickListener(new View.OnClickListener()
        {
            public final void onClick(View it) {
                Toast.makeText(ItemAdapter.this.context, "Clicked!", Toast.LENGTH_LONG).show();
            }
        });*/



        holder.getFollowButton().setOnClickListener(new View.OnClickListener()
        {

            int count = Integer.parseInt(holder.getDownloadedCount().getText().toString());

            public final void onClick(View it)
            {
                holder.getDownloadedCount().setText(Integer.toString(++count));
            }
        });
    }


    public int getItemCount()
    {
        return itemList.size();
    }

    public ItemAdapter(LinearLayoutManager layout)
    {
        super(layout);

        itemList = new ArrayList<>();
        itemList.add(new Item("Din Gelo", "Habib Wahid", "", 100));
        itemList.add(new Item("I dont Wanna Live Forever", "Taylor Swift","", 100));
        itemList.add(new Item("Good for you", "Selena Gomez","", 100));
        itemList.add(new Item("Starboy", "The Weekend","",100));
        itemList.add(new Item("Bum bidi bum", "Nicky Minaj","", 100));


    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        
        private  ExpandableItem item;
        private  ImageView imageView;
        private  TextView songName, artistName;
        private  Button button;
        private  Button followButton;
        private  TextView followerCount, toFollowtext;



         
        public ExpandableItem getItem() {
            return this.item;
        }

         
        public ImageView getImageView() {
            return this.imageView;
        }

         
        public TextView getSongName() {
            return this.songName;
        }
        public TextView getArtistName() {
            return this.artistName;
        }

         
/*        public Button getButton() {
            return this.button;
        }*/
        public Button getFollowButton() {
            return this.followButton;
        }
        public TextView getDownloadedCount() {
            return this.followerCount;
        }
        public TextView getToFollowText() {
            return this.toFollowtext;
        }

        public ViewHolder(View itemView)
        {

            super(itemView);
            View var10001 = itemView.findViewById(R.id.row);

            this.item = (ExpandableItem)var10001;
            var10001 = this.item.getHeaderLayout().findViewById(R.id.imageView);

            this.imageView = (ImageView)var10001;

            var10001 = this.item.getHeaderLayout().findViewById(R.id.song_name_suggested);
            this.songName = (TextView)var10001;

            var10001 = this.item.getHeaderLayout().findViewById(R.id.artist_name_suggested);
            this.artistName = (TextView)var10001;

/*            var10001 = this.item.getContentLayout().findViewById(R.id.button);

            this.button = (Button)var10001;*/

            this.followButton = this.item.getContentLayout().findViewById(R.id.follow_button);
            this.followerCount = this.item.getContentLayout().findViewById(R.id.follower_count);
            this.toFollowtext = this.item.getContentLayout().findViewById(R.id.toFollowText);

   /*         this.songName = this.item.getHeaderLayout().findViewById(R.id.song_name_suggested);
            this.artistName = this.item.getHeaderLayout().findViewById(R.id.artist_name_suggested);*/



        }
    }
}
