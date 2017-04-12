package com.dimedriller.multitool.purchases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.dimedriller.advancedmodel.result.ErrorType;
import com.dimedriller.multitool.R;
import com.dimedriller.multitoolmodel.purchases.Purchase;
import com.dimedriller.presenter.ViewInterface;

public class PurchasesPresenterViewInterface extends ViewInterface {
    private static final int PAGE_PROGRESS = 0;
    private static final int PAGE_FAILURE = 1;
    private static final int PAGE_LIST = 2;

    @Override
    protected @NonNull View onCreateView(@NonNull ViewGroup parentView) {
        Context context = parentView.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.purchases, parentView, false);
    }

    private @Nullable
    ViewAnimator showPage(int pageIndex) {
        View rootView = getRootView();
        if (rootView == null)
            return null;

        ViewAnimator animatorView = (ViewAnimator) rootView.findViewById(R.id.Animator);
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
