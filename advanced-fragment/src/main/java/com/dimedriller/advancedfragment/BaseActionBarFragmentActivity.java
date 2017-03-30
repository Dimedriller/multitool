package com.dimedriller.advancedfragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.dimedriller.advancedfragment.actionbar.ActionBarInterface;
import com.dimedriller.advancedfragment.navigation.ActionBarRootNavigationManager;

public abstract class BaseActionBarFragmentActivity extends BaseFragmentActivity {
    private final ActionBarInterface mActionBarInterface = new ActionBarInterface();
    private final ActionBarRootNavigationManager mNavigationManager
            = new ActionBarRootNavigationManager(mActionBarInterface);

    protected abstract @LayoutRes int getContentLayoutID();
    protected abstract @IdRes int getToolbarViewID();
    protected abstract @IdRes int getAnchorViewID();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentLayoutID());

        mNavigationManager.onAttach(getSupportFragmentManager(), getAnchorViewID());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Toolbar toolbarView = (Toolbar) findViewById(getToolbarViewID());
        setSupportActionBar(toolbarView);
        mActionBarInterface.onAttach(toolbarView);
    }

    @Override
    protected void onDestroy() {
        mActionBarInterface.onDetach();
        mNavigationManager.onDetach();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationManager.onBackPressed())
            return;

        finish();
    }

    protected ActionBarRootNavigationManager getNavigationManager() {
        return mNavigationManager;
    }
}
