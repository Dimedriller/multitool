package com.dimedriller.multitool;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.dimedriller.multitool.purchases.PurchasesPresenter;
import com.dimedriller.presenter.PresenterActivity;
import com.dimedriller.presenter.PresenterBuilder;

public class TestActivity extends PresenterActivity<TestViewInterface> {
    public TestActivity() {
        super(TestViewInterface.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
                    getPresenterManager().newPushTransaction()
                            .addPresenter(new PresenterBuilder<>(PurchasesPresenter.class))
                            .addPresenter(new PresenterBuilder<>(TestPresenter.class))
                            .commit();},
                500);
    }
}
