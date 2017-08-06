package com.dimedriller.presenter;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class TransactionAttachStep extends TransactionStep {
    public static final Creator<TransactionAttachStep> CREATOR = new Creator<TransactionAttachStep>() {
        @Override
        public TransactionAttachStep createFromParcel(Parcel source) {
            return new TransactionAttachStep(source);
        }

        @Override
        public TransactionAttachStep[] newArray(int size) {
            return new TransactionAttachStep[size];
        }
    };

    private final @NonNull String mTag;
    private final @NonNull PresenterBuilder mPresenterBuilder;

    private @Nullable Presenter mPresenter;

    TransactionAttachStep(@NonNull String tag, @NonNull PresenterBuilder presenterBuilder) {
        mTag = tag;
        mPresenterBuilder = presenterBuilder;
    }

    TransactionAttachStep(Parcel source) {
        mTag = source.readString();
        mPresenterBuilder = source.readParcelable(PresenterBuilder.class.getClassLoader());
    }

    @Override
    void actDirect(PresenterManager manager) {
        mPresenter = manager.attachPresenter(mTag, mPresenterBuilder);
    }

    @Override
    void actReverse(PresenterManager manager) {
        manager.detachPresenter(mTag);
    }

    @Override
    boolean isOpposite(TransactionStep other) {
        return false;
    }

    public @NonNull String getTag() {
        return mTag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTag);

        if (mPresenter == null)
            dest.writeParcelable(mPresenterBuilder, flags);
        else {
            PresenterBuilder presenterBuilder = mPresenter.toBuilder();
            dest.writeParcelable(presenterBuilder, flags);
        }
    }
}
