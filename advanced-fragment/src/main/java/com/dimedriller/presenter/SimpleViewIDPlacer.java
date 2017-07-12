package com.dimedriller.presenter;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

public class SimpleViewIDPlacer implements ViewPlacer {
    private final @IdRes int mAnchorViewID;

    public SimpleViewIDPlacer(int anchorViewID) {
        mAnchorViewID = anchorViewID;
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
}
