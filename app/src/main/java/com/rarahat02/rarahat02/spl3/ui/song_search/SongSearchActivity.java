package com.rarahat02.rarahat02.spl3.ui.song_search;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.rarahat02.rarahat02.spl3.ui.song_search.data.DataHelper;
import com.rarahat02.rarahat02.spl3.ui.song_search.fragment.BaseExampleFragment;
import com.rarahat02.rarahat02.spl3.ui.song_search.fragment.SlidingSearchResultsExampleFragment;

import com.github.rarahat02.instamaterial.R;

public class SongSearchActivity extends AppCompatActivity
        implements BaseExampleFragment.BaseExampleFragmentCallbacks
{

    private final String TAG = "SongSearchActivity";
    public static ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBarSongSearch);


        //new SongSearchActivity.SongLoadTask().execute();

        showFragment(new SlidingSearchResultsExampleFragment());
    }




    public class SongLoadTask extends AsyncTask<Void, Integer, String>
    {

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected String doInBackground(Void... params)
        {
            DataHelper.addValueListener();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress)
        {
            progressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);

            showFragment(new SlidingSearchResultsExampleFragment());




            //feedAdapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onAttachSearchViewToDrawer(FloatingSearchView searchView)
    {
        //searchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
    }

    @Override
    public void onBackPressed() {
/*        List fragments = getSupportFragmentManager().getFragments();
        BaseExampleFragment currentFragment = (BaseExampleFragment) fragments.get(fragments.size() - 1);

        if (!currentFragment.onActivityBackPress()) {
            super.onBackPressed();
        }*/
    }



    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }
}
