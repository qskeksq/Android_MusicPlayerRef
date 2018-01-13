package com.example.administrator.musicplayer2.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AlbumLab {

    Context context;

    public static AlbumLab sAlbumLab;
    private HashMap<Integer, String> albumMap = new HashMap<>();
    private List<Album> mAlbums;

    private AlbumLab(Context context){

        this.context = context;
        mAlbums = new ArrayList<>();
    }

    public static AlbumLab getInstance(Context context){

        if(sAlbumLab == null){
            sAlbumLab = new AlbumLab(context);
        }

        return sAlbumLab;
    }

    public void loader(){

        // 0. 앨범 아트 세팅
//        setAlbumArt(context);         // 일단 여기서 문제가 생겼다. 그대로 갖다 붙였는데 칼럼을 못 찾겠다고 하는군

        // 1. resolver & uri
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        ContentResolver resolver = context.getContentResolver();

        // 2.
        String[] proj = {MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART};    // 앨범 제목은 ALBUM, 아이디가 ALBUM_ID 임

        // 3.
        Cursor cursor = resolver.query(uri, proj, null, null, null);

        // 4. 쿼리  TODO 여기서 map 을 사용할 것인지, equals 를 사용할 것인지 모두 실험해본다.

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Album album = new Album();
                album.title = cursor.getString(cursor.getColumnIndex(proj[0]));
                album.albumUri = cursor.getString(cursor.getColumnIndex(proj[1]));
                mAlbums.add(album);
            }
            cursor.close();
        }

    }



    public List<Album> getDatas(){

        return mAlbums;
    }

    private void setAlbumArt(Context context){

        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        String[] proj = {MediaStore.Audio.Albums.ALBUM_ID, MediaStore.Audio.Albums.ALBUM_ART};

        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);

        if(cursor != null){
            while(cursor.moveToNext()){
                int albumId = cursor.getColumnIndex(proj[0]);
                int albumArt = cursor.getColumnIndex(proj[1]);

                int albumIdIndex = Integer.parseInt(cursor.getString(albumId));
                String albumArtUri = cursor.getString(albumArt);

                if(!albumMap.containsKey(Integer.parseInt(cursor.getString(albumArt)))){
                    albumMap.put(albumIdIndex, albumArtUri);
                }
            }
            cursor.close();
        }

    }


}
