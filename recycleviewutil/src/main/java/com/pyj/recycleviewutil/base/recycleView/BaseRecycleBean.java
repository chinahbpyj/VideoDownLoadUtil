package com.pyj.recycleviewutil.base.recycleView;

import java.util.List;

public interface BaseRecycleBean<T> {
    int getPageIndex();

    int getPageSize();

    int getTotal();

    List<T> getItems();
}
