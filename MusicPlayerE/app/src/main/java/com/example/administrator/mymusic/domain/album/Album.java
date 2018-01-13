package com.example.administrator.mymusic.domain.album;

/**
 * Created by Administrator on 2017-06-27.
 */

public class Album {

   public String albumId;
   public String albumTitle;
   public String albumUri;

   @Override
   public int hashCode() {
      return albumId.hashCode();
   }

   @Override
   public boolean equals(Object album) {

      if(album == null) return false;

      if(!(album instanceof Album)) return false;

      return albumId.hashCode() == album.hashCode();
   }
}
