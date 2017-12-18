package com.rarahat02.rarahat02.spl3.ui.home.detail_artist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import com.github.rarahat02.instamaterial.R;
import com.rarahat02.rarahat02.spl3.Utils;

public class DetailArtistActivity extends AppCompatActivity
{

    RecyclerView recyclerView;
    SubtitleCollapsingToolbarLayout subtitleCollapsingToolbarLayout;
    private ArrayList<Item> itemList;
    ImageView artistToolBarPic;

    int artistPic;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_artist_activity);

        itemList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewDetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        artistToolBarPic = (ImageView) findViewById(R.id.artistToolbarPic);
        subtitleCollapsingToolbarLayout = findViewById(R.id.toolbarLayout);



        if (getIntent().getExtras() != null)
        {

            String imageUrl = getIntent().getExtras().getString("pic");
            new Utils.ImageLoadTask(imageUrl, artistToolBarPic).execute();
            subtitleCollapsingToolbarLayout.setTitle(getIntent().getExtras().getString("name"));
            subtitleCollapsingToolbarLayout.setSubtitle(getIntent().getExtras().getString("followers"));
        }


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemAdapter);


        itemList.add(new Item(R.drawable.habib_wahid, "Song name", "hello", "12 March", ""));
        itemList.add(new Item(R.drawable.habib_wahid, "Song name", "hello", "12 March", ""));
        itemList.add(new Item(R.drawable.habib_wahid, "Song name", "hello", "12 March", ""));
        itemList.add(new Item(R.drawable.habib_wahid, "Song name", "hello", "12 March", ""));
        itemList.add(new Item(R.drawable.habib_wahid, "Song name", "hello", "12 March", ""));


        itemAdapter.notifyDataSetChanged();





/*        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ItemAdapter(layoutManager)*/




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
