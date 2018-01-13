package com.example.administrator.musicplayer.adapter.page;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.musicplayer.R;
import com.example.administrator.musicplayer.domain.Song;
import com.example.administrator.musicplayer.lib.GoLib;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-10-11.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.Holder> {

    List<Song> songList = new ArrayList<>();
    Context context;

    public SongAdapter(List<Song> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    public void setData(List<Song> songList){
        this.songList = songList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.title.setText(songList.get(position).title);
        holder.artist.setText(songList.get(position).artist);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoLib.getInstance().goPlayerActivity(context, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView title, artist, length;
        View itemView;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = (TextView) itemView.findViewById(R.id.songTitle);
            artist = (TextView) itemView.findViewById(R.id.songArtist);
            length = (TextView) itemView.findViewById(R.id.songLength);
        }
    }

}
