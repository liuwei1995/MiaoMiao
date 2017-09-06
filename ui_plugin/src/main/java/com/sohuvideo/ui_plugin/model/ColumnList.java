package com.sohuvideo.ui_plugin.model;


public class ColumnList<T> {

    private int count;
    private T columns;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getColumns() {
        return columns;
    }

    public void setColumns(T columns) {
        this.columns = columns;
    }
}
