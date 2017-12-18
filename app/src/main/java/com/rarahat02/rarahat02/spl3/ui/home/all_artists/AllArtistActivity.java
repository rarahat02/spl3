package com.rarahat02.rarahat02.spl3.ui.home.all_artists;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.github.rarahat02.instamaterial.R;

public class AllArtistActivity extends AppCompatActivity
{


    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private RecyclerView recyclerView;

    private ArrayList<Item> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_artist_activity);

        itemList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("social/all_artists/0");
        //mFirebaseDatabase.keepSynced(false);




        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        ItemAdapter itemAdapter = new ItemAdapter(linearLayoutManager, itemList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemAdapter);




        ValueEventListener postListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {


                Iterable<DataSnapshot> children = dataSnapshot.getChildren();


                for (DataSnapshot child : children)
                {
                    Item specimenDTO = child.getValue(Item.class);
                    itemList.add(specimenDTO);
                    Log.v("rahatmal", "onDataChange: " + specimenDTO.getTitle() + "\t" + specimenDTO.getfollower_count());

                }

                Log.v("rahat", "onDataChange: " + itemList.size());
                itemAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("firebase", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mFirebaseDatabase.addValueEventListener(postListener);



/*        itemList.add(new Item("R.drawable.habib_wahid", "Habib Wahid", 100, false));
        itemList.add(new Item("R.drawable.taylor_swift", "Taylor Swift", 100, false));
        itemList.add(new Item("R.drawable.ic_test1", "Selena Gomez", 100, false));
        itemList.add(new Item("R.drawable.ic_test1", "The Weekend", 100, false));
        itemList.add(new Item("R.drawable.ic_test1", "Nicky Minaj", 100, false));*/








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
