package com.dimedriller.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PresenterBuilder<P extends Presenter> {
    private final Class<P> mPresenterClass;
    private final Bundle mParams = new Bundle();

    public PresenterBuilder(Class<P> presenterClass) {
        mPresenterClass = presenterClass;
    }

    public @NonNull String getTag() {
        return mPresenterClass.getName();
    }

    final P build(PresenterContainer container) {
        try {
            Constructor<P> constructor = mPresenterClass.getConstructor(PresenterContainer.class,
                    Bundle.class,
                    String.class);
            String tag = getTag();
            return constructor.newInstance(container, mParams, tag);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    "Constructor " + mPresenterClass.getName() + "(PresenterContainer, Bundle, String) not found",
                    e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(
                    "Constructor " + mPresenterClass.getName() + "(PresenterContainer, Bundle, String) not accessible",
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
}
