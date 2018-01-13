package com.example.administrator.mymusic.domain.album;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017-06-27.
 */

public class AlbumLab {

    Set<Album> data;
    List<Album> returnData;
    Map<String, String> albumMap;

    Context context;
    private static AlbumLab sAlbumLab;

    private AlbumLab(Context context) {

        this.context = context;
        albumMap = new HashMap<>();
        data = new HashSet<>();
        returnData = new ArrayList<>();
    }

    public static AlbumLab getInstance(Context context){
        if(sAlbumLab == null){
            sAlbumLab = new AlbumLab(context);
        }
        return sAlbumLab;

    }


    // TODO 아마 이거 두 번 호출하면 중복 처리 안 해줘서 두개씩 생길 것임
    public void loadData() {

        // 1.
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        ContentResolver resolver = context.getContentResolver();

        // 2.
        String[] proj = {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ALBUM_ART};

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, null);
        cursor.moveToFirst();

        // 4.

        while (cursor.moveToNext()) {
            Album album = new Album();
            album.albumId = cursor.getString(cursor.getColumnIndex(proj[0]));
            album.albumTitle = cursor.getString(cursor.getColumnIndex(proj[1]));
            album.albumUri = cursor.getString(cursor.getColumnIndex(proj[2]));

            data.add(album);
        }

    }

    // Todo 여기서 안 되면 밑으로 가라
    public List<Album> getDatas(){
        returnData.removeAll(data);
        returnData.addAll(data);
        return returnData;
    }

}
