package com.tgb.lk.ahibernate.dao;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

    SQLiteOpenHelper getDbHelper();

    long insert(T entity);

    long insert(T entity, boolean flag);

    int delete(int id);

    void delete(Integer... ids);

    void update(T entity);

    T get(int id);

    T findBySql(String sql, String... selectionArgs);

    List<T> findBySql2List(String sql, String... selectionArgs);

    List<T> find();

    List<T> find(String[] columns, String selection,
                 String[] selectionArgs, String groupBy, String having,
                 String orderBy, String limit);

    boolean isExist(String sql, String... selectionArgs);

    List<Map<String, String>> query2MapList(String sql,
                                            String[] selectionArgs);

    void execSql(String sql, Object... selectionArgs);

}