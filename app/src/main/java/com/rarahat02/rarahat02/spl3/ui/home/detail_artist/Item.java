package com.rarahat02.rarahat02.spl3.ui.home.detail_artist;

/**
 * Created by rarahat on 9/29/17.
 */

public class Item
{


    private final int artistPic;
    private final String songTitle;
    private final String post;
    private final String date;
    private final String downloadLink;



    public int getArtistPic() {
        return artistPic;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getPost() {
        return post;
    }

    public String getDate() {
        return date;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public Item(int artistPic, String songTitle, String post, String date, String downloadLink) {

        this.artistPic = artistPic;
        this.songTitle = songTitle;
        this.post = post;
        this.date = date;
        this.downloadLink = downloadLink;
    }






}