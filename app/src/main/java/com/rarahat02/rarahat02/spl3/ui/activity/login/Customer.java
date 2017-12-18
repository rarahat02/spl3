package com.rarahat02.rarahat02.spl3.ui.activity.login;

import java.util.ArrayList;

class Follower
{
    private ArrayList<String> artists_titles;

    public ArrayList<String> getArtistsTitles() { return this.artists_titles; }

    public void setArtistsTitles(ArrayList<String> artists_titles) { this.artists_titles = artists_titles; }
}

class Following
{
    private ArrayList<String> artists_titles;

    public ArrayList<String> getArtistsTitles() { return this.artists_titles; }

    public void setArtistsTitles(ArrayList<String> artists_titles) { this.artists_titles = artists_titles; }
}

class Uid
{
    private Follower follower;

    public Follower getFollower() { return this.follower; }

    public void setFollower(Follower follower) { this.follower = follower; }

    private Following following;

    public Following getFollowing() { return this.following; }

    public void setFollowing(Following following) { this.following = following; }

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }
}

public class Customer
{
    private Uid uid;

    public Uid getUid() { return this.uid; }

    public void setUid(Uid uid) { this.uid = uid; }
}

