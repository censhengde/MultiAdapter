package com.tencent.lib.multi.core;

import android.util.SparseArray;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Author：岑胜德 on 2021/1/27 16:33
 *
 * 说明：实现Item多样式的公共逻辑封装。本质上是代理Adapter 的生命周期，
 * 将 Adapter 生命周期分发给了position对应的ItemType。
 */
public abstract class MultiHelper<T, VH extends RecyclerView.ViewHolder> {

    public static final int INVALID_VIEW_TYPE = -1;
    private final RecyclerView.Adapter realAdapter;

    /**
     * ItemType集合，以其id为key，ItemType为value。
     */
    private final SparseArray<ItemType<T, VH>> mItemTypes = new SparseArray<>();
    /**
     * ItemType在Adapter position上的对应记录，其索引与Adapter position一一对应,
     * 表示某position想要表现的ItemType，
     * 注意，并非一定与数据集的index对应。
     */
    private List<ItemType<T, VH>> mItemTypeRecord;


    public MultiHelper(Adapter realAdapter) {
        this.realAdapter = realAdapter;
    }

    public int getItemCount() {
        return realAdapter == null ? 0 : realAdapter.getItemCount();
    }


    public final int getItemViewType(int position) {
        if (position == RecyclerView.NO_POSITION) {
            return INVALID_VIEW_TYPE;
        }
        ItemType<T, VH> currentType = null;
        final int typeSize = mItemTypes.size();
        //单样式
        if (typeSize == 1) {
            currentType = mItemTypes.valueAt(0);
        }
        //多样式
        else if (typeSize > 1) {
            final T data = getItem(position);

            //mItemTypeRecord 初始化在这里进行
            if (mItemTypeRecord == null) {
                mItemTypeRecord = new ArrayList<>();
            }
            if (position >= 0 && position < mItemTypeRecord.size()) {
                currentType = mItemTypeRecord.get(position);
                //如果当前 position 对应的ItemType不再与旧的ItemType匹配，
                // 说明当前 position 对应的ItemType已经被更改，在RecyclerView列表进行增、删、改操作
                // 时候可能会出现这种情况，这时候就需重新匹配当前position所对应的ItemType
                if (!currentType.matchItemType(data, position)) {

                    final ItemType<T, VH> newType = findCurrentType(data, position);
                    if (newType != null) {
                        currentType = newType;
                        mItemTypeRecord.set(position, newType);
                    }

                }
            }
            //首次进来 mItemTypeRecord.isEmpty() 为true，会走这里。
            else {
                currentType = findCurrentType(data, position);
                if (currentType != null) {
                    mItemTypeRecord.add(currentType);
                }

            }
        }

        return currentType == null ? INVALID_VIEW_TYPE : currentType.getViewType();
    }

    /**
     * 遍历查找当前position对应的ItemType。
     *
     * @param data
     * @param position
     * @return
     */
    @Nullable
    private ItemType<T, VH> findCurrentType(T data, int position) {
        //为当前position 匹配它的ItemType
        for (int i = 0; i < mItemTypes.size(); i++) {
            final ItemType<T, VH> type = mItemTypes.valueAt(i);
            if (type.matchItemType(data, position)) {
                return type;
            }
        }
        return null;
    }

    @NotNull
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ItemType<T, VH> type = mItemTypes.get(viewType);
        if (viewType == INVALID_VIEW_TYPE || type == null) {//表示无效
            throw new IllegalStateException("ItemType 不合法：viewType==" + viewType + " ItemType==" + type);
        }
        final VH holder = type.onCreateViewHolder(parent);
        type.onViewHolderCreated(holder, this);
        return holder;
    }

    public final void onBindViewHolder(@NonNull VH holder,
            int position,
            @NonNull List<Object> payloads) {
        /*统一捕获由position引发的可能异常*/
        try {
            final int size = mItemTypes.size();
            ItemType<T, VH> type;
            if (size == 1) {/*单样式*/
                type = mItemTypes.valueAt(0);
            } else if (size > 1) {/*多样式*/
                /*可能有越界风险*/
                type = mItemTypeRecord.get(position);
            } else {
                return;
            }
            if (payloads.isEmpty()) {
                type.onBindViewHolder(holder, this, position);
            }
            /*局部刷新*/
            else {
                type.onBindViewHolder(holder, this, position, payloads);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    public abstract T getItem(int position);


    /**
     * 绝大多数情况下，用户不需要主动维护ItemTypeRecord集合，
     * 即便当对item进行增、删、改（伴随数据集元素的增删改）。但用户应当拥有主动维护这个集合的权力。
     * @return ItemType记录。当RecyclerView是单样式item的时候返回null。
     */
    @Nullable
    public final List<ItemType<T, VH>> getItemTypeRecord() {
        return mItemTypeRecord;
    }


    /**
     * 注册ItemType
     * @param type
     * @return
     */
    public final MultiHelper<T, VH> addItemType(ItemType<T, VH> type) {
        if (type == null) {
            return this;
        }
        mItemTypes.put(type.getViewType(), type);
        return this;
    }


}
