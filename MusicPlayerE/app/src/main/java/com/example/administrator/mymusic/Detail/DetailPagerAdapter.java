package com.example.administrator.mymusic.Detail;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.mymusic.R;
import com.example.administrator.mymusic.domain.music.Music;
import com.example.administrator.mymusic.domain.music.MusicLab;

import java.util.List;

/**
 * Created by Administrator on 2017-06-29.
 */

// todo 클래스 분리
public class DetailPagerAdapter extends PagerAdapter {

    List<Music> data;

    ImageView imageView;


    public DetailPagerAdapter(List<Music> data) {

        this.data = data;
    }

    // 아마 데이터 사이즈 만큼 페이지를 만들어 주는 곳인 듯 하다.
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View layout = LayoutInflater.from(container.getContext()).inflate(R.layout.detail_pager_item, null);

        imageView = (ImageView) layout.findViewById(R.id.detailPagerImg);

        Glide.with(container.getContext()).load(MusicLab.getInstance(container.getContext()).getDatas().get(position).albumArtUri)
                .placeholder(R.mipmap.android).into(imageView);

        container.addView(layout);  // 아하 이거 또 안 해줬다

        return layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

//        destroyItem(container, position, object);
        container.removeView((View)object);
    }



//    // 내장 클래스로 빼주자
//    class PagerHolder {
//
//        ImageView imageView;
//
//        View container;
//
//        public PagerHolder(View container) {
//            this.container = container;
//        }
//
//        public void setViews(View layout){
//            imageView = (ImageView) layout.findViewById(R.id.detailPagerImg);
//        }
//
//        public void inflateView()
//
//    }

}
