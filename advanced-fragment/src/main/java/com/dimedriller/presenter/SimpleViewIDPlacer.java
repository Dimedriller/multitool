package com.dimedriller.presenter;

import android.os.Parcel;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

public class SimpleViewIDPlacer implements ViewPlacer {
    public static final Creator<SimpleViewIDPlacer> CREATOR = new Creator<SimpleViewIDPlacer>() {
        @Override
        public SimpleViewIDPlacer createFromParcel(Parcel source) {
            return new SimpleViewIDPlacer(source);
        }

        @Override
        public SimpleViewIDPlacer[] newArray(int size) {
            return new SimpleViewIDPlacer[size];
        }
    };

    private final @IdRes int mAnchorViewID;

    public SimpleViewIDPlacer(int anchorViewID) {
        mAnchorViewID = anchorViewID;
    }

    SimpleViewIDPlacer(Parcel source) {
        mAnchorViewID = source.readInt();
    }

    @Override
    public void attachView(ViewGroup containerView, View view) {
        ViewGroup anchorView = (ViewGroup) containerView.findViewById(mAnchorViewID);
        anchorView.setSaveEnabled(false);
        anchorView.addView(view);
    }

    @Override
    public void detachView(ViewGroup containerView, View view) {
        ViewGroup anchorView = (ViewGroup) containerView.findViewById(mAnchorViewID);
        anchorView.removeView(view);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mAnchorViewID);
    }
}
