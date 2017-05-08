package com.pharmacy.pharmacy.Customs;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;

import com.android.volley.cache.plus.ImageCache;


public class LruBitmapCache extends LruCache<String, Bitmap> implements
        ImageCache {
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
 
        return cacheSize;
    }
 
    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }
 
    public LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }
 
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }
 
	@Override
	public BitmapDrawable getBitmap(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putBitmap(String url, BitmapDrawable bitmap) {
		// TODO Auto-generated method stub
		//putBitmap(url, bitmap);
	}

	@Override
	public void invalidateBitmap(String url) {
		// TODO Auto-generated method stub
		//invalidateBitmap(url);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
