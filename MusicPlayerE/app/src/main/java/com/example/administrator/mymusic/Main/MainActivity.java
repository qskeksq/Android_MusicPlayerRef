package com.example.administrator.mymusic.Main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.mymusic.Detail.DetailFragment;
import com.example.administrator.mymusic.List.ListPager.ListPagerFragment;
import com.example.administrator.mymusic.List.SMusicList.SMusicFragment;
import com.example.administrator.mymusic.R;
import com.example.administrator.mymusic.Util.CheckPermission;

/**
 *  MainActivity mainly focuses on
 *  1. managing fragment
 *  2. inter-fragment interaction
 *
 */

public class MainActivity extends AppCompatActivity implements IInteraction, CheckPermission.CallBack {

    ListPagerFragment pagerFragment;
    DetailFragment detailFragment;
    SMusicFragment sMusicFragment;

    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing method, should be on top of all others
        initialize();

        makeFragment();

        CheckPermission.checkVersion(this);

    }

    public void initialize(){
        manager = getSupportFragmentManager();

    }

    /**
     * create fragment
     * when called, should be staged previous to other initiating methods
     */
    public void makeFragment(){

        pagerFragment = ListPagerFragment.newInstance();
        detailFragment = DetailFragment.newInstance(0);
        sMusicFragment = SMusicFragment.newInstance();
    }

    public void addPagerFragment(){

        manager.beginTransaction().add(R.id.fragmentMainContainer, pagerFragment).commit();
    }

    @Override
    public void addDetailFragment(){

        manager.beginTransaction().addToBackStack(null).add(R.id.fragmentMainContainer, detailFragment).commit();
    }

    @Override
    public void addSListFragment(int category, String identifier) {

        sMusicFragment.setIdentifier(identifier);

        sMusicFragment.setCategory(category);

        manager.beginTransaction().addToBackStack(null).add(R.id.fragmentMainContainer, sMusicFragment).commit();
    }




    @Override
    public void callInit() {

        addPagerFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        CheckPermission.onResult(requestCode, grantResults, this);
    }
}
