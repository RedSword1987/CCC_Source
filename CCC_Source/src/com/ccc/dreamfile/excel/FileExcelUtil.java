package com.ccc.dreamfile.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ccc.dreamfile.handlefile.FileHandleI;
import com.ccc.dreamtag.tag.function.StringUtil;

/**
 * @author RedSword
 * @date 2011-06-08 23:54:34
 * @version 2.0
 */

public class FileExcelUtil {

    public static List<List<String[]>> convertListListStringArray(String filePath, FileHandleI fh) {
        InputStream stream = null;
        Workbook xwb = null;
        try {
            stream = new FileInputStream(filePath);
            if (filePath.endsWith(".xls")) {
                xwb = new HSSFWorkbook(stream);
            } else if (filePath.endsWith(".xlsx")) {
                xwb = new XSSFWorkbook(stream);
            } else {
                return null;
            }
            List<List<String[]>> returnlist = new ArrayList<List<String[]>>();
            Sheet sheet;
            Row row;
            Cell cell;
            String[] str;
            List<String[]> list = null;
            for (int d = 0; d < xwb.getNumberOfSheets(); d++) {
                list = new ArrayList<String[]>();
                sheet = xwb.getSheetAt(d);
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    str = new String[row.getLastCellNum()];
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        cell = row.getCell(j);
                        if (cell != null) {
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                try {
                                    String ss = row.getCell(j).toString();
                                    if (!StringUtil.isEmpty(ss)) {
                                        if (ss.indexOf("E") != -1) {
                                            Double sss = Double.valueOf(ss);
                                            str[j] = String.valueOf(sss.longValue());
                                        } else {
                                            str[j] = String.valueOf(row.getCell(j).toString());
                                        }
                                        Double sss = Double.valueOf(ss);
                                        System.out.println(sss.longValue());
                                    } else {
                                        str[j] = "";
                                    }

                                } catch (Exception e) {
                                    str[j] = String.valueOf(row.getCell(j).toString());
                                }
                            } else {
                                str[j] = String.valueOf(row.getCell(j).toString());
                            }
                        }
                    }
                    list.add(str);
                }
                returnlist.add(list);
            }
            return returnlist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void handleExcel(String filePath, FileHandleI fh) {
        InputStream stream = null;
        Workbook xwb = null;
        try {
            stream = new FileInputStream(filePath);
            if (filePath.endsWith(".xls")) {
                xwb = new HSSFWorkbook(stream);
            } else if (filePath.endsWith(".xlsx")) {
                xwb = new XSSFWorkbook(stream);
            } else {
                return;
            }
            Sheet sheet;
            Row row;
            Cell cell;
            String[] str;
            for (int d = 0; d < xwb.getNumberOfSheets(); d++) {
                sheet = xwb.getSheetAt(d);
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    str = new String[row.getLastCellNum()];
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        cell = row.getCell(j);
                        if (cell != null) {
                            str[j] = String.valueOf(row.getCell(j).toString());
                        }
                    }
                    fh.handleObject(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fh.handleOver();
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeFile(FileExcelBean fileexcelbean, String outputFile) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();

            for (FileExcelSheet fileexcelsheet : fileexcelbean.getSheets()) {
                HSSFSheet sheet = workbook.createSheet(fileexcelsheet.getSheetName());
                int begin = 1;
                for (FileExcelRow fileexcelrow : fileexcelsheet.getRows()) {
                    HSSFRow row = sheet.createRow(begin);
                    int rowBegin = 0;
                    for (FileExcelCell cell : fileexcelrow.getList()) {
                        HSSFCell cell1 = row.createCell(rowBegin);
                        if (2 == cell.getContentType()) {
                            cell1.setCellValue(Integer.valueOf(cell.getContent().toString()));
                            cell1.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        } else {
                            cell1.setCellValue(cell.getContent().toString());
                            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
                        }
                        if (1 != cell.getLength()) {
                            sheet.addMergedRegion(new Region(begin, (short) rowBegin, begin, (short) (rowBegin + cell.getLength() - 1)));
                        }
                        rowBegin += cell.getLength();
                    }
                    begin++;
                }
            }
            FileOutputStream fOut = new FileOutputStream(outputFile);
            workbook.write(fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
