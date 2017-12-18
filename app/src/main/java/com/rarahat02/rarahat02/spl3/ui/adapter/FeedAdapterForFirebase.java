package com.rarahat02.rarahat02.spl3.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.afollestad.materialdialogssample.MyPlayground;
import com.rarahat02.rarahat02.spl3.ui.view.LoadingFeedItemView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tingyik90.snackprogressbar.SnackProgressBar;
import com.tingyik90.snackprogressbar.SnackProgressBarManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.rarahat02.instamaterial.R;
import com.rarahat02.rarahat02.spl3.ui.fragments.FeedFragment;
import com.rarahat02.rarahat02.spl3.ui.model.FeedItemFirebase;

/**
 * Created by froger_mcs on 05.11.14.
 */
public class FeedAdapterForFirebase extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";

    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;

    private final List<FeedItemFirebase> feedItems = new ArrayList<>();

    private Context context;
    private OnFeedItemClickListener onFeedItemClickListener;

    private boolean showLoadingView = false;

    private SnackProgressBarManager snackProgressBarManager;
    private SnackProgressBar determinateType;

    public FeedAdapterForFirebase(Context context) {
        this.context = context;
    }



    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == VIEW_TYPE_DEFAULT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);

            CellFeedViewHolder cellFeedViewHolder = new CellFeedViewHolder(view);
            setupClickableViews(view, cellFeedViewHolder);
            return cellFeedViewHolder;
        }
        else if (viewType == VIEW_TYPE_LOADER)
        {
            LoadingFeedItemView view = new LoadingFeedItemView(context);
            view.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            return new LoadingCellFeedViewHolder(view);
        }

        return null;
    }

    private void setupClickableViews(final View view, final CellFeedViewHolder cellFeedViewHolder)
    {
        cellFeedViewHolder.btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onCommentsClick(view, cellFeedViewHolder.getAdapterPosition());
            }
        });
        cellFeedViewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onMoreClick(v, cellFeedViewHolder.getAdapterPosition());
            }
        });


        cellFeedViewHolder.downloadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //determinateBtnClick(v);   // kaj kore
                //indeterminateBtnClick(v);

                String fileId = "abc";
                String artistName = "pussycat";
                String songName = (String) v.getTag(R.id.song_name);
                String downloadLink = (String) v.getTag(R.id.download_link);

                MyPlayground.downloadAndPurchasePermissionDialog(context, fileId,
                            songName, artistName);

                Log.v(" downloadbutton",songName + "\t" + downloadLink);
            }
        });
        /*cellFeedViewHolder.ivFeedCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = cellFeedViewHolder.getAdapterPosition();
                feedItems.get(adapterPosition).likesCount++;
                notifyItemChanged(adapterPosition, ACTION_LIKE_IMAGE_CLICKED);
                if (context instanceof StartActivity) {
                    ((StartActivity) context).showLikedSnackbar();
                }
            }
        });*/
        cellFeedViewHolder.btnLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int adapterPosition = cellFeedViewHolder.getAdapterPosition();
                feedItems.get(adapterPosition).likesCount++;
                notifyItemChanged(adapterPosition, ACTION_LIKE_BUTTON_CLICKED);
