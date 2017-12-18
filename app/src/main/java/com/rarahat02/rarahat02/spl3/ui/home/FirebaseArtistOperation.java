package com.rarahat02.rarahat02.spl3.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.rarahat02.rarahat02.spl3.ui.home.all_artists.AllArtistActivity;
import com.rarahat02.rarahat02.spl3.ui.home.all_artists.Item;

/**
 * Created by iit on 10/3/17.
 */

public class FirebaseArtistOperation
{

    static String projectUrl = "https://console.firebase.google.com/project/instamaterial-master-64217/";

    //static Firebase CustomersRef = new Firebase(projectUrl + "database/data/social/Customers");
    static Firebase CustomersRef = new Firebase(projectUrl + "database/data/social/");





    public static void artistFollow(Context context, boolean isFollowOperation, String title, int followercount)
    {
        FirebaseDatabase.getInstance().getReference().child("social/all_artists/0")
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            Item artist = snapshot.getValue(Item.class);

                            if(artist.getTitle().equals(title))
                            {
                                Log.v("follow", title);

                                DatabaseReference c1v2 = snapshot.getRef().child("follower_count");
                                c1v2.setValue(followercount);


                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(context, AllArtistActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                ((AllArtistActivity) context).finish();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                    }
                });
    }

}

