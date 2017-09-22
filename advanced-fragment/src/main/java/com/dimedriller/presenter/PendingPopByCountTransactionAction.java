package com.dimedriller.presenter;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

class PendingPopByCountTransactionAction implements PendingAction, Parcelable {
    public static final Creator<PendingPopByCountTransactionAction> CREATOR
            = new Creator<PendingPopByCountTransactionAction>() {
        @Override
        public PendingPopByCountTransactionAction createFromParcel(Parcel source) {
            return new PendingPopByCountTransactionAction(source);
        }

        @Override
        public PendingPopByCountTransactionAction[] newArray(int size) {
            return new PendingPopByCountTransactionAction[size];
        }
    };

    private final @Nullable String mStackName;
    private final int mTransactionCount;

    PendingPopByCountTransactionAction(@Nullable String stackName, int transactionCount) {
        mStackName = stackName;
        mTransactionCount = transactionCount;
    }

    PendingPopByCountTransactionAction(Parcel parcel) {
        mStackName = parcel.readString();
        mTransactionCount = parcel.readInt();
    }

    @Override
    public void act(PresenterManager manager) {
        manager.popTransaction(mStackName, mTransactionCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStackName);
        dest.writeInt(mTransactionCount);
    }
}
