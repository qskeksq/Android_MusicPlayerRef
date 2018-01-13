package com.example.administrator.mymusic.domain.music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017-06-27.
 */

public class MusicLab {

    Set<Music> data;
    List<Music> returnData;
    Map<String, String> albumMap;

    Context context;
    private static MusicLab sMusicLab;

    private MusicLab(Context context) {

        this.context = context;
        albumMap = new HashMap<>();
        data = new HashSet<>();
        returnData = new ArrayList<>();
    }

    public static MusicLab getInstance(Context context){
        if(sMusicLab == null){
            sMusicLab = new MusicLab(context);
        }
        return sMusicLab;

    }


    // TODO 아마 이거 두 번 호출하면 중복 처리 안 해줘서 두개씩 생길 것임
    public void loadData() {

        // 0. 앨범 셋
        loadAlbumArt();

        // 1.
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        ContentResolver resolver = context.getContentResolver();

        // 2.
        String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM};

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, null); // TODO 정렬
        cursor.moveToFirst();

        // 4.

        while (cursor.moveToNext()) {
            Music album = new Music();
            album.musicId = cursor.getString(cursor.getColumnIndex(proj[0]));
            Log.e("musicId", album.musicId);
            album.musicTitle = cursor.getString(cursor.getColumnIndex(proj[1]));
            Log.e("musicTitle", album.musicTitle);
            album.albumTitle = cursor.getString(cursor.getColumnIndex(proj[2]));
            Log.e("albumTitle", album.albumTitle);

            // todo key 값에셔 문제가 생길 수 있다! 확인하자
            album.musicUri = makeMusicUri(album.musicId);
            Log.e("musicUri", album.musicUri + "");
            album.albumArtUri = albumMap.get(album.albumTitle);
            Log.e("albumArtUri", album.albumArtUri + "");

            data.add(album);
        }

    }


    // 이렇게 해주는 이유가 앨범아트가 쿼리 소속이 다르기 때문이다. MediaStore.Audio.Albums.ALBUM_ART 에 들어가 있는데 위에서 쿼리하는
    // 테이블 주소가 달라서 따로 앨범아트는 쿼리를 해줘야 한다. 그리고 그 각각 앨범아트와 음악을 연결해주기 위해서 맵을 이용해서 연결해준다.
    public void loadAlbumArt(){
        // 1.
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        ContentResolver resolver = context.getContentResolver();

        // 2.
        String[] proj = {MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART};

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, null);
        cursor.moveToFirst();

        // 4.

        while(cursor.moveToNext()){

            String key = cursor.getString(cursor.getColumnIndex(proj[0]));
            String value = cursor.getString(cursor.getColumnIndex(proj[1]));

            if(!albumMap.containsKey(cursor.getString(cursor.getColumnIndex(proj[0])))){
                albumMap.put(key, value);
            }
        }
    }

    public Uri makeMusicUri(String id){

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri musicUri = Uri.withAppendedPath(uri, id);
        return musicUri;
    }

    // Todo 여기서 안 되면 밑으로 가라
    public List<Music> getDatas(){
        returnData.removeAll(data);
        returnData.addAll(data);
        return returnData;
    }

//    public List<Music> getDatas2(){
//
//        return new ArrayList<>(data);
//    }


}
