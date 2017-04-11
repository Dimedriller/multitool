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

    private final Map<String, PresenterRecord> mPresenterMap = new HashMap<>();
    private final Map<String, List<Transaction>> mTransactionStackMap = new HashMap<>();

    public PresenterManager(@NonNull PresenterContainer presenterContainer) {
        mPresenterContainer = presenterContainer;
    }

    void attachPresenter(@NonNull String tag, @NonNull ViewAnchor anchor, @NonNull PresenterBuilder presenterBuilder) {
        PresenterRecord presenterRecord = mPresenterMap.get(tag);
        if (presenterRecord != null) {
            Log.w("Presenter \"" + tag + "\" already exists.");
            return;
        }

        Presenter presenter = presenterBuilder.build(mPresenterContainer);
        presenterRecord = new PresenterRecord(presenter, anchor);
        mPresenterMap.put(tag, presenterRecord);
    }

    void detachPresenter(@NonNull String tag) {
        PresenterRecord presenterRecord = mPresenterMap.get(tag);
        if (presenterRecord == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        mPresenterMap.remove(tag);
        Presenter presenter = presenterRecord.mPresenter;
        presenter.finish();
    }

    void showPresenter(@NonNull String tag) {
        PresenterRecord presenterRecord = mPresenterMap.get(tag);
        if (presenterRecord == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        Presenter presenter = presenterRecord.mPresenter;
        if (presenter.hasView()) {
            Log.w("Presenter \"" + tag + "\" was already shown");
            return;
        }

        ViewAnchor anchor = presenterRecord.mAnchor;
        presenter.createView(anchor);
    }

    void hidePresenter(@NonNull String tag) {
        PresenterRecord presenterRecord = mPresenterMap.get(tag);
        if (presenterRecord == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        Presenter presenter = presenterRecord.mPresenter;
        if (!presenter.hasView()) {
            Log.w("Presenter \"" + tag + "\" was already hidden");
            return;
        }

        ViewAnchor anchor = presenterRecord.mAnchor;
        presenter.destroyView(anchor);
    }

    void resumePresenter(@NonNull String tag) {
        PresenterRecord presenterRecord = mPresenterMap.get(tag);
        if (presenterRecord == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        Presenter presenter = presenterRecord.mPresenter;
        if (presenter.getState() == PresenterState.RESUMED) {
            Log.w("Presenter \"" + tag + "\" was already resumed.");
            return;
        }

        presenter.resume();
    }

    void pausePresenter(@NonNull String tag) {
        PresenterRecord presenterRecord = mPresenterMap.get(tag);
        if (presenterRecord == null) {
            Log.w("Presenter \"" + tag + "\" does not exist.");
            return;
        }

        Presenter presenter = presenterRecord.mPresenter;
        if (presenter.getState() == PresenterState.PAUSED) {
            Log.w("Presenter \"" + tag + "\" was already paused.");
            return;
        }

        presenter.pause();
    }

    void pushTransaction(String stackName, Transaction transaction) {
        List<Transaction> transactionStack = mTransactionStackMap.get(stackName);
        if (transactionStack == null) {
            transactionStack = new ArrayList<>();
            mTransactionStackMap.put(stackName, transactionStack);
        }

        transactionStack.add(0, transaction);
    }

    void popTransaction(@Nullable String stackName) {

    }
}