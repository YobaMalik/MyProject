package com.example.demo.Interface;

public interface Rows<T> {
     void addValue(int index, T iValue);
      void addValue(T iValue);
     int getSize ();

     T getValue(int index);

     T getLast();
}
