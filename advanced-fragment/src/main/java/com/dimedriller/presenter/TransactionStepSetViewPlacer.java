package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionStepSetViewPlacer extends TransactionStep {
    private final @NonNull String mTag;
    private final @NonNull ViewPlacer mPlacer;

    public TransactionStepSetViewPlacer(@NonNull String tag, @NonNull ViewPlacer placer) {
        mTag = tag;
        mPlacer = placer;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.setViewPlacer(mTag, mPlacer);
    }

    @Override
    void actReverse(PresenterManager manager) {
        // No action
    }

    @Override
    boolean isOpposite(TransactionStep other) {
        return false;
    }
}
