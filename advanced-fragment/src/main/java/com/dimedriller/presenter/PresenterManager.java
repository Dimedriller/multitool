package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dimedriller.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresenterManager {
    private final @NonNull PresenterContainer mPresenterContainer;

    private final Map<String, Presenter> mPresenterMap = new HashMap<>();
    private final Map<String, List<TransactionStepGroup>> mTransactionStackMap = new HashMap<>();

    public PresenterManager(@NonNull PresenterContainer presenterContainer) {
        mPresenterContainer = presenterContainer;
    }

    public PushTransaction newPushTransaction(@Nullable String stackName) {
        return new PushTransaction(this, stackName);
    }

    public PushTransaction newPushTransaction() {
        return newPushTransaction(null);
    }

    public PopTransaction newPopTransaction(String stackName) {
        return new PopTransaction(this, stackName);
    }

    public PopTransaction newPopTransaction() {
        return newPopTransaction(null);
    }

    void attachPresenter(@NonNull String tag,
            @NonNull PresenterBuilder presenterBuilder,
            @NonNull ViewLocator locator,
            @NonNull ViewPlacer placer) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter != null) {
            Log.w("Presenter \"" + tag + "\" already exists.");
            return;
        }

        presenter = presenterBuilder.build(mPresenterContainer);
        presenter.setLocator(locator);
        presenter.setPlacer(placer);
        mPresenterMap.put(tag, presenter);
        presenter.create();
    }

    void detachPresenter(@NonNull String tag) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        presenter.destroy();
        mPresenterMap.remove(tag);
    }

    void showPresenter(@NonNull String tag) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        if (presenter.hasView()) {
            Log.w("Presenter \"" + tag + "\" was already shown");
            return;
        }

        presenter.createView();
    }

    void hidePresenter(@NonNull String tag, boolean isViewStateSaved) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        if (!presenter.hasView()) {
            Log.w("Presenter \"" + tag + "\" was already hidden");
            return;
        }

        presenter.destroyView(isViewStateSaved);
    }

    void resumePresenter(@NonNull String tag) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        if (presenter.getState() == PresenterState.RESUMED) {
            Log.w("Presenter \"" + tag + "\" was already resumed.");
            return;
        }

        presenter.resume();
    }

    void pausePresenter(@NonNull String tag) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        if (presenter.getState() == PresenterState.PAUSED) {
            Log.w("Presenter \"" + tag + "\" was already paused.");
            return;
        }

        presenter.pause();
    }

    void pushTransaction(String stackName, TransactionStepGroup transaction) {
        List<TransactionStepGroup> transactionStack = mTransactionStackMap.get(stackName);
        if (transactionStack == null) {
            transactionStack = new ArrayList<>();
            mTransactionStackMap.put(stackName, transactionStack);
        }

        transactionStack.add(0, transaction);
    }

    void popTransaction(@Nullable String stackName, int transactionCount) {
        List<TransactionStepGroup> transactionStack = mTransactionStackMap.get(stackName);
        if (transactionStack == null) {
            Log.w("Pop with null \"" + stackName + "\" stack was ignored.");
            return;
        }

        int stackTransactionCount = transactionStack.size();

        if (transactionCount <= 0 || transactionCount > stackTransactionCount)
            transactionCount = stackTransactionCount;

        if (transactionCount == 0) {
            Log.w("Pop with empty \"" + stackName + "\" stack was ignored.");
            return;
        }

        TransactionStepGroup mergedGroup = transactionStack.remove(0);
        for(int indexTransaction = 1; indexTransaction < transactionCount; indexTransaction++) {
            TransactionStepGroup popGroup = transactionStack.remove(0);
            mergedGroup = mergedGroup.merge(popGroup);
        }

        mergedGroup.actReverse(this);
    }

    void popTransaction(@Nullable String stackName, String toTagName) {
        List<TransactionStepGroup> transactionStack = mTransactionStackMap.get(stackName);
        if (transactionStack == null) {
            Log.w("Pop with null \"" + stackName + "\" stack was ignored.");
            return;
        }

        int stackTransactionCount = transactionStack.size();
        if (stackTransactionCount == 0) {
            Log.w("Pop with empty \"" + stackName + "\" stack was ignored.");
            return;
        }

        TransactionStepGroup popGroup = transactionStack.remove(0);
        TransactionStepGroup mergedPopGroup = new TransactionStepGroup(new ArrayList<TransactionStep>());
        for(int indexTransaction = 1;
                indexTransaction < stackTransactionCount && !popGroup.containsAttachStep(toTagName);
                indexTransaction++) {
            mergedPopGroup = mergedPopGroup.merge(popGroup);
            popGroup = transactionStack.remove(0);
        }
        if (!popGroup.containsAttachStep(toTagName))
            mergedPopGroup.merge(popGroup);

        mergedPopGroup.actReverse(this);
    }
}
