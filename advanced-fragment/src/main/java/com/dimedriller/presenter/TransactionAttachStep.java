package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionAttachStep extends TransactionStep {
    private final @NonNull ViewAnchor mAnchor;
    private final @NonNull PresenterBuilder mPresenterBuilder;
    private final @NonNull String mTag;

    TransactionAttachStep(@NonNull String tag,
            @NonNull ViewAnchor anchor,
            @NonNull PresenterBuilder presenterBuilder) {
        mAnchor = anchor;
        mPresenterBuilder = presenterBuilder;
        mTag = tag;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.attachPresenter(mTag, mAnchor, mPresenterBuilder);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.detachPresenter(mTag);
    }
}
