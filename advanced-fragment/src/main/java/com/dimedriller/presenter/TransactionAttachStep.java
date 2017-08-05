package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionAttachStep extends TransactionStep {
    private final @NonNull String mTag;
    private final @NonNull Presenter mPresenter;

    TransactionAttachStep(@NonNull String tag, @NonNull Presenter presenter) {
        mTag = tag;
        mPresenter = presenter;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.attachPresenter(mTag, mPresenter);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.detachPresenter(mTag);
    }

    @Override
    boolean isOpposite(TransactionStep other) {
        return false;
    }

    public @NonNull String getTag() {
        return mTag;
    }
}
