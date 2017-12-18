package com.rarahat02.rarahat02.spl3.ui.model;

import android.content.Context;

/**
 * Created by rarahat on 7/29/17.
 */

public class FeedItemFirebase
{
    String postText;
    String postedTime;
    public String musicianName;
    String  musicianPic;
    String downloadLink;
    String songName;

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
    //String  download;



    public int likesCount;
    public boolean isLiked;

    private Context context;


    public FeedItemFirebase()
    {

    }




 /*   public String getDownloadCount() {
        return download;
    }

    public void setDownloadCount(String downloadCount) {
        this.download = downloadCount;
    }*/
    public String getPostedTime()
    {
        return postedTime;
    }

    /*public void setpostedTime(String time)
    {
        this.postedTime = time;
    }*/
    public String getMusicianName()
    {
        return musicianName;
    }

    public void setMusicianName(String musicianName)
    {
        this.musicianName = musicianName;
    }
    public String getPostText()
    {
        return postText;
    }

    public void setPostText(String postText)
    {
        this.postText = postText;
    }

    public String getMusicianPic()
    {
        return musicianPic;
    }

    public void setMusicianPic(String image)
    {
        this.musicianPic = image;
    }

    public int getLikesCount()
    {
        return likesCount;
    }

    public void setLikesCount(int likesCount)
    {
        this.likesCount = likesCount;
    }

    public boolean isLiked()
    {
        return isLiked;
    }

    public void setLiked(boolean liked)
    {
        isLiked = liked;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}