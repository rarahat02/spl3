package com.rarahat02.rarahat02.spl3.ui.home.songs;

import com.github.rarahat02.instamaterial.R;

/**
 * Created by rarahat on 9/29/17.
 */

public class Item
{
    private final int drawable;
    private final String songTitle;
    private final String artistTitle;
    private final int downloadedCount;
    private final String songUrl;

    public final int getDrawable() {
        return this.drawable;
    }

    public final String getSongTitle() {
        return this.songTitle;
    }
    public final int getdownloadedCount() {
        return this.downloadedCount;
    }

    public String getArtistTitle() {
        return artistTitle;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public Item(String songTitle, String artistTitle, String songUrl, int downloadedCount)
    {
        super();
        this.songTitle = songTitle;
        this.downloadedCount = downloadedCount;
        this.artistTitle = artistTitle;
        this.songUrl = songUrl;
        this.drawable = R.drawable.ic_test1;


    }



}