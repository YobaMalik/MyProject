package com.example.demo.Interface;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

    public interface ConvertString {

        default double conv(String vaule) throws ParseException {
            NumberFormat format=NumberFormat.getInstance(Locale.FRANCE);
            Number number=format.parse(vaule);
            return  number.doubleValue();
        }


        default double getMaxValue(String sValue)throws NumberFormatException  {
            String tempString=sValue.replace(";", "/");
            tempString=tempString.replace(" ","");
            tempString=tempString.replace(",",".");
            String[] splt=tempString.split("/");
            double val=-100000.0;
            for (int i=0;i<splt.length;i++) {
                if(this.stringToDouble(splt[i])) {
                    double tempVal=Double.parseDouble(splt[i]);
                    val=(val<tempVal)?tempVal:val;
                }
            }

            return (val!=-100000.0)?val:-100000.0;
        }

        default boolean stringToDouble(String value){
            try {
                double d=Double.parseDouble(value);
            } catch(NumberFormatException e) {
                return false;
            }
            return true;
        }




    }


