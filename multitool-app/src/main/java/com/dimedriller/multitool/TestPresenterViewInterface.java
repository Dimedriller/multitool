package com.dimedriller.multitool;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimedriller.presenter.ViewInterface;

public class TestPresenterViewInterface extends ViewInterface {
    @NonNull
    @Override
    protected View onCreateView(@NonNull ViewGroup parentView) {
        LayoutInflater inflater = LayoutInflater.from(parentView.getContext());
        View view = inflater.inflate(R.layout.test, parentView, false);
        view.setBackgroundColor(0xFFFF00FF);
        return view;
    }
}
