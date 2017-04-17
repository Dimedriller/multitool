package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionHideStep extends TransactionStep {
    private final @NonNull String mTag;

    TransactionHideStep(@NonNull String tag) {
        mTag = tag;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.hidePresenter(mTag);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.resumePresenter(mTag);
    }
}
