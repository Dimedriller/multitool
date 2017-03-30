package com.dimedriller.advancedfragment;

import android.support.v7.app.ActionBarIndicatorState;

public abstract class RootFragment<VI extends ViewInterface, M> extends BaseFragment<VI, M> {
    protected RootFragment(VI viewInt) {
        super(viewInt);
    }

    protected RootFragment(Class<VI> viewIntClass) {
        super(viewIntClass);
    }

    public ActionBarIndicatorState getActionBarIndicatorState() {
        return ActionBarIndicatorState.BACK;
    }

    public String getTitle() {
        return null;
    }
}
