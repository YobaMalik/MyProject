package com.example.demo.Pasport;

import com.example.demo.Interface.IRows;

import java.util.ArrayList;
import java.util.List;

public class RowfTable<T> implements IRows<T> {
    private List<T> RowList=new ArrayList<>();

    public void addValue(int index, T iValue){
        RowList.add(index, iValue);
    }

    public void addValue(T iValue) {
        RowList.add(iValue);
    }
    public int getSize (){
        return RowList.size();
    }
    public T getValue(int index){
        return RowList.get(index);
    }
    public T getLast(){
        return RowList.get(0);
    }
    public List<T> getRowList(){
        return this.RowList;
    }
}
