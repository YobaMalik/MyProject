package com.example.demo.Rubbish;

import com.example.demo.pasports.RowfTable;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WordFiles {
	private static String paspNumb="";
	private static int count=1;
	private static int tablecount;
	 private XWPFTable gettableIter(XWPFDocument testdoc, int part)  {
		 XWPFTable sTable = null;
		//int desingpressure=0;
		 Iterator<XWPFTable> iter = testdoc.getTablesIterator();
		 while (iter.hasNext()) {
             XWPFTable newtable = iter.next();


             //find desing pressure and pipesize
             List<XWPFTableRow> rowiter = newtable.getRows();
             for (int i = 0; i < rowiter.size(); i++) {
                 List<XWPFTableCell> cellIter = newtable.getRow(i).getTableCells();              
                 for (int j = 0; j < cellIter.size(); j++) {
                     if (newtable.getRow(i).getCell(j).getText().toLowerCase().contains("наименование") &&
                    		 newtable.getRow(i).getCell(j).getText().toLowerCase().contains("участков") && part ==3) {
                         sTable = newtable;                       
                     }
                     
                     if (newtable.getRow(i).getCell(j).getText().toLowerCase().contains("наименование")&& 
                    		 newtable.getRow(i).getCell(j).getText().toLowerCase().contains("параметра")&& part==2) {
                         sTable = newtable;     
                     }
                   /*  if (newtable.getRow(i).getCell(j).getText().toLowerCase().indexOf("расчетные") != -1) {
                         desingpressure = j;
                     }*/
                 }
             }
         }		
		 return sTable;
	 }
	 
	 private void mergerow(XWPFTableRow emptyR) {
		 CTRow testR=emptyR.getCtRow();
			List<CTTc> rList=testR.getTcList();
			for (int i=0;i<rList.size()-1;i++) {
				 rList.get(i).addNewTcPr();
				 CTTcPr TcPr= rList.get(i).getTcPr();
				 CTHMerge merg= TcPr.addNewHMerge();
			 if (i==0){
				merg.setVal(STMerge.RESTART);
			 }
			 else
			 {
				 merg.setVal(STMerge.CONTINUE);
			 }
			}
	 }
	 
	 
	 private void mergecol(XWPFTable table, int firstrow,int lastrow, int col) {
		
		String val=table.getRow(firstrow).getCell(col).getText().toString();
	//	System.out.println(val);
		for (int i=firstrow; i<lastrow;i++) {
			CTTc mCell=table.getRow(i).getCell(col).getCTTc();
			
			if(i==firstrow) {
				this.removeObj(mCell);
				mCell.addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
				
			}
			else 
			{
				this.removeObj(mCell);
				mCell.addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
			}
		}
		CTP Paragraph=table.getRow(firstrow).getCell(col).getCTTc().getPArray(0);
		CTR run=Paragraph.addNewR();
		CTText text=run.addNewT();
		text.setStringValue(val);
	 }
	 
	 private void MrgClTbl(XWPFTable iTable, int fRow,int...args) {
		 int rowsize=iTable.getRows().size();
		// HashMap<String,String> rMap=new HashMap<>();
	
		 String cellV=iTable.getRow(fRow).getCell(args[0]).getText();
		 int z=fRow;
		 
		 for (int i=fRow;i<rowsize-1;i++) {	
				if( cellV.compareTo(iTable.getRow(i).getCell(args[0]).getText())==0) {
					 System.out.println(z+" "+i+" "+iTable.getRow(i).getCell(args[0]).getText());
				}
			 if( cellV.compareTo(iTable.getRow(i).getCell(args[0]).getText())!=0) {
				
				 cellV=iTable.getRow(i).getCell(args[0]).getText();
				 if ( (i-z>1)&&iTable.getRow(i-1).getCell(args[0]).getText().length()>2) {
					 for(int j=0;j<args.length;j++) {
					 this.mergecol(iTable, z, i, args[j]);
					 }
				 }
				 z=i;
			 }
		 }
		 
		 
	 }
	 
	 private void removeObj(XWPFTable iTable1,int rowC, int cellC) {
		 XWPFTableCell tCell=iTable1.getRow(rowC).getCell(cellC);
		 CTTc rCell=tCell.getCTTc();
		 rCell.setPArray(new CTP[] {CTP.Factory.newInstance()});
	 }
	 
	 private void removeObj( CTTc rCell) {
		
		 rCell.setPArray(new CTP[] {CTP.Factory.newInstance()});
	 }
	 
	 private void fTablle2(XWPFTable iTable1, RowfTable<String> z) {
		this.removeObj(iTable1, 1, 0);
		iTable1.getRow(1).getCell(0).setText(z.getValue(8).toString());
		
		this.removeObj(iTable1, 2, 1);
		iTable1.getRow(2).getCell(1).setText(z.getValue(10).toString());
		
		this.removeObj(iTable1, 3, 2);
		iTable1.getRow(3).getCell(2).setText(z.getValue(9).toString());
		
		this.removeObj(iTable1, 4, 2);
		iTable1.getRow(4).getCell(2).setText(z.getValue(11).toString()); 
		
		this.removeObj(iTable1, 5, 2);
		iTable1.getRow(5).getCell(2).setText(z.getValue(33).toString()); 
		
		this.removeObj(iTable1, 6, 2);
		iTable1.getRow(6).getCell(2).setText(z.getValue(12).toString()); 
		
		this.removeObj(iTable1, 7, 2);
		iTable1.getRow(7).getCell(2).setText(z.getValue(17).toString()); 
	
		this.removeObj(iTable1, 8, 2);
		iTable1.getRow(8).getCell(2).setText(z.getValue(18).toString()); 
		
		this.removeObj(iTable1, 9, 2);
		iTable1.getRow(9).getCell(2).setText(z.getValue(19).toString()); 
		
		this.removeObj(iTable1, 10, 2);
		iTable1.getRow(10).getCell(2).setText(z.getValue(20).toString()); 
		
		this.removeObj(iTable1, 11, 1);
		iTable1.getRow(11).getCell(1).setText(z.getValue(13).toString()); 
		
		this.removeObj(iTable1, 12, 1);
		iTable1.getRow(12).getCell(1).setText(z.getValue(12).toString()); 
		
		this.removeObj(iTable1, 13, 2);
		iTable1.getRow(13).getCell(2).setText(z.getValue(23).toString());
		
		this.removeObj(iTable1, 14, 2);
		iTable1.getRow(14).getCell(2).setText(z.getValue(24).toString());
		
		this.removeObj(iTable1, 15, 1);
		iTable1.getRow(15).getCell(1).setText(z.getValue(29).toString());
		
		this.removeObj(iTable1, 16, 1);
		iTable1.getRow(16).getCell(1).setText(z.getValue(15).toString());
		
		this.removeObj(iTable1, 17, 1);
		iTable1.getRow(17).getCell(1).setText(z.getValue(30).toString());
		
		this.removeObj(iTable1, 18, 1);
		iTable1.getRow(18).getCell(1).setText("1000");
		this.removeObj(iTable1, 19, 1);
		iTable1.getRow(19).getCell(1).setText(z.getValue(31).toString());
		
		// iTable1.getRow(1).getCell(1).
		// iTable1.getRow(1).getCell(1).
	// String rplc=iTable1.getRow(1).getCell(1).getText();
		 //iTable1.getRow(1).getCell(1)
	//	iTable1.getRow(1).getCell(1).setText(rplc.replaceAll(rplc, z.getValue(10).toString()));
			
	 }
	 
	 public void testblyat(String fPath) throws IOException {
			FileInputStream fPathStrm=null;
			try {
				fPathStrm=new FileInputStream(new File(fPath));
			XWPFDocument wDoc=new XWPFDocument(fPathStrm);
		    XWPFTable iTable=this.gettableIter(wDoc,3);

			  iTable.getRows().forEach(e->{
				  e.getTableCells().forEach(z->{
					System.out.println(z.getText()); 
				  });
			   });
		    
			} 	 catch (IOException e) {
				e.printStackTrace();
			} finally {if (fPathStrm!=null) {
				fPathStrm.close();
			}

		}
		}
	 
public void FillTable(String fPath, ConcurrentLinkedQueue<RowfTable<String>> lineinfo) throws IOException, ParseException {
	List<RowfTable<String>> sortrow=Lists.newArrayList(lineinfo.iterator());
	sortrow.sort(new Comparator<RowfTable<String>>() {
		@Override
		public int compare(RowfTable<String> o1, RowfTable<String> o2) {
			if(o1.getValue(26).compareTo(o2.getValue(26))>0){
				return 1;
			}
			else if(o1.getValue(26).compareTo(o2.getValue(26))<0){
				return -1;
			} else {
				return 0;
			}
			// TODO Auto-generated method stub
		
		}
		
	});
	FileInputStream fPathStrm=null;
	try {
		fPathStrm=new FileInputStream(new File(fPath));
		XWPFDocument wDoc=new XWPFDocument(fPathStrm);
	    XWPFTable iTable=this.gettableIter(wDoc,3);
		//XWPFTableRow nRow=iTable.getRows().get(iTable.getNumberOfRows()-1);
		tablecount=wDoc.getTables().size()-1;
		//List<XWPFTableCell> iter=iRow.getTableCells();
		//
		Iterator<RowfTable<String>> iter=sortrow.iterator();
		while(iter.hasNext()) {
			try{
     	RowfTable<String> z=iter.next();
     	
			if (paspNumb.compareTo(z.getValue(26).toString())!=0) {
				XWPFTableRow emptyR=iTable.createRow();
				emptyR.createCell();
				emptyR.createCell();
			    this.mergerow(emptyR);
			    emptyR.getCell(0).setText(z.getValue(8));
				paspNumb=z.getValue(26).toString();
				count=1;
			
			XWPFTable nTable=this.gettableIter(wDoc,2);
			wDoc.createTable();
			wDoc.setTable(tablecount, nTable);
			this.fTablle2(wDoc.getTableArray(tablecount), z);
		
			
		//	System.out.println(wDoc.getTableArray(tablecount).getRow(1).getCell(1).getText());
			tablecount++;
			}
			XWPFTableRow iRow=iTable.createRow();
			iRow.createCell();
			iRow.createCell();
		iRow.getCell(0).setText(Integer.toString(count));
		iRow.getCell(1).setText(z.getValue(0).toString().replaceAll("\n",""));
		iRow.getCell(2).setText(z.getValue(1).toString().replace(".", ","));
		iRow.getCell(3).setText(z.getValue(2).toString().replace(".", ","));
		iRow.getCell(4).setText(z.getValue(3).toString().replace(".", ","));
		iRow.getCell(5).setText(z.getValue(4).toString());
		iRow.getCell(6).setText(z.getValue(12).toString()+" "+z.getValue(14).toString());
		iRow.getCell(7).setText(z.getValue(5).toString().replace(".", ","));
		iRow.getCell(8).setText(z.getValue(6).toString().replace(".", ","));
		DecimalFormat ndf=new DecimalFormat("##0.0");
		
     	double number=Double.parseDouble(z.getValue(7).toString());
		iRow.getCell(9).setText(ndf.format(number));
		count++;
		}
		
		catch(NullPointerException e) {
			e.printStackTrace();
		}

		}
		
		
	
		/*iRow.getTableCells().forEach(e->{
			System.out.println(	e.getText());
			e.setText("wtfmen??");
		});*/
		//iTable.addRow(iRow);
		//wDoc.insertTable(wDoc.getTables().size(), iTable);
//this.mergecol(iTable, 10, 20, 3);
			/*iTable=this.gettableIter(wDoc, 3);
			
			  iTable.getRows().forEach(e->{
				  e.getTableCells().forEach(z->{
					
				  });
			
			   });
	*/		
		String fPath1="C:\\Users\\Dmitrij.Harko\\Desktop\\для рэ и об1.docx";
		wDoc.write(new FileOutputStream(new File(fPath1)));
		wDoc.close();
		//fPathStrm=new FileInputStream(new File(fPath1));
		//wDoc=new XWPFDocument(fPathStrm);
		fPathStrm=new FileInputStream(new File(fPath1));
		wDoc=new XWPFDocument(fPathStrm);
		int[] cols= {1,0,2,3,4,5};
		this.MrgClTbl(this.gettableIter(wDoc, 3), 0,cols);
		//this.mergecol(this.gettableIter(wDoc, 3), 4, 6, 1);
		wDoc.write(new FileOutputStream(new File(fPath1)));
		wDoc.close();
		

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {if (fPathStrm!=null) {
		fPathStrm.close();
	}

	}
}
}