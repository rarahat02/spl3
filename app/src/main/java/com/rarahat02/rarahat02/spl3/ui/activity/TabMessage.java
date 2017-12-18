package com.rarahat02.rarahat02.spl3.ui.activity;

import com.github.rarahat02.instamaterial.R;

/**
 * Created by iiro on 7.6.2016.
 */
public class TabMessage {
    public static String get(int menuItemId, boolean isReselection) {
        String message = "Content for ";

        switch (menuItemId) {
            case R.id.tab_feed:
                message += "recents";
                break;
            case R.id.tab_player:
                message += "favorites";
                break;
            case R.id.tab_search:
                message += "nearby";
                break;

/*            case R.id.tab_food:
                message += "food";
                break;*/
        }

        if (isReselection) {
            message += " WAS RESELECTED! YAY!";
        }

        return message;
    }
}
