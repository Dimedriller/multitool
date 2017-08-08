package com.dimedriller.presenter;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.dimedriller.advancedutils.utils.ParcelUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PresenterBuilder<P extends Presenter> implements Parcelable {
    public static final Creator<PresenterBuilder> CREATOR = new Creator<PresenterBuilder>() {
        @Override
        public PresenterBuilder<? extends Presenter> createFromParcel(Parcel source) {
            return new PresenterBuilder<>(source);
        }

        @Override
        public PresenterBuilder[] newArray(int size) {
            return new PresenterBuilder[size];
        }
    };

    private final Class<P> mPresenterClass;
    private final Bundle mParams = new Bundle();

    private @Nullable SparseArray<Parcelable> mViewState;

    public PresenterBuilder(Class<P> presenterClass) {
        mPresenterClass = presenterClass;
    }

    PresenterBuilder(Parcel source) {
        String presenterClassName = source.readString();
        try {
            //noinspection unchecked
            mPresenterClass = (Class<P>) Class.forName(presenterClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class \"" + presenterClassName + "\" cannot be found", e);
        }

        Bundle params = source.readBundle(getClass().getClassLoader());
        mParams.putAll(params);

        mViewState = ParcelUtil.readSparseArray(source, Parcelable.class);
    }

    protected @NonNull String getTag() {
        return mPresenterClass.getName();
    }

    protected PresenterBuilder<P> setParam(String name, int value) {
        mParams.putInt(name, value);
        return this;
    }

    protected PresenterBuilder<P> setParam(String name, int[] value) {
        mParams.putIntArray(name, value);
        return this;
    }

    protected PresenterBuilder<P> setParam(String name, long value) {
        mParams.putLong(name, value);
        return this;
    }

    protected PresenterBuilder<P> setParam(String name, long[] value) {
        mParams.putLongArray(name, value);
        return this;
    }

    protected PresenterBuilder<P> setParam(String name, String value) {
        mParams.putString(name, value);
        return this;
    }

    protected PresenterBuilder<P> setParam(String name, String[] value) {
        mParams.putStringArray(name, value);
        return this;
    }

    protected PresenterBuilder<P> setParam(String name, Parcelable value) {
        mParams.putParcelable(name, value);
        return this;
    }

    protected PresenterBuilder<P> setParam(String name, Parcelable[] value) {
        mParams.putParcelableArray(name, value);
        return this;
    }

    PresenterBuilder<P> setParams(@NonNull Bundle params) {
        mParams.putAll(params);
        return this;
    }

    PresenterBuilder<P> setViewState(@Nullable SparseArray<Parcelable> viewState) {
        mViewState = viewState;
        return this;
    }

    final P build(PresenterContainer container) {
        String tag = getTag();
        Bundle params = new Bundle(mParams);
        params.putString(Presenter.PARAM_TAG, tag);

        try {
            Constructor<P> constructor = mPresenterClass.getConstructor(PresenterContainer.class, Bundle.class);
            P p = constructor.newInstance(container, params);
            p.mViewInterface.setViewState(mViewState);
            return p;
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    "Constructor " + mPresenterClass.getName() + "(PresenterContainer, Bundle) not found",
                    e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(
                    "Constructor " + mPresenterClass.getName() + "(PresenterContainer, Bundle) not accessible",
                    e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Class " + mPresenterClass.getName() + " instantiation not possible", e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(
                    "Constructor " + mPresenterClass.getName()
                            + "(PresenterContainer, Bundle, String) exception happened",
                    e);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPresenterClass.getName());
        dest.writeBundle(mParams);
        ParcelUtil.writeSparseArray(dest, mViewState, flags);
    }
}
