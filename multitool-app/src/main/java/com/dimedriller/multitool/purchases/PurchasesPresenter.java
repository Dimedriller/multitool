package com.dimedriller.multitool.purchases;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dimedriller.multitoolmodel.MultitoolModel;
import com.dimedriller.presenter.Presenter;
import com.dimedriller.presenter.PresenterContainer;

public class PurchasesPresenter extends Presenter<PurchasesPresenterViewInterface, MultitoolModel> {
    public PurchasesPresenter(@NonNull PresenterContainer container, @NonNull Bundle params) {
        super(PurchasesPresenterViewInterface.class, container, params);
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
