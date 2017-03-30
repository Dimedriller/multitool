package com.dimedriller.advancedfragment.navigation;

import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dimedriller.advancedfragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class NavigationManager<F extends BaseFragment> {
    private @IdRes int mAnchorViewID;
    private @Nullable FragmentManager mFragmentManager;

    private final Handler mHandler = new Handler();

    public void onAttach(FragmentManager fragmentManager, @IdRes int anchorViewID) {
        mAnchorViewID = anchorViewID;
        mFragmentManager = fragmentManager;
    }

    public void onDetach() {
        mFragmentManager = null;
        mAnchorViewID = 0;
    }

    protected String generateFragmentTag(F fragment) {
        return fragment.getClass().getName();
    }

    protected void onShowFragment(F fragment) {
        // No action
    }

    private void scheduleOnShowFragmentEvent(@NonNull final F fragment) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onShowFragment(fragment);
            }
        });
    }

    public boolean showFragmentOnly(@NonNull F fragment, @NonNull String tag) {
        if (mFragmentManager == null)
            return false;

        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mFragmentManager.beginTransaction()
                .add(mAnchorViewID, fragment, tag)
                .addToBackStack(tag)
                .commit();

        scheduleOnShowFragmentEvent(fragment);
        return true;
    }


    public boolean showFragmentOnly(@NonNull F fragment) {
        String tag = generateFragmentTag(fragment);
        return showFragmentOnly(fragment, tag);
    }

    protected F getFragmentFromBackStack(@NonNull FragmentManager fragmentManager, int indexFragment) {
        FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(indexFragment);
        //noinspection unchecked
        return (F) fragmentManager.findFragmentByTag(backStackEntry.getName());
    }

    public boolean showFragmentOver(@NonNull F fragment, @NonNull String tag) {
        if (mFragmentManager == null)
            return false;

        List<F> fragmentToRemoveList = new ArrayList<F>();
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        for(int indexFragment = backStackEntryCount - 1; indexFragment >= 0; indexFragment--) {
            F fragmentToRemove = getFragmentFromBackStack(mFragmentManager, indexFragment);
            if (fragmentToRemove.isAdded())
                fragmentToRemoveList.add(fragmentToRemove);
            else
                break;
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for(F fragmentToRemove : fragmentToRemoveList)
            fragmentTransaction.remove(fragmentToRemove);
        fragmentTransaction.add(mAnchorViewID, fragment, tag)
                .addToBackStack(tag)
                .commit();
        scheduleOnShowFragmentEvent(fragment);
        return true;
    }

    public boolean showFragmentOver(@NonNull F fragment) {
        String tag = generateFragmentTag(fragment);
        return showFragmentOver(fragment, tag);
    }

    public boolean showFragmentOverlap(@NonNull F fragment, @NonNull String tag) {
        if (mFragmentManager == null)
            return false;

        mFragmentManager.beginTransaction()
                .add(mAnchorViewID, fragment, tag)
                .addToBackStack(tag)
                .commit();

        scheduleOnShowFragmentEvent(fragment);
        return true;
    }

    public boolean showFragmentOverlap(@NonNull F fragment) {
        String tag = generateFragmentTag(fragment);
        return showFragmentOverlap(fragment, tag);
    }

    public boolean showFragmentSubstitute(@NonNull F fragment, @NonNull String tag) {
        if (mFragmentManager == null)
            return false;

        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if (backStackEntryCount > 0)
            mFragmentManager.popBackStack();

        mFragmentManager.beginTransaction()
                .add(mAnchorViewID, fragment, tag)
                .addToBackStack(tag)
                .commit();

        scheduleOnShowFragmentEvent(fragment);
        return true;
    }

    public boolean showFragmentSubstitute(@NonNull F fragment) {
        String tag = generateFragmentTag(fragment);
        return showFragmentSubstitute(fragment, tag);
    }

    public boolean showFragment(@NonNull F fragment, @NonNull String tag, ShowMethod showMethod) {
        return showMethod.showFragment(this, fragment, tag);
    }

    public boolean showFragment(@NonNull F fragment, ShowMethod showMethod) {
        String tag = generateFragmentTag(fragment);
        return showFragment(fragment, tag, showMethod);
    }

    public boolean canGoBack() {
        if (mFragmentManager == null)
            return false;

        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if (backStackEntryCount == 0)
            return false;
        if (backStackEntryCount > 1)
            return true;

        F topFragment = getFragmentFromBackStack(mFragmentManager, backStackEntryCount - 1);
        return topFragment.canGoBack();
    }

    public boolean popTop() {
        if (mFragmentManager == null)
            return false;

        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if (backStackEntryCount < 2)
            return false;

        F prevTopFragment = getFragmentFromBackStack(mFragmentManager, backStackEntryCount - 2);
        mFragmentManager.popBackStack();
        scheduleOnShowFragmentEvent(prevTopFragment);
        return true;
    }

    public boolean onBackPressed() {
        if (mFragmentManager == null)
            return false;

        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if (backStackEntryCount == 0)
            return false;

        F fragment = getFragmentFromBackStack(mFragmentManager, backStackEntryCount - 1);
        //noinspection SimplifiableIfStatement
        if (fragment.onBackPressed())
            return true;
        else
            return popTop();
    }
}
