package com.dimedriller.presenter;

import android.os.Parcel;
import android.support.annotation.NonNull;

class TransactionShowStep extends TransactionStep {
    public static final Creator<TransactionShowStep> CREATOR = new Creator<TransactionShowStep>() {
        @Override
        public TransactionShowStep createFromParcel(Parcel source) {
            return new TransactionShowStep(source);
        }

        @Override
        public TransactionShowStep[] newArray(int size) {
            return new TransactionShowStep[size];
        }
    };

    final @NonNull String mTag;

    TransactionShowStep(@NonNull String tag) {
        mTag = tag;
    }

    TransactionShowStep(Parcel source) {
        mTag = source.readString();
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.showPresenter(mTag);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.hidePresenter(mTag, false);
    }

    @Override
    boolean isOpposite(TransactionStep other) {
        if (!(other instanceof TransactionHideStep))
            return false;

        TransactionHideStep hideStep = (TransactionHideStep) other;
        return mTag.equals(hideStep.mTag);
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
