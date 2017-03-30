package com.dimedriller.advancedfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ViewInterface {
    protected @Nullable View mRootView;

    protected abstract View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState);

    public View createView(LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mRootView = onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }

    protected void onDestroyView() {
        // No action
    }

    public void destroyView() {
        onDestroyView();

        mRootView = null;
    }
}
