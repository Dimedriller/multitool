package com.dimedriller.presenter;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

class PendingPushTransactionAction implements PendingAction, Parcelable {
    public static final Creator<PendingPushTransactionAction> CREATOR
            = new Creator<PendingPushTransactionAction>() {
        @Override
        public PendingPushTransactionAction createFromParcel(Parcel source) {
            return new PendingPushTransactionAction(source);
        }

        @Override
        public PendingPushTransactionAction[] newArray(int size) {
            return new PendingPushTransactionAction[size];
        }
    };

    private final @NonNull String mStackName;
    private final @NonNull TransactionStepGroup mTransaction;

    public PendingPushTransactionAction(@NonNull String stackName, @NonNull TransactionStepGroup transaction) {
        mStackName = stackName;
        mTransaction = transaction;
    }

    PendingPushTransactionAction(Parcel parcel) {
        mStackName = parcel.readString();
        mTransaction = parcel.readParcelable(TransactionStepGroup.class.getClassLoader());
    }

    @Override
    public void act(PresenterManager manager) {
        manager.pushTransaction(mStackName, mTransaction);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStackName);
        dest.writeParcelable(mTransaction, flags);
    }
}
