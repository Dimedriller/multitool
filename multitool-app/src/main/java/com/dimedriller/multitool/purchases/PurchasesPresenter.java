package com.dimedriller.multitool.purchases;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dimedriller.multitoolmodel.MultitoolModel;
import com.dimedriller.presenter.Presenter;
import com.dimedriller.presenter.PresenterContainer;

public class PurchasesPresenter extends Presenter<PurchasesPresenterViewInterface, MultitoolModel> {
    public PurchasesPresenter(@NonNull PresenterContainer container,
            @NonNull Bundle params,
            @NonNull String tag) {
        super(PurchasesPresenterViewInterface.class, container, params, tag);
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();

        mModel.getPurchaseManager().fetchPurchases(System.currentTimeMillis(),
                mViewInterface::showProgress,
                mViewInterface::showPurchases,
                mViewInterface::showFailure);
    }
}
