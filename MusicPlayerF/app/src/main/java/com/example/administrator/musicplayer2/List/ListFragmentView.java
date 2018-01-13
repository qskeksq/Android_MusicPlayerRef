package com.example.administrator.musicplayer2.List;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.musicplayer2.IController;
import com.example.administrator.musicplayer2.R;
import com.example.administrator.musicplayer2.Util.Controller;
import com.example.administrator.musicplayer2.Util.Player;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017-06-20.
 */

public class ListFragmentView implements View.OnClickListener, IController{

    View view;                      // 넘겨 받은 자원

    RecyclerView recyclerView;      // 사용할 위젯 & 자원
    ConstraintLayout bottomToolbar;
    ImageView listImg;
    TextView listTxt;
    Button listbtn;
    Context context;


    public ListFragmentView(LayoutInflater inflater, ViewGroup container) {

        // 뷰를 만들어 주는 일까지 뷰가 한다.
        view = inflater.inflate(R.layout.fragment_list, container, false);

        setViews();

        setLayout(container.getContext());

        setListener();

        Controller.getInstance().addObserver(this);

        context = container.getContext();

    }

    public void setViews(){

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        bottomToolbar = (ConstraintLayout) view.findViewById(R.id.listController);
        listImg = (ImageView) view.findViewById(R.id.albumListImage);
        listTxt = (TextView) view.findViewById(R.id.listTxt);
        listbtn = (Button) view.findViewById(R.id.albumListBtnPlay);
    }

    public void setListener(){

        bottomToolbar.setOnClickListener(this);
        listbtn.setOnClickListener(this);
    }


    public void setLayout(Context context){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setAdapter(ListAdapter adapter){

        recyclerView.setAdapter(adapter);
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.albumListBtnPlay:
                switch (Player.status){
                    case Player.PLAY:
                        Player.pause();
                        Controller.getInstance().play();
                        break;
                    case Player.PAUSE:
                        Player.play();
                        Controller.getInstance().pause();
                        break;
                }

        }
    }

    // 하단 툴바의 이미지 변경    --> 여기서 처리를 해 주고 다른 곳에서는 인터페이스로 자원을 받아서 메소드만 호출해줘야 한다.
    public void setListImage(String albumUri){

        Glide.with(context).load(albumUri)
                .placeholder(R.mipmap.android4)
                .bitmapTransform(new RoundedCornersTransformation(context, 4,0))
                .into(listImg);

//        Uri uri = getItem().musicUri;  --> 인자로 받는 것이 좋은 생각일 듯 하다

//        listImg.setImageResource(R.mipmap.android3); --> 실험1

//        listImg.setImageURI(albumUri);               --> 실험2  : 실험을 통해 이미지는 나온다는 것을 알았다.

//        Log.e("리스트 화면 확인 ", albumUri+"");      // --> 로그값 : Uri 값이 전달되는 것을 확인한다.
    }

    // 하단 툴바의 제목 변경
    public void setListText(String title){

        listTxt.setText(title);
        listTxt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    // 하단 툴바의 재생 버튼 변경
    public void setPlay(){

        listbtn.setBackgroundResource(R.drawable.ic_action_play);
    }

    // 하단 툴바의 재생 버튼 변경
    public void setPause(){

        listbtn.setBackgroundResource(R.drawable.ic_action_pause);
    }




    @Override
    public void startPlayer() {
        setPlay();
    }

    @Override
    public void pausePlayer() {
        setPause();
    }

}