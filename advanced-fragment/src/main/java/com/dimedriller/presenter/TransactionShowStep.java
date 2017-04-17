package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionShowStep extends TransactionStep {
    private final @NonNull String mTag;

    public TransactionShowStep(@NonNull String tag) {
        mTag = tag;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.showPresenter(mTag);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.hidePresenter(mTag);
    }
}
