package com.dimedriller.presenter;

import android.os.Parcel;
import android.support.annotation.NonNull;

class TransactionPauseStep extends TransactionStep {
    public static final Creator<TransactionPauseStep> CREATOR = new Creator<TransactionPauseStep>() {
        @Override
        public TransactionPauseStep createFromParcel(Parcel source) {
            return new TransactionPauseStep(source);
        }

        @Override
        public TransactionPauseStep[] newArray(int size) {
            return new TransactionPauseStep[size];
        }
    };

    final @NonNull String mTag;

    TransactionPauseStep(@NonNull String tag) {
        mTag = tag;
    }

    TransactionPauseStep(Parcel source) {
        mTag = source.readString();
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.pausePresenter(mTag);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.resumePresenter(mTag);
    }

    @Override
    boolean isOpposite(TransactionStep other) {
        if (!(other instanceof TransactionResumeStep))
            return false;

        TransactionResumeStep resumeStep = (TransactionResumeStep) other;
        return mTag.equals(resumeStep.mTag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTag);
    }
}
