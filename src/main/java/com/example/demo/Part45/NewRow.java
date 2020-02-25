package com.example.demo.Part45;

import java.util.ArrayList;
import java.util.List;

public class NewRow<T> {

private List<T> RowList=new ArrayList<>();
T fVAl=null;
/*
@SuppressWarnings("unchecked")
public void createRow(int i) {
for (int j=0; j<i;j++) {
this.RowList.add((T) "#Н/Д");
}
}*/

public void addValue(int index, T iValue){

    RowList.add(index,iValue);

}
public void addValue(T iValue){

    RowList.add(iValue);

}
/* public T getValue (int index){
     return RowList.get(index);
 }*/
public int getSize (){
    return RowList.size();
}
public T getValue(int index){
    return RowList.get(index);
}
public String getLast(){
    return RowList.get(0).toString();
}
}


