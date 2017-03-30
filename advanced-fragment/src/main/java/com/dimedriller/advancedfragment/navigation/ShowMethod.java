package com.dimedriller.advancedfragment.navigation;

import com.dimedriller.advancedfragment.BaseFragment;

public enum ShowMethod {
    ONLY {
        @Override
        <F extends BaseFragment> boolean showFragment(NavigationManager<F> navigationManager, F fragment, String tag) {
            return navigationManager.showFragmentOnly(fragment, tag);
        }
    },
    OVERRIDE_TOP {
        @Override
        <F extends BaseFragment> boolean showFragment(NavigationManager<F> navigationManager, F fragment, String tag) {
            return navigationManager.showFragmentOver(fragment, tag);
        }
    },
    OVERLAP {
        @Override
        <F extends BaseFragment> boolean showFragment(NavigationManager<F> navigationManager, F fragment, String tag) {
            return navigationManager.showFragmentOverlap(fragment, tag);
        }
    },
    SUBSTITUTE_TOP {
        @Override
        <F extends BaseFragment> boolean showFragment(NavigationManager<F> navigationManager, F fragment, String tag) {
            return navigationManager.showFragmentSubstitute(fragment, tag);
        }
    };

    abstract <F extends BaseFragment> boolean showFragment(NavigationManager<F> navigationManager,
            F fragment,
            String tag);
}