/*                if (context instanceof  FeedFragment) {
                    ((FeedFragment) context).showLikedSnackbar();
                }*/



            }
        });
        cellFeedViewHolder.musicianPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onProfileClick(view);
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        ((CellFeedViewHolder) viewHolder).bindView(feedItems.get(position));

        if (getItemViewType(position) == VIEW_TYPE_LOADER) {
            bindLoadingFeedItem((LoadingCellFeedViewHolder) viewHolder);
        }


    }

    private void bindLoadingFeedItem(final LoadingCellFeedViewHolder holder) {
        holder.loadingFeedItemView.setOnLoadingFinishedListener(new LoadingFeedItemView.OnLoadingFinishedListener() {
            @Override
            public void onLoadingFinished() {
                showLoadingView = false;
                notifyItemChanged(0);
            }
        });
        holder.loadingFeedItemView.startLoading();
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadingView && position == 0) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }
    private class FeedLoadTask extends AsyncTask<Void, Void, Bitmap>
    {
        boolean animated;

        public FeedLoadTask(boolean animated)
        {
            this.animated = animated;
        }




        @Override
        protected Bitmap doInBackground(Void... params)
        {
            mFirebaseInstance = FirebaseDatabase.getInstance();
            //mFirebaseDatabase = mFirebaseInstance.getReference("social/posts/your_post_id/");
            mFirebaseDatabase = mFirebaseInstance.getReference("social/posts/0");
            //mFirebaseDatabase.keepSynced(false);



            ValueEventListener postListener = new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {


                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();


                    for (DataSnapshot child : children)
                    {
                        FeedItemFirebase specimenDTO = child.getValue(FeedItemFirebase.class);
                        feedItems.add(specimenDTO);

                    }

                    Log.v("rahat", "onDataChange: " + feedItems.size());
/*                   musicianName.setText(post.getMusicianName());
                    postedTime.setText(post.getPostedTime());
                    postText.setText(post.getPostText());
                    downloadCount.setText("133 Downloads");

                    new DownloadImageTask(musicianPic).execute(post.getMusicianPic());*/

/*                    feedItems.add(new FeedItemFirebase(context, post.getMusicianName(), post.getMusicianPic(),
                            post.getPostedTime(), post.getPostText(), 33, true));*/


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
                }
            };
            mFirebaseDatabase.addValueEventListener(postListener);

/*            if (animated)
            {
                notifyItemRangeInserted(0, feedItems.size());
            }
            else
            {
                notifyDataSetChanged();
            }*/


            return null;
        }


    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }



    private void changeValueListener()
    {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        //mFirebaseDatabase = mFirebaseInstance.getReference("social/posts/your_post_id/");
        mFirebaseDatabase = mFirebaseInstance.getReference("social/posts/");
        //mFirebaseDatabase.keepSynced(false);



        mFirebaseDatabase.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey)
            {
                Iterable<DataSnapshot> children = snapshot.getChildren();

                Log.v("previous child key", "onchild added: " + previousChildKey);

                for (DataSnapshot child : children)
                {
                    Log.v("rahat child", "on child added: " + child.getValue());
                    FeedItemFirebase specimenDTO = child.getValue(FeedItemFirebase.class);
                    feedItems.add(specimenDTO);

                }
                FeedFragment.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }

        });
    }

    private void addValueListener()
    {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        //mFirebaseDatabase = mFirebaseInstance.getReference("social/posts/your_post_id/");
        mFirebaseDatabase = mFirebaseInstance.getReference("social/posts/0");
        //mFirebaseDatabase.keepSynced(false);



        ValueEventListener postListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {


                Iterable<DataSnapshot> children = dataSnapshot.getChildren();


                for (DataSnapshot child : children)
                {
                    FeedItemFirebase specimenDTO = child.getValue(FeedItemFirebase.class);
                    feedItems.add(specimenDTO);

                }

                Log.v("rahat", "onDataChange: " + feedItems.size());
                FeedFragment.progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mFirebaseDatabase.addValueEventListener(postListener);
    }


    public void updateItems()
    {
        //feedItems.clear();

        //addValueListener();
        changeValueListener();

        //new FeedLoadTask(animated).execute();

    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }

    public  class CellFeedViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.postText)
        TextView postText;
        @BindView(R.id.btnComments)
        ImageButton btnComments;
        @BindView(R.id.btnLike)
        ImageButton btnLike;
        @BindView(R.id.btnMore)
        ImageButton btnMore;
/*        @BindView(R.id.vBgLike)
        View vBgLike;*/
