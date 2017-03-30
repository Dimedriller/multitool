package com.dimedriller.multitoolmodel;

import android.app.Application;

import com.dimedriller.advancedmodel.Model;

public class MultitoolApplication extends Application implements Model.Container<MultitoolModel> {
    private final MultitoolModel mModel = new MultitoolModel();

    @Override
    public void onCreate() {
        super.onCreate();

        mModel.startServices(this);
    }

    @Override
    public void onTerminate() {
        mModel.stopServices(this);

        super.onTerminate();
    }

    @Override
    public MultitoolModel getModel() {
        return mModel;
    }
}
