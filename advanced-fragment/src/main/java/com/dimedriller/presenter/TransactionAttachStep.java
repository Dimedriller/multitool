package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionAttachStep extends TransactionStep {
    private final @NonNull ViewLocator mAnchor;
    private final @NonNull ViewPlacer mPlacer;
    private final @NonNull PresenterBuilder mPresenterBuilder;
    private final @NonNull String mTag;

    public TransactionAttachStep(@NonNull String tag,
            @NonNull ViewLocator anchor,
            @NonNull ViewPlacer placer,
            @NonNull PresenterBuilder presenterBuilder) {
        mAnchor = anchor;
        mPlacer = placer;
        mPresenterBuilder = presenterBuilder;
        mTag = tag;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.attachPresenter(mTag, mAnchor, mPlacer, mPresenterBuilder);
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
