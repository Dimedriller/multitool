package com.dimedriller.multitool.purchases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dimedriller.multitool.MultitoolFragment;

public class PurchasesFragment extends MultitoolFragment<PurchasesViewInterface> {
    public PurchasesFragment() {
        super(PurchasesViewInterface.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mModel.getPurchaseManager().fetchPurchases(System.currentTimeMillis(),
                mViewInt::showProgress,
                mViewInt::showPurchases,
                mViewInt::showFailure);
    }
}
