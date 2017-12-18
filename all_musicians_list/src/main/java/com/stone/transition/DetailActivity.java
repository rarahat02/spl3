package com.stone.transition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xmuSistone on 2016/9/19.
 */
public class DetailActivity extends FragmentActivity {

    public static final String EXTRA_IMAGE_URL = "detailImageUrl";

/*    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    public static final String ADDRESS1_TRANSITION_NAME = "address1";
    public static final String ADDRESS2_TRANSITION_NAME = "address2";
    public static final String ADDRESS3_TRANSITION_NAME = "address3";
    public static final String ADDRESS4_TRANSITION_NAME = "address4";
    public static final String ADDRESS5_TRANSITION_NAME = "address5";
    public static final String RATINGBAR_TRANSITION_NAME = "ratingBar";*/

/*
    public static final String HEAD1_TRANSITION_NAME = "head1";
    public static final String HEAD2_TRANSITION_NAME = "head2";
    public static final String HEAD3_TRANSITION_NAME = "head3";
    public static final String HEAD4_TRANSITION_NAME = "head4";*/

    //private View address1, address2, address3, address4, address5;
    private ImageView artistImage;

    ProgressBar progressBar;

    LinearLayout midLayout;


    TextView trackCount, artistName, followerCount;
    Button followButton;

    //private RatingBar ratingBar;

    private LinearLayout listContainer;
    //private static final String[] headStrs = {HEAD1_TRANSITION_NAME, HEAD2_TRANSITION_NAME, HEAD3_TRANSITION_NAME, HEAD4_TRANSITION_NAME};
    //private static final int[] imageIds = {R.drawable.head1, R.drawable.head2, R.drawable.head3, R.drawable.head4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);




        artistImage = (ImageView) findViewById(R.id.image);

        trackCount = (TextView) findViewById(R.id.track_count);

        midLayout = (LinearLayout) findViewById(R.id.mid_layout);
        artistName =  (TextView) findViewById(R.id.artist_name);
        followerCount =  (TextView) findViewById(R.id.follower_count);

        followButton = (Button)  findViewById(R.id.follow_button);




        progressBar = (ProgressBar)findViewById(R.id.progressBarDetailArtist);
/*        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        address3 = findViewById(R.id.address3);
        address4 = findViewById(R.id.address4);
        address5 = findViewById(R.id.address5);
        ratingBar = (RatingBar) findViewById(R.id.rating);*/
        listContainer = (LinearLayout) findViewById(R.id.detail_list_container);

/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);*/

/*        ViewCompat.setTransitionName(imageView, IMAGE_TRANSITION_NAME);
        ViewCompat.setTransitionName(address1, ADDRESS1_TRANSITION_NAME);
        ViewCompat.setTransitionName(address2, ADDRESS2_TRANSITION_NAME);
        ViewCompat.setTransitionName(address3, ADDRESS3_TRANSITION_NAME);
        ViewCompat.setTransitionName(address4, ADDRESS4_TRANSITION_NAME);
        ViewCompat.setTransitionName(address5, ADDRESS5_TRANSITION_NAME);
        ViewCompat.setTransitionName(ratingBar, RATINGBAR_TRANSITION_NAME);*/


        try
        {
            URL url = new URL("https://placeimg.com/640/480/any");
            new MusicianPageAsyncTask().execute(url, url, url, url, url, url, url, url, url, url);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //dealListView();
    }

    class MusicianPageAsyncTask extends AsyncTask<URL, Integer, Bitmap[]> {

        @Override
        protected Bitmap[] doInBackground(URL... urls)
        {
            Bitmap[] bitmaps = new Bitmap[urls.length];

/*            for(int i = 0; i < 10000; i++)
            {

            }*/

            for (int i = 0; i < urls.length; i++) {
                try {
                    bitmaps[i] = BitmapFactory.decodeStream(urls[i].openConnection().getInputStream());
                    int percentProgress = (int)((i / (urls.length - 1f)) * 100);
                    publishProgress(percentProgress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bitmaps;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Bitmap[] bitmaps)
        {
            progressBar.setVisibility(View.GONE);


            trackCount.setText("100 Tracks");
            artistName.setText("Nerd Musician");
            followerCount.setText("1000 followers");

            midLayout.setVisibility(View.VISIBLE);



            if(!followButton.isShown())
            {
                followButton.setText("Unfollow");
                followButton.setVisibility(View.VISIBLE);
            }



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                Window window = getWindow();
                window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

            String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
            ImageLoader.getInstance().displayImage(imageUrl, artistImage);

            dealListView();

        }
    }

    private void dealListView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        for (int i = 0; i < 20; i++) {
            View childView = layoutInflater.inflate(R.layout.detail_list_item, null);
            listContainer.addView(childView);
            //ImageView headView = (ImageView) childView.findViewById(R.id.head);
/*            if (i < headStrs.length)
            {
                headView.setImageResource(imageIds[i % imageIds.length]);
                ViewCompat.setTransitionName(headView, headStrs[i]);
            }*/
        }
    }
}
