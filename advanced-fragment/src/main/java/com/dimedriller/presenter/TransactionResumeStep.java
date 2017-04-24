package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionResumeStep extends TransactionStep {
    final @NonNull String mTag;

    TransactionResumeStep(@NonNull String tag) {
        mTag = tag;
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.resumePresenter(mTag);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.pausePresenter(mTag);
    }

    @Override
    boolean isOpposite(TransactionStep other) {
        if (!(other instanceof TransactionPauseStep))
            return false;

        TransactionPauseStep pauseStep = (TransactionPauseStep) other;
        return mTag.equals(pauseStep.mTag);
    }
}
