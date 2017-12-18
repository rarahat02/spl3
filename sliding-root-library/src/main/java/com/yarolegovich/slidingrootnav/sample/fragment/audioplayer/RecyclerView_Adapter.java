package com.yarolegovich.slidingrootnav.sample.fragment.audioplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.appthemeengine.Config;
import com.yarolegovich.slidingrootnav.sample.R;
import com.yarolegovich.slidingrootnav.sample.widget.MusicVisualizer;

import java.util.Collections;
import java.util.List;

/**
 * Created by Valdio Veliu on 16-07-08.
 */
public class RecyclerView_Adapter extends RecyclerView.Adapter<ViewHolder>  {

    List<Audio> list = Collections.emptyList();
    Context context;


    public RecyclerView_Adapter(List<Audio> list, Context context)
    {
        this.list = list;
        this.context = context;
        //this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.title.setText(list.get(position).getTitle());

        Audio localItem = list.get(position);


        if(MediaPlayerService.mediaPlayer != null)
        {

/*            if (MediaPlayerService.mediaPlayer. == localItem.getTitle())
            {*/
            holder.title.setTextColor(Config.accentColor(context, "light_theme"));
            if (MediaPlayerService.mediaPlayer.isPlaying()) {
                holder.visualizer.setColor(Config.accentColor(context, "light_theme"));
                //holder.visualizer.setVisibility(View.VISIBLE);
            }
            else holder.visualizer.setVisibility(View.GONE);
        }
        else holder.visualizer.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    TextView title;
    ImageView play_pause;

    protected ImageView albumArt, popupMenu;
    protected MusicVisualizer visualizer;


    ViewHolder(View itemView)
    {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        play_pause = (ImageView) itemView.findViewById(R.id.play_pause);

        this.popupMenu = (ImageView) itemView.findViewById(R.id.popup_menu);
        visualizer = (MusicVisualizer) itemView.findViewById(R.id.visualizer);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
/*                MusicPlayer.playAll(mContext, songIDs, getAdapterPosition(), -1, TimberUtils.IdType.NA, false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemChanged(currentlyPlayingPosition);
                        notifyItemChanged(getAdapterPosition());
                    }
                }, 50);*/
            }
        }, 100);
    }
}