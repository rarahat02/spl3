package com.yarolegovich.slidingrootnav.sample.fragment.audioplayer;

/**
 * Created by Rahat-PDM on 6/4/2017.
 */

public class CurrentData
{
    private String title;
    public String subTitle;

    public CurrentData(String title, String subtitle)
    {
        this.title = title;
        this.subTitle = subtitle;
    }

    public  String getTitle()
    {
        return title;
    }
    public String getSubTitle()
    {
        return subTitle;
    }
}