/*        @BindView(R.id.ivLike)
        ImageView ivLike;*/
        @BindView(R.id.tsLikesCounter)
        TextSwitcher tsLikesCounter;
        @BindView(R.id.musicianPic)
        ImageView musicianPic;

        @BindView(R.id.musicianName)
        TextView musicianName;
        @BindView(R.id.postTime)
        TextView postedTime;

        @BindView(R.id.downloadCount)
        TextView downloadCount;

        @BindView(R.id.downloadButtonFromFeed)
        Button downloadButton;

        FeedItemFirebase feedItem;

        public CellFeedViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(FeedItemFirebase feedItem)
        {

            this.feedItem = feedItem;

            musicianName.setText(feedItem.getMusicianName());
            postedTime.setText(feedItem.getPostedTime());
            postText.setText(feedItem.getPostText());
            //downloadCount.setText(feedItem.getDownloadCount());
            //downloadCount.setText("133 Downloads");

            //downloadButton.setTag(feedItem.getDownloadLink());


            downloadButton.setTag(R.id.song_name, feedItem.getSongName());
            downloadButton.setTag(R.id.download_link, feedItem.getDownloadLink());

            new DownloadImageTask(musicianPic).execute(feedItem.getMusicianPic());

            int adapterPosition = getAdapterPosition();
            //ivFeedCenter.setImageResource(adapterPosition % 2 == 0 ? R.drawable.img_feed_center_1 : R.drawable.img_feed_center_2);
            //postText.setImageResource(adapterPosition % 2 == 0 ? R.drawable.img_feed_bottom_1 : R.drawable.img_feed_bottom_2);
            btnLike.setImageResource(feedItem.isLiked ? R.drawable.ic_heart_red : R.drawable.ic_heart_outline_grey);
            /*tsLikesCounter.setCurrentText(vImageRoot.getResources().getQuantityString(
                    R.plurals.likes_count, feedItem.likesCount, feedItem.likesCount
            ));*/
        }

        public FeedItemFirebase getFeedItem() {
            return feedItem;
        }
    }

    public class LoadingCellFeedViewHolder extends CellFeedViewHolder
    {

        LoadingFeedItemView loadingFeedItemView;

        public LoadingCellFeedViewHolder(LoadingFeedItemView view) {
            super(view);
            this.loadingFeedItemView = view;
        }


        @Override
        public void bindView(FeedItemFirebase feedItem) {
            super.bindView(feedItem);
        }

    }



    public interface OnFeedItemClickListener {
        void onCommentsClick(View v, int position);

        void onMoreClick(View v, int position);

        void onProfileClick(View v);
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }















    public  void determinateBtnClick(View view)
    {

        determinateType = new SnackProgressBar(
                SnackProgressBar.TYPE_DETERMINATE, "TYPE_DETERMINATE.")
                // (optional) set max progress, default = 100
                .setProgressMax(100)
                // (optional) show percentage, default = TRUE
                .setShowProgressPercentage(true);




        snackProgressBarManager = new SnackProgressBarManager(view)
                .setProgressBarColor(R.color.colorAccent)
                // (optional) register onDisplayListener
                .setOnDisplayListener(new SnackProgressBarManager.OnDisplayListener() {
                    @Override
                    public void onShown(int onDisplayId)
                    {}

                    @Override
                    public void onDismissed(int onDisplayId)
                    {}
                });


        // or call to show via SnackProgressBar itself
        snackProgressBarManager.show(determinateType, SnackProgressBarManager.LENGTH_INDEFINITE);
        new CountDownTimer(5000, 50) {

            int i = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                snackProgressBarManager.setProgress(i);
            }

            @Override
            public void onFinish() {
                snackProgressBarManager.dismiss();
            }
        }.start();
    }
    public void indeterminateBtnClick(View view) {
        SnackProgressBar indeterminateType = new SnackProgressBar(SnackProgressBar.TYPE_INDETERMINATE, "TYPE_INDETERMINATE.");
        snackProgressBarManager.show(indeterminateType, SnackProgressBarManager.LENGTH_INDEFINITE);
        new CountDownTimer(8000, 2000) {

            int i = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                // get the currently showing indeterminateType and change the message
                SnackProgressBar snackProgressBar = snackProgressBarManager.getLastShown();
                snackProgressBar.setMessage("TYPE_INDETERMINATE - " + i);
                // calling updateTo() will not hide and show again the SnackProgressBar
                snackProgressBarManager.updateTo(snackProgressBar);
            }

            @Override
            public void onFinish() {
                snackProgressBarManager.dismiss();
            }
        }.start();
    }
}
