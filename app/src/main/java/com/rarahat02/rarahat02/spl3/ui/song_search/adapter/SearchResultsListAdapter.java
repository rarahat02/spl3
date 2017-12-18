package com.rarahat02.rarahat02.spl3.ui.song_search.adapter;

/**
 * Copyright (C) 2015 Ari C.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogssample.MyPlayground;
import com.arlib.floatingsearchview.util.Util;
import com.rarahat02.rarahat02.spl3.ui.song_search.data.ColorWrapper;
import com.tingyik90.snackprogressbar.SnackProgressBar;
import com.tingyik90.snackprogressbar.SnackProgressBarManager;

import java.util.ArrayList;
import java.util.List;

import com.github.rarahat02.instamaterial.R;

public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.ViewHolder> {

    private List<ColorWrapper> mDataSet = new ArrayList<>();

    private int mLastAnimatedItemPosition = -1;

    private Context context;

    private SnackProgressBarManager snackProgressBarManager;
    private SnackProgressBar determinateType;


    public SearchResultsListAdapter(Context context) {
        this.context = context;
    }

    public interface OnItemClickListener
    {
        void onClick(ColorWrapper ColorWrapper);
    }

    private OnItemClickListener mItemsOnClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView mColorName;
        public final TextView mColorValue;
        public final View mTextContainer;
        public final Button mColorLinkButton;


        public ViewHolder(View view) {
            super(view);
            mColorName = (TextView) view.findViewById(R.id.color_name);
            mColorValue = (TextView) view.findViewById(R.id.color_value);
            mTextContainer = view.findViewById(R.id.text_container);
            mColorLinkButton = (Button) view.findViewById(R.id.download_button_from_search);
        }


    }

    public void swapData(List<ColorWrapper> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }

    public void setItemsOnClickListener(OnItemClickListener onClickListener){
        this.mItemsOnClickListener = onClickListener;
    }

    @Override
    public SearchResultsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_results_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultsListAdapter.ViewHolder holder, final int position)
    {

        ColorWrapper ColorSuggestion = mDataSet.get(position);
        holder.mColorName.setText(ColorSuggestion.getName());
        holder.mColorValue.setText(ColorSuggestion.getHex());
        //holder.mColorValue.setText("#0000FF");


        int Color = android.graphics.Color.parseColor("#0000FF");
        //int Color = android.graphics.Color.parseColor(ColorSuggestion.getHex());
        holder.mColorName.setTextColor(Color);
        holder.mColorValue.setTextColor(Color);

        if(mLastAnimatedItemPosition < position){
            animateItem(holder.itemView);
            mLastAnimatedItemPosition = position;
        }

        if(mItemsOnClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    mItemsOnClickListener.onClick(mDataSet.get(position));
                }
            });
        }

        holder.mColorLinkButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //determinateBtnClick(v);
                //indeterminateBtnClick(v);

                String fileId = "abc";
                String customerPassword = "2222";
                String artistName = ColorSuggestion.getHex();
                String songName = ColorSuggestion.getName();
                String downloadLink = ColorSuggestion.getRgb();

                MyPlayground.downloadAndPurchasePermissionDialog(context, fileId,
                            songName, artistName);

                Log.v(" button", ColorSuggestion.getRgb());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void animateItem(View view) {
        view.setTranslationY(Util.getScreenHeight((Activity) view.getContext()));
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }




    public  void determinateBtnClick(View view)
    {

        determinateType = new SnackProgressBar(
                SnackProgressBar.TYPE_DETERMINATE, "TYPE_DETERMINATE.")
                // (optional) set max progress, default = 100
                .setProgressMax(100)
                // (optional) show percentage, default = TRUE
                .setShowProgressPercentage(true);



        snackProgressBarManager = new SnackProgressBarManager(view)
                .setProgressBarColor(R.color.colorAccent)
                // (optional) register onDisplayListener
                .setOnDisplayListener(new SnackProgressBarManager.OnDisplayListener() {
                    @Override
                    public void onShown(int onDisplayId)
                    {}

                    @Override
                    public void onDismissed(int onDisplayId)
                    {}
                });


        // or call to show via SnackProgressBar itself
        snackProgressBarManager.show(determinateType, SnackProgressBarManager.LENGTH_INDEFINITE);
        new CountDownTimer(5000, 50) {

            int i = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                snackProgressBarManager.setProgress(i);
            }

            @Override
            public void onFinish() {
                snackProgressBarManager.dismiss();
            }
        }.start();
    }
    public void indeterminateBtnClick(View view) {
        SnackProgressBar indeterminateType = new SnackProgressBar(SnackProgressBar.TYPE_INDETERMINATE, "TYPE_INDETERMINATE.");
        snackProgressBarManager.show(indeterminateType, SnackProgressBarManager.LENGTH_INDEFINITE);
        new CountDownTimer(8000, 2000) {

            int i = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                // get the currently showing indeterminateType and change the message
                SnackProgressBar snackProgressBar = snackProgressBarManager.getLastShown();
                snackProgressBar.setMessage("TYPE_INDETERMINATE - " + i);
                // calling updateTo() will not hide and show again the SnackProgressBar
                snackProgressBarManager.updateTo(snackProgressBar);
            }

            @Override
            public void onFinish() {
                snackProgressBarManager.dismiss();
            }
        }.start();
    }
}
