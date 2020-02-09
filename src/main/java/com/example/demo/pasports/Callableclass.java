package com.example.demo.pasports;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Callableclass  implements Callable<Void> {
    private InputStream fFile;
    private String filename;
    private ConcurrentLinkedQueue<RowfTable<String>> allTable=new ConcurrentLinkedQueue<>();
    public   Callableclass(String filename,InputStream fFile, ConcurrentLinkedQueue<RowfTable<String>> allTable1){
        this.allTable=allTable1;
        this.fFile=fFile;
        this.filename=filename;
    }

		public Void call() throws IOException {
	
			
			XSSFWorkbook pasp=null;
            try {
            	
                CategoryCalc test123 = new CategoryCalc();
                //File afile=new File(fFile);
              //  System.out.println(fFile);
                pasp=new XSSFWorkbook(fFile);
                test123.getinfopart2(pasp); // get info from part 2              
                test123.getNameTitul(pasp);
                test123.calc(pasp, filename, allTable); // get and write in (file and result arraylist) info from part 3 and 5
             //   test123.getinfopart4(pasp);   
              //  test123.testsom("C:\\Users\\Dmitrij.Harko\\Desktop\\Свидетельство о монтаже №123.xlsx");
                pasp.close();

              	System.out.println(filename +" Done" );
            } catch (IOException e){
                e.printStackTrace();
            }
            finally {
            	pasp.close();
            }
            return null;
        }
    }

