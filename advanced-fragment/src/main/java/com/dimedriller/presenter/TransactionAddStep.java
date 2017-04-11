package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionAddStep extends TransactionStep {
    private final @NonNull ViewAnchor mAnchor;
    private final @NonNull PresenterBuilder mPresenterBuilder;
    private final @NonNull String mTag;

    public TransactionAddStep(@NonNull ViewAnchor anchor,
            @NonNull PresenterBuilder presenterBuilder,
            @NonNull String tag) {
        mAnchor = anchor;
        mPresenterBuilder = presenterBuilder;
        mTag = tag;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.attachPresenter(mTag, mAnchor, mPresenterBuilder);
        manager.showPresenter(mTag);
        manager.resumePresenter(mTag);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.pausePresenter(mTag);
        manager.hidePresenter(mTag);
        manager.detachPresenter(mTag);
    }
}
