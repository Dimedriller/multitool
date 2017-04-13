package com.dimedriller.multitool;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.dimedriller.multitool.purchases.PurchasesPresenter;
import com.dimedriller.presenter.PresenterActivity;
import com.dimedriller.presenter.PresenterBuilder;
import com.dimedriller.presenter.ViewIDAnchor;

public class TestActivity extends PresenterActivity<TestViewInterface> {
    public TestActivity() {
        super(TestViewInterface.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
                getPresenterManager().newPushTransaction()
                        .addPresenter(new ViewIDAnchor(R.id.container), new PresenterBuilder<>(PurchasesPresenter.class))
                        .commit();},
                500);
    }
}
