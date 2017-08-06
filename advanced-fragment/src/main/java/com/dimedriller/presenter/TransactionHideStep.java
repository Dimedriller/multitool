package com.dimedriller.presenter;

import android.os.Parcel;
import android.support.annotation.NonNull;

class TransactionHideStep extends TransactionStep {
    public static final Creator<TransactionHideStep> CREATOR = new Creator<TransactionHideStep>() {
        @Override
        public TransactionHideStep createFromParcel(Parcel source) {
            return new TransactionHideStep(source);
        }

        @Override
        public TransactionHideStep[] newArray(int size) {
            return new TransactionHideStep[size];
        }
    };

    final @NonNull String mTag;

    TransactionHideStep(@NonNull String tag) {
        mTag = tag;
    }

    public TransactionHideStep(Parcel source) {
        mTag = source.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTag);
    }
}
