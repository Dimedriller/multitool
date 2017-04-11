package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionRemoveStep extends TransactionStep {
    private final @NonNull String mTag;

    TransactionRemoveStep(@NonNull String tag) {
        mTag = tag;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.pausePresenter(mTag);
        manager.hidePresenter(mTag);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.showPresenter(mTag);
        manager.resumePresenter(mTag);
    }
}
