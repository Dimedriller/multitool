package com.dimedriller.presenter;

import java.util.ArrayList;
import java.util.List;

class TransactionStepGroup {
    private final TransactionStep[] mSteps;

    TransactionStepGroup(List<TransactionStep> stepList) {
        mSteps = new TransactionStep[stepList.size()];
        stepList.toArray(mSteps);
    }

    void actDirect(PresenterManager manager) {
        int countStep = mSteps.length;
        //noinspection ForLoopReplaceableByForEach
        for(int indexStep = 0; indexStep < countStep; indexStep++) {
            TransactionStep step = mSteps[indexStep];
            step.actDirect(manager);
        }
    }

    void actReverse(PresenterManager manager) {
        for(int indexStep = mSteps.length - 1; indexStep >=0; indexStep--) {
            TransactionStep step = mSteps[indexStep];
            step.actReverse(manager);
        }
    }

    private static int findOppositeStepIndex(List<TransactionStep> stepList, TransactionStep lookUpStep) {
        int countStep = stepList.size();
        for(int indexStep = 0; indexStep < countStep; indexStep++) {
            TransactionStep step = stepList.get(indexStep);
            if (lookUpStep.isOpposite(step))
                return indexStep;
        }
        return -1;
    }

    /**
     * Merges steps with another {@code TransactionStepGroup} and returns new {@code TransactionStepGroup}.
     *
     * @param otherGroup - other group to merge
     *
     * Note: Order of steps is significantly important so {@code for} with index loop is used
     */
    TransactionStepGroup merge(TransactionStepGroup otherGroup) {
        List<TransactionStep> stepList = new ArrayList<>();

        TransactionStep[] otherSteps = otherGroup.mSteps;
        int countOtherStep = otherSteps.length;
        List<TransactionStep> mergedStepList = new ArrayList<>(countOtherStep);
        //noinspection ManualArrayToCollectionCopy,ForLoopReplaceableByForEach
        for(int indexStep = 0; indexStep < countOtherStep; indexStep++) {
            TransactionStep otherStep = otherSteps[indexStep];
            mergedStepList.add(otherStep);
        }

        int countStep = mSteps.length;
        //noinspection ForLoopReplaceableByForEach
        for(int indexStep = 0; indexStep < countStep; indexStep++) {
            TransactionStep step = mSteps[indexStep];
            int indexOppositeStep = findOppositeStepIndex(mergedStepList, step);
            if (indexOppositeStep == -1)
                stepList.add(step);
            else
                mergedStepList.remove(indexOppositeStep);
        }

        countStep = stepList.size();
        for(int indexStep = 0; indexStep < countStep; indexStep++) {
            TransactionStep step = stepList.get(indexStep);
            mergedStepList.add(step);
        }

        return new TransactionStepGroup(mergedStepList);
    }

    boolean containsAttachStep(String tag) {
        for(TransactionStep step : mSteps) {
            if (!(step instanceof TransactionAttachStep))
                continue;

            TransactionAttachStep attachStep = (TransactionAttachStep) step;
            if (attachStep.getTag().equals(tag))
                return true;
        }
        return false;
    }
}
