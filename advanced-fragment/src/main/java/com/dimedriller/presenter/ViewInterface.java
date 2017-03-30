package com.dimedriller.presenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class ViewInterface {
    public abstract View createView(ViewGroup parentView);

    static <V extends ViewInterface> V createViewInterface(Class<V> viClass) {
        try {
            Constructor<V> viConstructor = viClass.getConstructor();
            return viConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Default constructor not found: " + viClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Default constructor not accessible: " + viClass.getName(), e);
        } catch (java.lang.InstantiationException e) {
            throw new IllegalArgumentException("Class instantiation not possible: " + viClass.getName() + " class", e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Constructor exception happened: " + viClass.getName(), e);
        }
    }
}
