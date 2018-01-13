package com.example.administrator.musicplayer2.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.musicplayer2.R;
import com.example.administrator.musicplayer2.domain.Item;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.example.administrator.musicplayer2.List.ListFragment.OnListFragmentInteraction;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MusicHolder> {

    OnListFragmentInteraction mListener;        // 넘겨받은 자원
    OnListFragmentInter mListInter;

    List<Item> datas;                           // 사용할 자원
    Context context;
   public MusicHolder holder;

    int position;                               // 넘겨줄 자원


    // 생성자
    public ListAdapter(List<Item> datas, OnListFragmentInteraction mListener, OnListFragmentInter mListInter) {
        this.datas = datas;
        this.mListener = mListener;
        this.mListInter = mListInter;
    }

    // 뷰 생성
    @Override
    public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);

        return holder = new MusicHolder(view);
    }

    // 데이터 넣어주기
    @Override
    public void onBindViewHolder(MusicHolder holder, int position) {

        this.position = position;

        Item item = datas.get(position);

        holder.title.setText(item.title);
        holder.albumTitle.setText(item.albumTitle);
        holder.no.setText(position + "");
//        holder.musicUri = item.musicUri;
        holder.position = position;
//        holder.listTitle = item.title;
//        holder.albumUri = item.albumUri;
        holder.albumListTitle = item.albumTitle;
        Glide.with(context).load(item.albumUri).placeholder(R.mipmap.android4)
                .bitmapTransform(new CropCircleTransformation(context)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return datas.size();
    }



    public void goDetail(int position, String title){

        mListener.onListFragmentInteraction(position, title);
    }

//    TODO 이거 위에랑 똑같은데 왜 오류가 생기는거지
//    public void playVisibility(boolean isClicked){
//
//        if(isClicked){
//
//            holder.setVisible();
//        } else {
//
//            holder.setInvisible();
//        }
//    }

// position 값만 넣어주면 그 값 이외 모든 항목은 false 그 값만 true 로 바꿔준다.
//    public void onItemClicked(int position){
//
//        for(Item item : datas){
//
//            item.isClicked = false;
//        }
//
//        datas.get(position).isClicked = true;
//
//        notifyDataSetChanged();                     // 이거 안 해주면 안 됨. 어떻게 동작하는지 좀 더 연구해보자.
//    }

    class MusicHolder extends RecyclerView.ViewHolder {

//        Uri musicUri;                   // 받아온 자원
//        String listTitle, albumUri;
        String albumListTitle;
        int position;
        View itemView;

        ImageView imageView;            // 위젯
        TextView title, albumTitle, no;

        public MusicHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;

            setViews(itemView);

            setListener();
        }

        // findViewById
        public void setViews(View itemView){

            imageView = (ImageView) itemView.findViewById(R.id.albumListImageView);
            title = (TextView) itemView.findViewById(R.id.albumListTitle);
            albumTitle = (TextView) itemView.findViewById(R.id.albumTitle);
            no = (TextView) itemView.findViewById(R.id.no);
        }

        // 리스너
        public void setListener(){

            itemView.setOnClickListener(itemListener);
        }

        // 아이템뷰 클릭 메소드
        View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goDetail(position, "");
                Log.e("어댑터에서 포지션 확인", position+"");
            }
        };

    }




    public interface OnListFragmentInter {

        void setListImage(String musicUri);
        void setListText(String title);
        void setPlay();
        void setPause();
    }


}
