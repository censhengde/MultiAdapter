package com.tencent.lib.multi.core;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author：岑胜德 on 2021/3/14 17:18
 *
 * 说明：
 */
public interface OnLongClickItemListener<T> {

    boolean onLongClickItem(@NonNull View v,@Nullable T item, int position);

}
