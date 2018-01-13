package com.example.administrator.musicplayer2.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MusicLab {

    private static MusicLab sMusicLab;
    private Context context;
    private Set<Item> datas;            // 값이 중복되지 않도록 set 으로 해준다.

    private MusicLab(Context context){
        this.context = context;
        datas = new HashSet<>();
    }


    public static MusicLab getInstance(Context context){
        if(sMusicLab == null){
            sMusicLab = new MusicLab(context);
        }
        return sMusicLab;
    }

    // 데이터 로딩해주기
    public void Loader(){

        // 앨범아트 테이블의 데이터를 먼저 로드해 둔다
        setAlbumArt(context);

        // 기존의 내가 하는 방식으로 해 주지 않는 것 즉, 로더를 호출할 때마다 배열을 생성해줘서 갱신할 필요가 없게 한
        // 것과 조금 다르다. 아마 2000개 3000개까지 넘어가면 계속 생성해 내는 것에 부하가 생길 수 있기 때문인 듯 하다.

        // 1. 리졸버
        ContentResolver resolver = context.getContentResolver();

        // 2. 테이블 주소값
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // 3. 칼럼명
        String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM};

        // 4. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, null);

        while(cursor.moveToNext()){
            String id = getValue(cursor, proj[0]);
            String albumId = getValue(cursor, proj[2]);
            String title = getValue(cursor, proj[1]);
            String albumTitle = getValue(cursor, proj[3]);

            Uri musicUri = getMusicUri(getValue(cursor, proj[0]));
            //Uri albumUri = getAlbumUri(getValue(cursor, proj[0]));
            String albumUri = albumMap.get(Integer.parseInt(albumId));
            Log.d("확인", albumUri + "");

            Item item = new Item();
            item.id = id;
            item.title = title;
            item.album = albumId;
            item.musicUri = musicUri;
            item.albumUri = albumUri;           // 글라이드는 스트링 값도 알아서 처리해 주는군
            item.albumTitle = albumTitle;

            datas.add(item);
        }
        cursor.close();
    }

    public List<Item> itemLoader(String getAlbumTitle){

        List<Item> albumItems = new ArrayList<>();

        // 앨범아트 테이블의 데이터를 먼저 로드해 둔다
        setAlbumArt(context);

        // 1. 리졸버
        ContentResolver resolver = context.getContentResolver();

        // 2. 테이블 주소값
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // 3. 칼럼명
        String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media.ALBUM};

        // 4. 쿼리
//        Cursor cursor = resolver.query(uri, proj, null, null, null);

        String whereClause =  MediaStore.Audio.Media.ALBUM + " = ?";
        String[] whereArgs = { getAlbumTitle };
        Cursor cursor = resolver.query(uri, proj, whereClause, whereArgs, null);

        while(cursor.moveToNext()){
            String id = getValue(cursor, proj[0]);
            String albumId = getValue(cursor, proj[2]);
            String title = getValue(cursor, proj[1]);
            String albumTitle = getValue(cursor, proj[3]);

            Uri musicUri = getMusicUri(getValue(cursor, proj[0]));
            //Uri albumUri = getAlbumUri(getValue(cursor, proj[0]));
            String albumUri = albumMap.get(Integer.parseInt(albumId));
            Log.d("확인", albumUri + "");

            Item item = new Item();
            item.id = id;
            item.title = title;
            item.album = albumId;
            item.musicUri = musicUri;
            item.albumUri = albumUri;           // 글라이드는 스트링 값도 알아서 처리해 주는군
            item.albumTitle = albumTitle;

            albumItems.add(item);
        }

        cursor.close();

        return albumItems;
    }

    // 값을 꺼내오는 작업을 메소드로 따로 빼줌
    public String getValue(Cursor cursor, String str){
        int columnIndex = cursor.getColumnIndex(str);
        return cursor.getString(columnIndex);
    }

    // 음악 Uri 값 찾아오기
    public Uri getMusicUri(String id){
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri musicUri = Uri.withAppendedPath(uri, id);
        return musicUri;
    }

    // 앨범자켓 Uri 값 찾아오기
    public Uri getAlbumUri(String albumId){
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Uri albumUri = Uri.withAppendedPath(uri, albumId);

//        로그 찍어서 경로를 확인한 후 추측해보자 -- 안 되는군
        String albumUri = "content://media/external/audio/albumart/";
        Uri resultUri = Uri.parse(albumUri + albumId);
        Log.d("MusicLab","resultUri========================="+resultUri);
        Log.d("MusicLab","uri.path========================="+new File(resultUri.getPath()).getAbsolutePath());
        return resultUri;
//        return albumUri;
    }

    // 데이터 리턴해주기
    public List<Item> getDatas(){
        return new ArrayList<>(datas);
    }


    // 앨범아트 데이터만 따로 저장
    private HashMap<Integer, String> albumMap = new HashMap<>(); //앨범아이디와 썸네일 경로 저장
    private void setAlbumArt(Context context) {

        String[] Album_cursorColumns = new String[]{
                MediaStore.Audio.Albums.ALBUM_ART, //앨범아트
                MediaStore.Audio.Albums._ID
        };

        Cursor Album_cursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                Album_cursorColumns, null, null, null);
        if (Album_cursor != null) { //커서가 널값이 아니면
            if (Album_cursor.moveToFirst()) { //처음참조
                int albumArt = Album_cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                int albumId = Album_cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
                do {
                    if (!albumMap.containsKey(Integer.parseInt(Album_cursor.getString(albumId)))) { //맵에 앨범아이디가 없으면
                        albumMap.put(Integer.parseInt(Album_cursor.getString(albumId)), Album_cursor.getString(albumArt)); //집어넣는다
                    }
                } while (Album_cursor.moveToNext());
            }
        }
        Album_cursor.close();
    }

}