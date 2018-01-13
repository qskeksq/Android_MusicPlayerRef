package com.example.administrator.musicplayer;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.administrator.musicplayer.adapter.ListPagerAdapter;
import com.example.administrator.musicplayer.domain.SongLab;
import com.example.administrator.musicplayer.fragment.AlbumFragment;
import com.example.administrator.musicplayer.fragment.ArtistFragment;
import com.example.administrator.musicplayer.fragment.GenreFragment;
import com.example.administrator.musicplayer.fragment.SongFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AlbumFragment.AlbumInteractionListener, ArtistFragment.ArtistInteractionListener,
                    GenreFragment.GenreInteractionListener, SongFragment.SongInteractionListener, IObserver{


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager tabPager;
    private FloatingActionButton fab;
    private NavigationView navView;

    AlbumFragment albumFragment;
    ArtistFragment artistFragment;
    GenreFragment genreFragment;
    SongFragment songFragment;
    private ImageView imgMainPlay;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void init(){
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.basic_background));
        load();
        initView();
        setToolbar();
        setListener();
        setActionBarToggle();
        setTabLayout();
        setTabPager();

        Observer.getInstance().addObserver(this);
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabPager = (ViewPager) findViewById(R.id.tabPager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        navView = (NavigationView) findViewById(R.id.nav_view);
        imgMainPlay = (ImageView) findViewById(R.id.imgMainPlay);
    }

    /**
     * 툴바
     */
    private void setToolbar(){
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.toolbar_title));
    }

    /**
     * 리스너
     */
    private void setListener(){
        navView.setNavigationItemSelectedListener(this);
    }

    /**
     * 액션바 토글버튼 설정
     */
    private void setActionBarToggle(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 탭바 설정
     */
    private void setTabLayout(){
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_songs)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_album)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_artist)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_genre)));
    }

    /**
     * 뷰페이저 설정
     */
    private void setTabPager(){
        List<Fragment> fragmentList = new ArrayList<>();
        albumFragment = new AlbumFragment();
        artistFragment = new ArtistFragment();
        genreFragment = new GenreFragment();
        songFragment = new SongFragment();
        fragmentList.add(songFragment);
        fragmentList.add(albumFragment);
        fragmentList.add(artistFragment);
        fragmentList.add(genreFragment);
        ListPagerAdapter pagerAdapter = new ListPagerAdapter(getSupportFragmentManager(), fragmentList);
        tabPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(tabPager));
        tabPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    /**
     * 음악 데이터 가져오기
     */
    private void load(){
        SongLab.getInstance().load(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onAlbumInteraction() {

    }

    @Override
    public void onSongInteraction() {

    }

    @Override
    public void onGenreInteraction(Uri uri) {

    }

    @Override
    public void onArtistInteraction(Uri uri) {

    }

    @Override
    public void setPlayButton() {
        imgMainPlay.setImageResource(R.drawable.play_rounded_button);
    }

    @Override
    public void setPauseButton() {
        imgMainPlay.setImageResource(R.drawable.round_pause_button);
    }

    @Override
    public void setProgress() {

    }

    @Override
    public void setCurrentTime() {

    }
}
