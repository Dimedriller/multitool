package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionAttachStep extends TransactionStep {
    private final @NonNull String mTag;
    private final @NonNull PresenterBuilder mPresenterBuilder;
    private final @NonNull ViewLocator mLocator;
    private final @NonNull ViewPlacer mPlacer;

    public TransactionAttachStep(@NonNull String tag,
            @NonNull PresenterBuilder presenterBuilder,
            @NonNull ViewLocator locator,
            @NonNull ViewPlacer placer) {
        mTag = tag;
        mPresenterBuilder = presenterBuilder;
        mLocator = locator;
        mPlacer = placer;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.attachPresenter(mTag, mPresenterBuilder, mLocator, mPlacer);
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
