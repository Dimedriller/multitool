package com.dimedriller.multitool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dimedriller.presenter.ContainerViewInterface;

public class TestViewInterface extends ContainerViewInterface {
    @NonNull
    @Override
    protected ViewGroup onCreateView(@NonNull ViewGroup parentView) {
        Context context = parentView.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return (ViewGroup) inflater.inflate(R.layout.activity_test, parentView, false);
    }
}
