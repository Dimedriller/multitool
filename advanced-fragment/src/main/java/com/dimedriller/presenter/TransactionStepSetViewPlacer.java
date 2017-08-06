package com.dimedriller.presenter;

import android.os.Parcel;
import android.support.annotation.NonNull;

class TransactionStepSetViewPlacer extends TransactionStep {
    public static final Creator<TransactionStepSetViewPlacer> CREATOR = new Creator<TransactionStepSetViewPlacer>() {
        @Override
        public TransactionStepSetViewPlacer createFromParcel(Parcel source) {
            return new TransactionStepSetViewPlacer(source);
        }

        @Override
        public TransactionStepSetViewPlacer[] newArray(int size) {
            return new TransactionStepSetViewPlacer[size];
        }
    };

    private final @NonNull String mTag;
    private final @NonNull ViewPlacer mViewPlacer;

    public TransactionStepSetViewPlacer(@NonNull String tag, @NonNull ViewPlacer viewPlacer) {
        mTag = tag;
        mViewPlacer = viewPlacer;
    }

    TransactionStepSetViewPlacer(Parcel source) {
        mTag = source.readString();
        mViewPlacer = source.readParcelable(ViewPlacer.class.getClassLoader());
    }

    @Override
    void actDirect(PresenterManager manager) {
        manager.setViewPlacer(mTag, mViewPlacer);
    }

    @Override
    void actReverse(PresenterManager manager) {
        // No action
    }

    @Override
    boolean isOpposite(TransactionStep other) {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTag);
        dest.writeParcelable(mViewPlacer, flags);
    }
}
