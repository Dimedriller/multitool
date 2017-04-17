package com.dimedriller.presenter;

import android.view.View;
import android.view.ViewGroup;

public class ViewSimplePlacer implements ViewPlacer {
    @Override
    public void attachView(ViewGroup parentView, View view) {
        parentView.addView(view);
    }

    @Override
    public void detachView(ViewGroup parentView, View view) {
        parentView.removeView(view);
    }
}
