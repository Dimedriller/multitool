package com.dimedriller.presenter;

import android.content.Context;
import android.os.Bundle;

public class PresenterBuilder<P extends Presenter> {
    private final Class<P> mPresenterClass;
    private final Bundle mParams = new Bundle();

    public PresenterBuilder(Class<P> presenterClass) {
        mPresenterClass = presenterClass;
    }

    final P build(PresenterContainer container) {


    }
}
