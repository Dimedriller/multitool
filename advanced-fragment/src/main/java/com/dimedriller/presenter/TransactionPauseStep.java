package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionPauseStep extends TransactionStep {
    private final @NonNull String mTag;

    TransactionPauseStep(@NonNull String tag) {
        mTag = tag;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.pausePresenter(mTag);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.showPresenter(mTag);
    }
}
