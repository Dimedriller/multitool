package com.dimedriller.advancedfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimedriller.advancedmodel.Model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class BaseFragment<VI extends ViewInterface, M> extends Fragment {
    protected final VI mViewInt;

    protected M mModel;

    protected BaseFragment(VI viewInt) {
        mViewInt = viewInt;
    }

    protected BaseFragment(Class<VI> viewIntClass) {
        mViewInt = createViewInterface(viewIntClass);
    }

    private VI createViewInterface(Class<VI> viClass) {
        try {
            Constructor<VI> viConstructor = viClass.getConstructor();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //noinspection unchecked
        Model.Container<M> modelContainer = (Model.Container<M>) context.getApplicationContext();
        mModel = modelContainer.getModel();
    }

    @Override
    public void onDetach() {
        mModel = null;

        super.onDetach();
    }

    @Override
    public @Nullable View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return mViewInt.createView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mViewInt.destroyView();

        super.onDestroyView();
    }

    public boolean canGoBack() {
        return false;
    }

    public  boolean onBackPressed() {
        return false;
    }
}
