package com.rarahat02.rarahat02.spl3.ui.home;


public class AppSong {

    public int downloadCount;
    public String artistName;
    public String songName;
    public String downloadLink;

    public AppSong(int downloadCount, String artistName, String songName, String downloadLink) {
        this.downloadCount = downloadCount;
        this.artistName = artistName;
        this.songName = songName;
        this.downloadLink = downloadLink;
    }

    public AppSong()
    {

    }



    public int getDownloadCount() {
        return downloadCount;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public String getSongName() {
        return songName;
    }
}

