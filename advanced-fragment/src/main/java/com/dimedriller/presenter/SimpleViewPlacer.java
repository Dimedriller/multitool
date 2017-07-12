package com.dimedriller.presenter;

import android.view.View;
import android.view.ViewGroup;

public class SimpleViewPlacer implements ViewPlacer {
    @Override
    public void attachView(ViewGroup containerView, View view) {
        containerView.setSaveEnabled(false);
        containerView.addView(view);
    }

    @Override
    public void detachView(ViewGroup containerView, View view) {
        containerView.removeView(view);
    }
}
