package com.rarahat02.rarahat02.spl3.ui.home.all_artists;

/**
 * Created by rarahat on 9/29/17.
 */

public class Item
{
    public String  drawable;
    public int follower_count;
    public boolean is_following;
    public String title;


    public boolean is_following() {
        return is_following;
    }

    public final String  getDrawable() {
        return this.drawable;
    }

    public final String getTitle() {
        return this.title;
    }
    public final int getfollower_count() {
        return this.follower_count;
    }

    public Item()
    {

    }

    public Item(String drawable,  String title, int follower_count, boolean is_following)
    {
        super();
        this.title = title;
        this.follower_count = follower_count;
        this.is_following = false;
        this.drawable = drawable;
    }
    public void setFollowingTrue() {
        this.is_following = true;
    }
    public void setfollower_countAddByOne() {
        this.follower_count++;
    }



}