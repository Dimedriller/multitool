package com.dimedriller.presenter;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

public interface ViewPlacer extends Parcelable {
    void attachView(ViewGroup parentView, View view);
    void detachView(ViewGroup parentView, View view);
}
