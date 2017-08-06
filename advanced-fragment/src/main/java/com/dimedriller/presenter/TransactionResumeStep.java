package com.dimedriller.presenter;

import android.os.Parcel;
import android.support.annotation.NonNull;

class TransactionResumeStep extends TransactionStep {
    public static final Creator<TransactionResumeStep> CREATOR = new Creator<TransactionResumeStep>() {
        @Override
        public TransactionResumeStep createFromParcel(Parcel source) {
            return new TransactionResumeStep(source);
        }

        @Override
        public TransactionResumeStep[] newArray(int size) {
            return new TransactionResumeStep[size];
        }
    };

    final @NonNull String mTag;

    TransactionResumeStep(@NonNull String tag) {
        mTag = tag;
    }

    TransactionResumeStep(Parcel source) {
        mTag = source.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTag);
    }
}
