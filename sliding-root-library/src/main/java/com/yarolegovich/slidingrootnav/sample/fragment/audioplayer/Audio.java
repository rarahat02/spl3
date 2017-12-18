package com.yarolegovich.slidingrootnav.sample.fragment.audioplayer;


import java.io.Serializable;

/**
 * Created by Valdio Veliu on 16-07-18.
 */
public class Audio implements Serializable {

    private String data;
    private String title;
    private String album;
    private String artist;
    //public Uri audioUri;
    private String  path;

    public Audio(String data, String title, String album, String artist, String path) {
        this.data = data;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.path = path;
        //this.audioUri = uri;
    }

    public String getData() {
        return data;
    }

/*    public Uri getUri() {
        return audioUri;
    }*/
    public String getPath() {
    return path;
}

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


}
