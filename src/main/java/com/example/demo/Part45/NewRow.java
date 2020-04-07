package com.example.demo.Part45;

import com.example.demo.Interface.Rows;

import java.util.ArrayList;
import java.util.List;

public class NewRow<T>  implements Rows<T> {

private List<T> RowList=new ArrayList<>();

public void addValue(int index, T iValue){
    RowList.add(index,iValue);
}

public void addValue(T iValue){
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
}


