package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface ViewAnchor {
    @NonNull ViewGroup findAnchorView(@NonNull ViewGroup containerView);
}
