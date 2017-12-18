package com.rarahat02.rarahat02.spl3.ui.home;


import java.util.List;

public class SnapSongs {

    private int mGravity;
    private String mText;
    private List<AppSong> mApps;

    public SnapSongs(int gravity, String text, List<AppSong> apps)
    {
        mGravity = gravity;
        mText = text;
        mApps = apps;
    }

    public String getText(){
        return mText;
    }

    public int getGravity(){
        return mGravity;
    }

    public List<AppSong> getApps(){
        return mApps;
    }

}
