package com.example.administrator.mymusic.List.AlbumList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.mymusic.Main.IInteraction;
import com.example.administrator.mymusic.R;
import com.example.administrator.mymusic.Util.Const;
import com.example.administrator.mymusic.domain.album.Album;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017-06-29.
 */

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumHolder> {

    List<Album> data;
    Context context;

    IInteraction iInteraction;

    public AlbumListAdapter(List<Album> data, IInteraction iInteraction) {
        this.data = data;
        this.iInteraction = iInteraction;
    }

    @Override
    public AlbumListAdapter.AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_album_list_item, parent, false);
        return new AlbumHolder(itemView);

    }

    @Override
    public void onBindViewHolder(AlbumListAdapter.AlbumHolder holder, int position) {
        Album album = data.get(position);
        Glide.with(context).load(album.albumUri).bitmapTransform(new RoundedCornersTransformation(context, 8, 0))
                .placeholder(R.mipmap.android).into(holder.albumItemImg);
        holder.albumItemTitle.setText(album.albumTitle);
        holder.albumTitle = album.albumTitle;

    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView albumItemImg;
        TextView albumItemTitle;

        String albumTitle;

        public AlbumHolder(View itemView) {
            super(itemView);
            albumItemImg = (ImageView) itemView.findViewById(R.id.albumItemImg);
            albumItemTitle = (TextView) itemView.findViewById(R.id.albumItemTitle);
            setListener();
        }

        public void setListener(){

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            iInteraction.addSListFragment(Const.Category.ALBUM, albumTitle);

            Log.e("ALBUM", Const.Category.ALBUM+"");
            Log.e("타이틀", albumTitle);
        }
    }
}
