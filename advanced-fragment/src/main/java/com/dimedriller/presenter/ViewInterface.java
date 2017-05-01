package com.dimedriller.presenter;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.dimedriller.log.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class ViewInterface {
    private @Nullable View mRootView;
    private @Nullable SparseArray<Parcelable> mViewState;

    protected abstract @NonNull View onCreateView(@NonNull ViewGroup parentView);

    final void createView(@NonNull ViewGroup parentView, ViewPlacer placer) {
        if (mRootView != null)
            throw new IllegalStateException(
                    "createView cannot be called when previous presenter view has not been destroyed");

        View view = onCreateView(parentView);
        placer.attachView(parentView, view);

        mRootView = view;
    }

    protected void onDestroyView(@NonNull ViewGroup parentView) {
        // No action
    }

    final void destroyView(@NonNull ViewGroup parentView, ViewPlacer placer) {
        if (mRootView == null)
            return;

        onDestroyView(parentView);
        placer.detachView(parentView, mRootView);
        mRootView = null;
    }

    final void saveViewState() {
        if (mRootView == null) {
            Log.w("Attempt to store view state with no view");
            return;
        }

        SparseArray<Parcelable> viewState = new SparseArray<>();
        mRootView.saveHierarchyState(viewState);
        mViewState = viewState;
    }

    final void restoreViewState() {
        if (mRootView == null) {
            Log.w("Attempt to restore view state with no view");
            return;
        }

        if (mViewState == null)
            return;
        mRootView.restoreHierarchyState(mViewState);
        mViewState = null;
    }

    protected @Nullable View getRootView() {
        return mRootView;
    }

    boolean hasView() {
        return mRootView != null;
    }

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
