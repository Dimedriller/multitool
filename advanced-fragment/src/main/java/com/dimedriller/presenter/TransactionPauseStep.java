package com.dimedriller.presenter;

import android.support.annotation.NonNull;

class TransactionPauseStep extends TransactionStep {
    final @NonNull String mTag;

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

    @Override
    boolean isOpposite(TransactionStep other) {
        if (!(other instanceof TransactionResumeStep))
            return false;

        TransactionResumeStep resumeStep = (TransactionResumeStep) other;
        return mTag.equals(resumeStep.mTag);
    }
}
