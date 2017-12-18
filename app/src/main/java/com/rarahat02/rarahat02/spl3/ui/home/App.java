package com.rarahat02.rarahat02.spl3.ui.home;


public class App {

    public int drawable;
    public String title;
    public int download_count;
    public int follower_count;
    public boolean is_following;



    public App(int mDrawable, String mName, int downloads, int followers) {
    
        this.drawable = mDrawable;
        this.title = mName;
        this.download_count = downloads;
        this.follower_count = followers;
    }

    public App() 
    {
       
    }



    public int getDrawable() {
        return drawable;
    }

    public String getName() {
        return title;
    }

    public int getDownloads() {
        return download_count;
    }

    public int getFollowers() {
        return follower_count;
    }
}

