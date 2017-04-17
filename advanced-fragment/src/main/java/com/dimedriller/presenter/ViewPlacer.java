package com.dimedriller.presenter;

import android.view.View;
import android.view.ViewGroup;

public interface ViewPlacer {
    void attachView(ViewGroup parentView, View view);
    void detachView(ViewGroup parentView, View view);
}
