package com.xinlan.imageeditlibrary.editimage.widget;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EditCache {
    public static final int EDIT_CACHE_SIZE = 10;

    private final int mCacheSize;
    private LinkedList<Bitmap> mCacheList = new LinkedList<Bitmap>();
    private int mCurrent = -1;
    private List<ListModify> mObserverList = new ArrayList<ListModify>(2);

    public EditCache(int cacheSize) {
        if (cacheSize <= 0) {
            cacheSize = EDIT_CACHE_SIZE;
        }
        this.mCacheSize = cacheSize;
    }

    public EditCache() {
        this(EDIT_CACHE_SIZE);
    }

    public static void freeBitmap(Bitmap bit) {
        if (bit != null && !bit.isRecycled()) {
            bit.recycle();
        }
    }

    public int getEditCacheSize() {
        return mCacheSize;
    }

    public synchronized int getSize() {
        return mCacheList.size();
    }

    public int getCur() {
        return mCurrent;
    }

    public String debugLog() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mCacheList.size(); i++) {
            sb.append("{ " + mCacheList.get(i) + " }");
        }
        return sb.toString();
    }

    public synchronized boolean push(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled())
            return false;

        while (!isPointToLastElem()) {
            Bitmap dropBit = mCacheList.pollLast();
            freeBitmap(dropBit);
        }

        Bitmap allReadyHaveBitmap = null;
        for (Bitmap b : mCacheList) {
            if (bitmap == b && !bitmap.isRecycled()) {
                allReadyHaveBitmap = bitmap;
                break;
            }
        }

        if (allReadyHaveBitmap != null) {
            mCacheList.remove(allReadyHaveBitmap);
            mCacheList.addLast(allReadyHaveBitmap);
            trimCacheList();
        } else {
            mCacheList.addLast(bitmap);
            trimCacheList();
        }

        mCurrent = mCacheList.size() - 1;
        notifyListChange();
        return true;
    }

    public synchronized Bitmap getNextCurrentBit() {
        mCurrent--;
        Bitmap ret = getCurBit();
        notifyListChange();
        return ret;
    }

    public synchronized Bitmap getPreCurrentBit() {
        mCurrent++;
        Bitmap ret = getCurBit();
        notifyListChange();
        return ret;
    }


    public boolean checkNextBitExist() {
        int point = mCurrent - 1;
        return point >= 0 && point < mCacheList.size();
    }


    public boolean checkPreBitExist() {
        int point = mCurrent + 1;
        return point >= 0 && point < mCacheList.size();
    }


    public synchronized void removeAll() {
        for (Bitmap b : mCacheList) {
            freeBitmap(b);
        }
        mCacheList.clear();
        notifyListChange();
    }

    public synchronized boolean isPointToLastElem() {
        return mCurrent == mCacheList.size() - 1;
    }

    public void addObserver(final ListModify observer) {
        if (observer != null && !mObserverList.contains(observer)) {
            mObserverList.add(observer);
        }
    }

    public void removeObserver(final ListModify observer) {
        if (observer != null && mObserverList.contains(observer)) {
            mObserverList.remove(observer);
        }
    }

    protected void notifyListChange() {
        for (ListModify observer : mObserverList) {
            observer.onCacheListChange(this);
        }
    }

    public Bitmap getCurBit() {
        if (mCurrent >= 0 && mCurrent < mCacheList.size()) {
            Bitmap bit = mCacheList.get(mCurrent);
            if (bit != null && !bit.isRecycled()) {
                return bit;
            }
        }
        return null;
    }

    private synchronized void trimCacheList() {
        while (mCacheList.size() > mCacheSize) {
            Bitmap dropBit = mCacheList.pollFirst();
            freeBitmap(dropBit);
        }
    }

    public interface ListModify {
        void onCacheListChange(EditCache cache);
    }

}
