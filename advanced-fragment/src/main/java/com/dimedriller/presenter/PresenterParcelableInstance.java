package com.dimedriller.presenter;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.dimedriller.advancedutils.utils.ParcelUtil;

public class PresenterParcelableInstance implements Parcelable {
    public static final Creator<PresenterParcelableInstance> CREATOR = new Creator<PresenterParcelableInstance>() {
        @Override
        public PresenterParcelableInstance createFromParcel(Parcel source) {
            return new PresenterParcelableInstance(source);
        }

        @Override
        public PresenterParcelableInstance[] newArray(int size) {
            return new PresenterParcelableInstance[size];
        }
    };

    private final @NonNull String mPresenterClassName;
    private final @NonNull Bundle mParams;
    private final @Nullable SparseArray<Parcelable> mViewState;

    PresenterParcelableInstance(@NonNull String presenterClassName,
            @NonNull Bundle params,
            @Nullable SparseArray<Parcelable> viewState) {
        mPresenterClassName = presenterClassName;
        mParams = params;
        mViewState = viewState;
    }

    PresenterParcelableInstance(Parcel source) {
        mPresenterClassName = source.readString();
        mParams = source.readBundle(getClass().getClassLoader());
        mViewState = ParcelUtil.readSparseArray(source, Parcelable.class);
    }

    Presenter restore(PresenterContainer container) {
        Class<Presenter> presenterClass;
        try {
            //noinspection unchecked
            presenterClass = (Class<Presenter>) Class.forName(mPresenterClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class " + mPresenterClassName + "\" does not exist", e);
        }

        Presenter presenter = PresenterBuilder.build(container, presenterClass, mParams);
        presenter.mViewInterface.setViewState(mViewState);
        return presenter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPresenterClassName);
        dest.writeBundle(mParams);
        ParcelUtil.writeSparseArray(dest, mViewState, flags);
    }
}
