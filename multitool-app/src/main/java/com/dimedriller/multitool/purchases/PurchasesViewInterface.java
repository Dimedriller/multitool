package com.dimedriller.multitool.purchases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.dimedriller.advancedfragment.ViewInterface;
import com.dimedriller.advancedmodel.result.ErrorType;
import com.dimedriller.multitool.R;
import com.dimedriller.multitoolmodel.purchases.Purchase;

public class PurchasesViewInterface extends ViewInterface {
    private static final int PAGE_PROGRESS = 0;
    private static final int PAGE_FAILURE = 1;
    private static final int PAGE_LIST = 2;

    @Override
    protected View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.purchases, container, false);
    }

    private @Nullable ViewAnimator showPage(int pageIndex) {
        if (mRootView == null)
            return null;

        ViewAnimator animatorView = (ViewAnimator) mRootView.findViewById(R.id.Animator);
        animatorView.setDisplayedChild(pageIndex);
        return animatorView;
    }

    void showProgress() {
        showPage(PAGE_PROGRESS);
    }

    void showFailure(ErrorType errorType) {
        ViewAnimator animatorView = showPage(PAGE_FAILURE);
        if (animatorView == null)
            return;

        TextView failureMessageView = (TextView) animatorView.findViewById(R.id.FailureMessage);
        failureMessageView.setText(errorType.name());
    }

    void showPurchases(Purchase[] purchases) {
        ViewAnimator animatorView = showPage(PAGE_LIST);
        if (animatorView == null)
            return;

        ListView listView = (ListView) animatorView.findViewById(R.id.List);
        listView.setAdapter(new PurchasesAdapter(purchases));
    }
}
