package com.rarahat02.rarahat02.spl3.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.firebase.client.Firebase;
import com.rarahat02.rarahat02.spl3.ui.adapter.FeedAdapterForFirebase;
import com.rarahat02.rarahat02.spl3.ui.model.FeedItemFirebase;
import com.rarahat02.rarahat02.spl3.ui.view.FeedContextMenu;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.github.rarahat02.instamaterial.R;

import com.rarahat02.rarahat02.spl3.ui.adapter.FeedItemAnimator;
import com.rarahat02.rarahat02.spl3.ui.view.FeedContextMenuManager;


public class FeedFragment extends Fragment implements FeedAdapterForFirebase.OnFeedItemClickListener,
        FeedContextMenu.OnFeedContextMenuItemClickListener
{

    private Firebase myFirebaseRef;
    private FirebaseAuth mAuth;

    public String customerPassword = "2222";

    public static ProgressBar progressBar;
    Button downloadFileFromFeed;


    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    //@BindView(R.id.rvFeed)
    RecyclerView rvFeed;
/*    @BindView(R.id.btnCreate)
    FloatingActionButton fabCreate;*/

    @BindView(R.id.content) CoordinatorLayout clContent;

    private FeedAdapterForFirebase feedAdapter;

    private boolean pendingIntroAnimation;




    public static FeedFragment newInstance()
    {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);
        rvFeed.setHasFixedSize(false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        downloadFileFromFeed = (Button) view.findViewById(R.id.downloadButtonFromFeed);

        feedAdapter = new FeedAdapterForFirebase(getActivity());


        setupFeed();

        new FeedLoadTask().execute();


        myFirebaseRef = new Firebase("https://console.firebase.google.com/project/instamaterial-master-64217/authentication/users");
        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fabCreate = (FloatingActionButton) view.findViewById(R.id.btnCreate);

        fabCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
/*                Intent intent = new Intent(getActivity(), com.stone.transition.StartActivity.class);
                startActivity(intent);*/
            }
        });


/*        downloadFileFromFeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                String fileId = "";
                MyPlayground.downloadAndPurchasePermissionDialog(getActivity(), fileId, customerPassword);
            }
        });*/


/*        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startContentAnimation();
        }*/

        return view;
    }




    public class FeedLoadTask extends AsyncTask<Void, Integer, List<FeedItemFirebase>>
    {

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected List<FeedItemFirebase> doInBackground(Void... params)
        {
                feedAdapter.updateItems();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress)
        {
            progressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(List<FeedItemFirebase> result)
        {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);


            feedAdapter.notifyDataSetChanged();

        }

    }

    private void setupFeed()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext())
        {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);


        //feedAdapter = new FeedAdapterForFirebase(getActivity());


        feedAdapter.setOnFeedItemClickListener(this);
        rvFeed.setAdapter(feedAdapter);
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvFeed.setItemAnimator(new FeedItemAnimator());


    }

/*    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction()))
        {
            showFeedLoadingItemDelayed();
        }
    }*/

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvFeed.smoothScrollToPosition(0);
                feedAdapter.showLoadingView();
            }
        }, 500);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }*/

    private void startIntroAnimation() {
       // fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

/*        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();*/
    }
/*    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }*/

    private void startContentAnimation()
    {
/*        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();*/
        feedAdapter.updateItems();
    }

    @Override
    public void onCommentsClick(View v, int position)
    {
            //TO_DO
/*        Fragment selectedFragment = CommentsFragment.newInstance();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();*/


        //https://stackoverflow.com/questions/16036572/how-to-pass-values-between-fragments



        /*    final Intent intent = new Intent(this, CommentsFragment.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsFragment.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);*/
    }

    @Override
    public void onMoreClick(View v, int itemPosition) {
        FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, itemPosition, this);
    }

    @Override
    public void onProfileClick(View v)
    {
/*        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);*/
    }

    @Override
    public void onReportClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onSharePhotoClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCopyShareUrlClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @Override
    public void onCancelClick(int feedItem) {
        FeedContextMenuManager.getInstance().hideContextMenu();
    }

    @OnClick(R.id.btnCreate)
    public void onTakePhotoClick()
    {
/*        int[] startingLocation = new int[2];
        fabCreate.getLocationOnScreen(startingLocation);
        startingLocation[0] += fabCreate.getWidth() / 2;
        TakePhotoActivity.startCameraFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);*/



/*        Intent intent = new Intent(getApplicationContext(), com.yarolegovich.slidingrootnav.sample.fragment.audioplayer.PlayerOrdinaryFiles.class);
        startActivity(intent);*/
    }

    public void showLikedSnackbar()
    {
        Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
    }





}