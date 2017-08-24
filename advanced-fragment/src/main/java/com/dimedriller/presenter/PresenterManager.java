package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dimedriller.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PresenterManager {
    private final @NonNull PresenterContainer mPresenterContainer;

    private final Map<String, Presenter> mPresenterMap = new HashMap<>();
    private final Map<String, List<TransactionStepGroup>> mTransactionStackMap = new HashMap<>();

    public PresenterManager(@NonNull PresenterContainer presenterContainer) {
        mPresenterContainer = presenterContainer;
    }

    @NonNull
    PresenterManagerInstanceState saveInstanceState() {
        HashMap<String, List<TransactionStepGroup>> transactionStackMap = new HashMap<>(mTransactionStackMap);
        return new PresenterManagerInstanceState(transactionStackMap);
    }

    void restoreInstanceState(@Nullable PresenterManagerInstanceState state) {
        if (state == null)
            return;

        Map<String, List<TransactionStepGroup>> transactionStackMap = state.getTransactionStackMap();
        mTransactionStackMap.putAll(transactionStackMap);

        Set<Map.Entry<String, List<TransactionStepGroup>>> transactionEntrySet = transactionStackMap.entrySet();
        for(Map.Entry<String, List<TransactionStepGroup>> entry : transactionEntrySet) {
            List<TransactionStepGroup> transactionStack = entry.getValue();
            int transactionStackSize = transactionStack.size();
            if (transactionStackSize == 0)
                continue;

            TransactionStepGroup mergedGroup = transactionStack.get(transactionStackSize - 1);
            for(int indexTransaction = transactionStackSize - 2; indexTransaction >= 0; indexTransaction--) {
                TransactionStepGroup group = transactionStack.get(indexTransaction);
                mergedGroup = mergedGroup.mergeForward(group);
            }
            mergedGroup.actDirect(this);
        }
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

    Presenter attachPresenter(@NonNull String tag, @NonNull PresenterBuilder presenterBuilder) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter != null) {
            Log.w("Presenter \"" + tag + "\" already exists.");
            return null;
        }

        presenter = presenterBuilder.build(mPresenterContainer);
        mPresenterMap.put(tag, presenter);
        presenter.create();
        return presenter;
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

    void setViewPlacer(@NonNull String tag, @NonNull ViewPlacer viewPlacer) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        presenter.setViewPlacer(viewPlacer);
    }

    void showPresenter(@NonNull String tag) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
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

        presenter.destroyView(isViewStateSaved);
    }

    void resumePresenter(@NonNull String tag) {
        Presenter presenter = mPresenterMap.get(tag);
        if (presenter == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
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
            mergedGroup = mergedGroup.mergeBack(popGroup);
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
            mergedPopGroup = mergedPopGroup.mergeBack(popGroup);
            popGroup = transactionStack.remove(0);
        }
        if (!popGroup.containsAttachStep(toTagName))
            mergedPopGroup.mergeBack(popGroup);

        mergedPopGroup.actReverse(this);
    }

    void resume() {
        // TODO
    }

    void pause() {
        // TODO
    }

    void destroy() {
        // TODO
    }
}
