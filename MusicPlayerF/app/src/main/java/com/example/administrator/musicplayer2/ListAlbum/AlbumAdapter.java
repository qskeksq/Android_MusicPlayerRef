package com.example.administrator.musicplayer2.ListAlbum;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.musicplayer2.ListAlbum.MusicList.AlbumMusicListFragment;
import com.example.administrator.musicplayer2.R;
import com.example.administrator.musicplayer2.domain.Album;

import java.util.List;


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {


    List<Album> datas;
    FragmentManager manager;
    Context context;

    public AlbumAdapter(List<Album> datas, FragmentManager manager) {

        this.datas = datas;
        this.manager = manager;
    }

    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_album_item, parent, false);

        return new AlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumHolder holder, int position) {

        Album album = datas.get(position);

        holder.albumTxt.setText(album.title);
        holder.title = album.title;

        Glide.with(context).load(album.albumUri).placeholder(R.mipmap.android4).into(holder.albumImg);
    }

    @Override
    public int getItemCount() {

        return datas.size();        // 리사이클러뷰에서 데이터가 하나도 안 뜨면 가장 먼저 여기로 올 것!
    }



    public class AlbumHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView albumImg;
        TextView albumTxt;

        String title;

        public AlbumHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            albumImg = (ImageView) itemView.findViewById(R.id.albumImg);
            albumTxt = (TextView) itemView.findViewById(R.id.albumTitle);
            setListener();
        }

        public void setListener(){

            itemView.setOnClickListener(holderListener);
        }

        View.OnClickListener holderListener = new View.OnClickListener(){
          public void onClick(View view){

                manager.beginTransaction().addToBackStack(null).add(R.id.fragment_container, AlbumMusicListFragment.newInstance(title)).commit();
          }
        };

    }

}
