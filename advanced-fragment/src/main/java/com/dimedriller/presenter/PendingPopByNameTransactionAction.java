package com.dimedriller.presenter;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class PendingPopByNameTransactionAction implements PendingAction, Parcelable {
    public static final Creator<PendingPopByNameTransactionAction> CREATOR
            = new Creator<PendingPopByNameTransactionAction>() {
        @Override
        public PendingPopByNameTransactionAction createFromParcel(Parcel source) {
            return new PendingPopByNameTransactionAction(source);
        }

        @Override
        public PendingPopByNameTransactionAction[] newArray(int size) {
            return new PendingPopByNameTransactionAction[size];
        }
    };

    private final @Nullable String mStackName;
    private final @NonNull String mTag;

    public PendingPopByNameTransactionAction(@Nullable String stackName, @NonNull String tag) {
        mStackName = stackName;
        mTag = tag;
    }

    PendingPopByNameTransactionAction(Parcel parcel) {
        mStackName = parcel.readString();
        mTag = parcel.readString();
    }

    @Override
    public void act(PresenterManager manager) {
        manager.popTransaction(mStackName, mTag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStackName);
        dest.writeString(mTag);
    }
}
