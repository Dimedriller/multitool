package com.dimedriller.presenter;

import android.os.Parcel;
import android.view.View;
import android.view.ViewGroup;

public class SimpleViewPlacer implements ViewPlacer {
    public static final Creator<SimpleViewPlacer> CREATOR = new Creator<SimpleViewPlacer>() {
        @Override
        public SimpleViewPlacer createFromParcel(Parcel source) {
            return new SimpleViewPlacer();
        }

        @Override
        public SimpleViewPlacer[] newArray(int size) {
            return new SimpleViewPlacer[size];
        }
    };

    @Override
    public void attachView(ViewGroup containerView, View view) {
        containerView.setSaveEnabled(false);
        containerView.addView(view);
    }

    @Override
    public void detachView(ViewGroup containerView, View view) {
        containerView.removeView(view);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // No action
    }
}
