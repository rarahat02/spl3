package com.rarahat02.rarahat02.spl3.ui.home.all_artists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarahat02.rarahat02.spl3.ui.home.detail_artist.DetailArtistActivity;
import com.hendraanggrian.widget.ExpandableItem;
import com.hendraanggrian.widget.ExpandableRecyclerView;

import java.util.ArrayList;

import com.github.rarahat02.instamaterial.R;
import com.rarahat02.rarahat02.spl3.Utils;
import com.rarahat02.rarahat02.spl3.ui.home.FirebaseArtistOperation;


public  class ItemAdapter extends ExpandableRecyclerView.Adapter<ItemAdapter.ViewHolder>
{
    private Context context;

    private ArrayList<Item> itemList = new ArrayList<>();

    public static final String OBJECT = "OBJECT";
    FollowingArtistListClass listClass;

    
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
     
        this.context = parent.getContext();
        View var10002 = LayoutInflater.from(this.context).inflate(R.layout.all_artist_view_row, parent, false);
        return new ItemAdapter.ViewHolder(var10002);
    }


    public void onBindViewHolder(ViewHolder holder, int position)
    {
       
        super.onBindViewHolder(holder, position);

        Item currentItem = this.itemList.get(position);
        String drawable = currentItem.getDrawable();
        //int drawable = R.drawable.habib_wahid;
        new Utils.ImageLoadTask(drawable, holder.getImageView()).execute();


        String title = currentItem.getTitle();
        int followers = currentItem.getfollower_count();


        //holder.getImageView().setImageDrawable(ContextCompat.getDrawable(this.context, drawable));
        holder.getTextView().setText(title);
        holder.getFollowerCount().setText(Integer.toString(followers));

        holder.getToFollowText().setText("Follow " + title + " to get " + title +
                "'s all updates and songs on your feed");




        holder.getButton().setOnClickListener(new View.OnClickListener()
        {
            public final void onClick(View it)
            {
                Intent intent = new Intent(context, DetailArtistActivity.class);
                intent.putExtra("pic", drawable);
                intent.putExtra("name", title);
                intent.putExtra("followers", Integer.toString(followers) + " Followers");

                context.startActivity(intent);

                //Toast.makeText(ItemAdapter.this.context, "Clicked!", Toast.LENGTH_LONG).show();
            }
        });



        holder.getFollowButton().setOnClickListener(new View.OnClickListener()
        {

            int count = Integer.parseInt(holder.getFollowerCount().getText().toString());

            public final void onClick(View it)
            {

                if(holder.getFollowButton().getText().equals("Follow"))
                {
                    holder.getFollowButton().setText("Following");
                    holder.getFollowerCount().setText(Integer.toString(++count));

                    FirebaseArtistOperation.artistFollow(context, true, title, count);

                }

                else
                {
                    holder.getFollowButton().setText("Follow");
                    holder.getFollowerCount().setText(Integer.toString(--count));

                    FirebaseArtistOperation.artistFollow(context, false, title, count);
                }
            }
        });
    }



    public int getItemCount()
    {
        return itemList.size();
    }

    public ItemAdapter(LinearLayoutManager linearLayoutManager, ArrayList<Item> itemList)
    {
        super(linearLayoutManager);

        this.itemList = itemList;


        //tinydb = new TinyDB(context);


    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        
        private  ExpandableItem item;
        private  ImageView imageView;
        private  TextView textView;
        private  Button button;
        private  Button followButton;
        private  TextView followerCount, toFollowtext;



         
        public ExpandableItem getItem() {
            return this.item;
        }

         
        public ImageView getImageView() {
            return this.imageView;
        }

         
        public TextView getTextView() {
            return this.textView;
        }

         
        public Button getButton() {
            return this.button;
        }
        public Button getFollowButton() {
            return this.followButton;
        }
        public TextView getFollowerCount() {
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
            var10001 = this.item.getHeaderLayout().findViewById(R.id.textView);

            this.textView = (TextView)var10001;
            var10001 = this.item.getContentLayout().findViewById(R.id.button);

            this.button = (Button)var10001;

            this.followButton = this.item.getContentLayout().findViewById(R.id.follow_button);
            this.followerCount = this.item.getContentLayout().findViewById(R.id.follower_count);
            this.toFollowtext = this.item.getContentLayout().findViewById(R.id.toFollowText);



        }
    }
}
