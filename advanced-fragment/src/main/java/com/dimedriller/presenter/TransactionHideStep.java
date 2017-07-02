package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionHideStep extends TransactionStep {
    final @NonNull String mTag;

    TransactionHideStep(@NonNull String tag) {
        mTag = tag;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.hidePresenter(mTag, true);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.showPresenter(mTag);
    }

    @Override
    boolean isOpposite(TransactionStep other) {
        if (!(other instanceof TransactionShowStep))
            return false;

        TransactionShowStep showStep = (TransactionShowStep) other;
        return mTag.equals(showStep.mTag);
    }
}
