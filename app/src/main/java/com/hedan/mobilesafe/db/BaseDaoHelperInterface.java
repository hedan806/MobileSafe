package com.hedan.mobilesafe.db;

import com.hedan.dao.BlackNumber;

import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public interface BaseDaoHelperInterface<T> {
    public T addData(T t);
    public void deleteData(String id);
    public <T> T getDataById(String id);
    public List getAllData();
    public boolean hasKey(String id);
    public long getTotalCount();
    public void deleteAll();
}
