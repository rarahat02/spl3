package com.rarahat02.rarahat02.spl3.ui.home.all_artists;

import java.util.ArrayList;

/**
 * Created by iit on 10/2/17.
 */

public class FollowingArtistListClass
{
    private static ArrayList<Item> followingArtistList = new ArrayList<>();

    public void setFollowingArtistList(ArrayList<Item> followingArtistList)
    {
        this.followingArtistList = followingArtistList;
    }

    public ArrayList<Item> getFollowingArtistList() {

        return followingArtistList;
    }

    public void deleteItem(String name)
    {
        followingArtistList.remove(name);
    }

    public FollowingArtistListClass(Item item)
    {

        boolean matched = false;
        for(int i = 0; i < followingArtistList.size(); i++)
        {
            if(followingArtistList.get(i).getTitle().equals(item.getTitle()))
                matched = true;
        }

        if(!matched)
            this.followingArtistList.add(item);
    }
}
