package com.example.demo.OBRE;


import com.example.demo.Pasport.RowfTable;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class OBREapplication implements GetTableNumber{

    private XWPFTable zTable=null;
    private XWPFTable table3Part=null;
    private int firstRow=3;
    public XWPFTable GetTable(){
        return this.table3Part;
    }

    private void mergerow(XWPFTableRow emptyR) {
        CTRow testR=emptyR.getCtRow();
        List<CTTc> rList=testR.getTcList();
        for (int i=0;i<rList.size();i++) {
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

        String val=this.GetParagraphText(table.getRow(firstrow).getCell(col));
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

public String GetParagraphText(XWPFTableCell cell){
        String sValue = null;
    CTP Paragraph=cell.getCTTc().getPArray(0);
    if(Paragraph.getRList().size()>0 && Paragraph.getRArray(0).getTList().size()>0){
        sValue= Paragraph.getRArray(0).getTArray(0).getStringValue();
    }
   return sValue;
}
    public void MrgClTbl(XWPFTable iTable, int fRow,int...args) {
        int rowsize=iTable.getRows().size();
        // HashMap<String,String> rMap=new HashMap<>();
        String cellV=this.GetParagraphText(iTable.getRow(fRow).getCell(args[0]));
        int z=fRow;
        for (int i=fRow;i<rowsize;i++) {
         String aStr=this.GetParagraphText(iTable.getRow(i).getCell(args[0]));
            if(cellV!=null && aStr!=null && cellV.compareTo(aStr)!=0 && iTable.getRow(i).getTableCells().size()>1) {
                cellV=aStr;
                if ( (i-z>1)) {
                    for (int arg : args) {
                        this.mergecol(iTable, z, i, arg);
                    }
                }
                z=i;
            }
            if(cellV==null){
                cellV=aStr;
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
        iTable1.getRow(1).getCell(0).setText(z.getValue(8));

        this.removeObj(iTable1, 2, 1);
        iTable1.getRow(2).getCell(1).setText(z.getValue(10));

        this.removeObj(iTable1, 3, 2);
        iTable1.getRow(3).getCell(2).setText(z.getValue(9));

        this.removeObj(iTable1, 4, 2);
        iTable1.getRow(4).getCell(2).setText(z.getValue(11));

        this.removeObj(iTable1, 5, 2);
        iTable1.getRow(5).getCell(2).setText(z.getValue(33));

        this.removeObj(iTable1, 6, 2);
        iTable1.getRow(6).getCell(2).setText(z.getValue(12));

        this.removeObj(iTable1, 7, 2);
        iTable1.getRow(7).getCell(2).setText(z.getValue(17));

        this.removeObj(iTable1, 8, 2);
        iTable1.getRow(8).getCell(2).setText(z.getValue(18));

        this.removeObj(iTable1, 9, 2);
        iTable1.getRow(9).getCell(2).setText(z.getValue(19));

        this.removeObj(iTable1, 10, 2);
        iTable1.getRow(10).getCell(2).setText(z.getValue(20));

        this.removeObj(iTable1, 11, 1);
        iTable1.getRow(11).getCell(1).setText(z.getValue(13));

        this.removeObj(iTable1, 12, 1);
        iTable1.getRow(12).getCell(1).setText(z.getValue(12));

        this.removeObj(iTable1, 13, 2);
        iTable1.getRow(13).getCell(2).setText(z.getValue(23));

        this.removeObj(iTable1, 14, 2);
        iTable1.getRow(14).getCell(2).setText(z.getValue(24));

        this.removeObj(iTable1, 15, 1);
        iTable1.getRow(15).getCell(1).setText(z.getValue(29));

        this.removeObj(iTable1, 16, 1);
        iTable1.getRow(16).getCell(1).setText(z.getValue(15));

        this.removeObj(iTable1, 17, 1);
        iTable1.getRow(17).getCell(1).setText(z.getValue(30));

        this.removeObj(iTable1, 18, 1);
        iTable1.getRow(18).getCell(1).setText("1000");
        this.removeObj(iTable1, 19, 1);
        iTable1.getRow(19).getCell(1).setText(z.getValue(31));

        // iTable1.getRow(1).getCell(1).
        // iTable1.getRow(1).getCell(1).
        // String rplc=iTable1.getRow(1).getCell(1).getText();
        //iTable1.getRow(1).getCell(1)
        //	iTable1.getRow(1).getCell(1).setText(rplc.replaceAll(rplc, z.getValue(10).toString()));

    }

    private void SetValue(XWPFTableRow row,int col,String val){
        CTP Paragraph=row.getCell(col).getCTTc().getPArray(0);
        CTR run=Paragraph.addNewR();
        CTText text=run.addNewT();
        text.setStringValue(val);
    }

    public void createBorder(XWPFTable iTable) {
        iTable.setBottomBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setTopBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setLeftBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setRightBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setInsideHBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
        iTable.setInsideVBorder(XWPFBorderType.SINGLE, 1, 0, "000000");
    }

    public void FillTable(XWPFDocument wDoc, Queue<RowfTable<String>> lineinfo) {
            int count=1;
            List<RowfTable<String>> sortrow=Lists.newArrayList(lineinfo.iterator());
            if (this.table3Part==null) {
                this.table3Part = this.getTableIter(wDoc, 3);
            }
            XWPFTableRow row= this.table3Part.createRow();
            row.createCell();
            row.createCell();
            this.mergerow(row);

            row.getCell(0).setText(sortrow.get(0).getValue(8));
            Iterator<RowfTable<String>> iter=sortrow.iterator();
            while(iter.hasNext()) {
                try{
                    RowfTable<String> z=iter.next();
                    XWPFTableRow iRow=this.table3Part.createRow();
                    iRow.createCell();
                    iRow.createCell();

                    this.SetValue(iRow,0,Integer.toString(count));
                   // iRow.getCell(0).setText(Integer.toString(count));
                    this.SetValue(iRow,1,z.getValue(0).replaceAll("\n",""));
                  //  iRow.getCell(1).setText(z.getValue(0).replaceAll("\n",""));
                    this.SetValue(iRow,2,z.getValue(1).replace(".", ","));
                  //  iRow.getCell(2).setText(z.getValue(1).replace(".", ","));
                    this.SetValue(iRow,3,z.getValue(2).replace(".", ","));
                   // iRow.getCell(3).setText(z.getValue(2).replace(".", ","));
                    this.SetValue(iRow,4,z.getValue(3).replace(".", ","));
                   // iRow.getCell(4).setText(z.getValue(3).replace(".", ","));

                    this.SetValue(iRow,5,z.getValue(4));
                  //  iRow.getCell(5).setText(z.getValue(4));
                    this.SetValue(iRow,6,z.getValue(12)+" "+z.getValue(14));
                  //  iRow.getCell(6).setText(z.getValue(12)+" "+z.getValue(14));
                    this.SetValue(iRow,7,z.getValue(5).replace(".", ","));
                 //   iRow.getCell(7).setText(z.getValue(5).replace(".", ","));
                    this.SetValue(iRow,8,z.getValue(6).replace(".", ","));
                 //   iRow.getCell(8).setText(z.getValue(6).replace(".", ","));
                    DecimalFormat ndf=new DecimalFormat("##0.0");
                    double number=Double.parseDouble(z.getValue(7));


                    this.SetValue(iRow,9,ndf.format(number));
                 //   iRow.getCell(9).setText(ndf.format(number));
                    count++;
                }
                catch(NullPointerException e) {
                    e.printStackTrace();
                }
            }


        if(this.zTable==null) {
            this.zTable = this.getTableIter(wDoc, 2);
        }
            XWPFTable newTestTable=wDoc.createTable();
            wDoc.setTable(wDoc.getTables().size()-1,newTestTable);
            this.copyTable(this.zTable,newTestTable);
            this.fTablle2(newTestTable,sortrow.get(0));

        int[] cols= {1,0,2,3,4,5};
        this.MrgClTbl(this.getTableIter(wDoc, 3),this.firstRow,cols);
        this.firstRow=this.getTableIter(wDoc, 3).getRows().size()+1;
        this.createBorder(this.table3Part);
    }
    public void DeleteTabpe(XWPFDocument wDoc){
        wDoc.removeBodyElement(wDoc.getPosOfTable(this.zTable));
    }

    private void copyTable(XWPFTable source, XWPFTable target) {
        if(source.getCTTbl()!=null && source.getCTTbl().getTblPr()!=null && source.getCTTbl().getTblGrid()!=null) {
            target.getCTTbl().setTblPr(source.getCTTbl().getTblPr());
            target.getCTTbl().setTblGrid(source.getCTTbl().getTblGrid());
        }
        for (int r = 0; r<source.getRows().size(); r++) {
            XWPFTableRow targetRow = target.createRow();
            XWPFTableRow row = source.getRows().get(r);
            targetRow.getCtRow().setTrPr(row.getCtRow().getTrPr());
            for (int c=0; c<row.getTableCells().size(); c++) {
                //newly created row has 1 cell
                if (targetRow.getTableCells().size()>0) {
                    XWPFTableCell targetCell = c == 0 ? targetRow.getTableCells().get(0) : targetRow.createCell();
                    XWPFTableCell cell = row.getTableCells().get(c);
                    targetCell.getCTTc().setTcPr(cell.getCTTc().getTcPr());
                    XmlCursor cursor = targetCell.getParagraphArray(0).getCTP().newCursor();
                    for (int p = 0; p < cell.getBodyElements().size(); p++) {
                        IBodyElement elem = cell.getBodyElements().get(p);
                        if (elem instanceof XWPFParagraph) {
                            XWPFParagraph targetPar = targetCell.insertNewParagraph(cursor);
                            cursor.toNextToken();
                            XWPFParagraph par = (XWPFParagraph) elem;
                            copyParagraph(par, targetPar);
                        } else if (elem instanceof XWPFTable) {
                            XWPFTable targetTable = targetCell.insertNewTbl(cursor);
                            XWPFTable table = (XWPFTable) elem;
                            copyTable(table, targetTable);
                            cursor.toNextToken();
                        }
                    }
                    targetCell.removeParagraph(targetCell.getParagraphs().size() - 1);
                }
            }
        }

        target.removeRow(0);
    }

    private void copyParagraph(XWPFParagraph source, XWPFParagraph target) {
        if (source.getCTP()==null&&source.getCTP().getPPr()!=null){
            target.getCTP().setPPr(source.getCTP().getPPr());
        }
        for (int i=0; i<source.getRuns().size(); i++ ) {
            XWPFRun run = source.getRuns().get(i);
            XWPFRun targetRun = target.createRun();
            targetRun.getCTR().setRPr(run.getCTR().getRPr());
            targetRun.setText(run.getText(0));
        }
    }
}