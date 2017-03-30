package com.dimedriller.advancedfragment.actionbar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarIndicatorState;
import android.support.v7.widget.Toolbar;

public class ActionBarInterface {
    private @Nullable Toolbar mToolbarView;

    public void onAttach(@NonNull Toolbar toolbarView) {
        mToolbarView = toolbarView;
        toolbarView.setNavigationIcon(new ActionBarIndicatorDrawable(toolbarView.getContext()));
    }

    public void onDetach() {
        mToolbarView = null;
    }

    public void showIndicator(ActionBarIndicatorState indicatorState, boolean isForced) {
        if (mToolbarView == null)
            return;

        ActionBarIndicatorDrawable indicatorDrawable = (ActionBarIndicatorDrawable) mToolbarView.getNavigationIcon();
        //noinspection ConstantConditions
        indicatorDrawable.showState(indicatorState, isForced);
    }

    public void showTitle(String title) {
        if (mToolbarView == null)
            return;

        mToolbarView.setTitle(title);
    }

}
