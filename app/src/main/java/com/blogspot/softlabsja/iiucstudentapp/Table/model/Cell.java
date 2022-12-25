package com.blogspot.softlabsja.iiucstudentapp.Table.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Cell {
    @NonNull
    private final String mId;
    @Nullable
    private final Object mData;

    public Cell(@NonNull String id, @Nullable Object data) {
        this.mId = id;
        this.mData = data;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public Object getData() {
        return mData;
    }
}
