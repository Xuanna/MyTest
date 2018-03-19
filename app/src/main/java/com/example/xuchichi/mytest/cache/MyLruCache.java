package com.example.xuchichi.mytest.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by xuchichi on 2018/3/18.
 */

public class MyLruCache {

    int maxMemory= (int) (Runtime.getRuntime().maxMemory()/1024);

    int cacheSize=maxMemory/8;
    LruCache<String,Bitmap> mMemoryCache=new LruCache<String, Bitmap>(cacheSize){
        /**
         * 计算缓存对象的大小
         * @param key
         * @param value
         * @return
         */
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes()*value.getHeight()/1024;
        }

    };

  public void put(String key,Bitmap value){
      mMemoryCache.put(key,value);
  }
  public void remove(String key){
      mMemoryCache.remove(key);
  }
  public Bitmap getBitmap(String key){
    return  mMemoryCache.get(key);
  }

}
