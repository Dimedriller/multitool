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

    static<P extends  Presenter> P build(PresenterContainer container, Class<P> presenterClass, Bundle params) {
        try {
            Constructor<P> constructor = presenterClass.getConstructor(PresenterContainer.class, Bundle.class);
            return constructor.newInstance(container, params);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                    "Constructor " + presenterClass.getName() + "(PresenterContainer, Bundle) not found",
                    e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(
                    "Constructor " + presenterClass.getName() + "(PresenterContainer, Bundle) not accessible",
                    e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Class " + presenterClass.getName() + " instantiation not possible", e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(
                    "Constructor " + presenterClass.getName()
                            + "(PresenterContainer, Bundle, String) exception happened",
                    e);
        }
    }

    final P build(PresenterContainer container) {
        String tag = getTag();
        Bundle params = new Bundle(mParams);
        params.putString(Presenter.PARAM_TAG, tag);
        return build(container, mPresenterClass, params);
    }
}
