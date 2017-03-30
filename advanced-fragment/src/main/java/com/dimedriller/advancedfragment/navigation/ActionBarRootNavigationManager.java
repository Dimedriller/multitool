package com.dimedriller.advancedfragment.navigation;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarIndicatorState;

import com.dimedriller.advancedfragment.RootFragment;
import com.dimedriller.advancedfragment.actionbar.ActionBarInterface;

public class ActionBarRootNavigationManager extends RootNavigationManager {
    private final ActionBarInterface mActionBarInterface;

    public ActionBarRootNavigationManager(ActionBarInterface actionBarInterface) {
        mActionBarInterface = actionBarInterface;
    }

    private @Nullable FragmentManager mFragmentManager;

    @Override
    public void onAttach(FragmentManager fragmentManager, @IdRes int anchorViewID) {
        super.onAttach(fragmentManager, anchorViewID);

        mFragmentManager = fragmentManager;
    }

    @Override
    public void onDetach() {
        mFragmentManager = null;

        super.onDetach();
    }

    @Override
    protected void onShowFragment(RootFragment fragment) {
        super.onShowFragment(fragment);

        if (mFragmentManager == null)
            return;

        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            mActionBarInterface.showIndicator(ActionBarIndicatorState.HOME, false);
            mActionBarInterface.showTitle("");
            return;
        }

        ActionBarIndicatorState indicatorState;
        if (canGoBack()) {
            RootFragment topFragment = getFragmentFromBackStack(mFragmentManager, backStackEntryCount - 1);
            indicatorState = topFragment.getActionBarIndicatorState();
        } else
            indicatorState = ActionBarIndicatorState.HOME;
        mActionBarInterface.showIndicator(indicatorState, false);

        for(int indexFragment = backStackEntryCount - 1; indexFragment >= 0; indexFragment--) {
            RootFragment topFragment = getFragmentFromBackStack(mFragmentManager, indexFragment);
            String title = topFragment.getTitle();
            if (title != null) {
                mActionBarInterface.showTitle(title);
                break;
            }
        }
    }
}
