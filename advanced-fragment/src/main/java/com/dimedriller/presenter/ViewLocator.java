package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface ViewLocator {
    @NonNull ViewGroup findAnchorView(@NonNull ViewGroup containerView);
}
