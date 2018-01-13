package com.example.administrator.musicplayer2.Detail;


import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.musicplayer2.R;
import com.example.administrator.musicplayer2.domain.Item;

import java.util.List;


public class DetailPagerAdapter extends PagerAdapter {

    List<Item> datas;           // 데이터 가져오기

    ImageView imageView;
    TextView textView;

    public DetailPagerAdapter(List<Item> datas) {

        this.datas = datas;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_pager_item, null);

        imageView = (ImageView) view.findViewById(R.id.imageView2);
        textView = (TextView) view.findViewById(R.id.txtTitle);
        textView.setText(datas.get(position).title);
        Glide.with(container.getContext()).load(datas.get(position).albumUri).placeholder(R.mipmap.android4).into(imageView);
        container.addView(view);        // 이거 빼먹어서 안 됨.

        return view;
    }

    @Override
    public int getCount() {

        return datas.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        // 이거 안 해주면 뷰가 계속 남아서 나중에 생성될 때 충돌된다. 따라서 지워주지 않으면 오류가 나도록 설계됨
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }


    class DetailView {


    }

}
