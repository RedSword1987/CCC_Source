package com.ccc.dreamfile.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ccc.dreamfile.handlefile.FileHandleI;

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
									String result = null;
									if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
										SimpleDateFormat sdf = null;
										if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
											sdf = new SimpleDateFormat("HH:mm");
										} else {// 日期  
											sdf = new SimpleDateFormat("yyyy-MM-dd");
										}
										Date date = cell.getDateCellValue();
										result = sdf.format(date);
									} else if (cell.getCellStyle().getDataFormat() == 58) {
										// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										double value = cell.getNumericCellValue();
										Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
										result = sdf.format(date);
									} else {
										double value = cell.getNumericCellValue();
										CellStyle style = cell.getCellStyle();
										DecimalFormat format = new DecimalFormat();
										String temp = style.getDataFormatString();
										// 单元格设置成常规  
										if (temp.equals("General")) {
											format.applyPattern("#");
										}
										result = format.format(value);
									}
									str[j] = result;

								} catch (Exception e) {
									str[j] = String.valueOf(row.getCell(j).toString());
								}
							} else {
								Cell cl = row.getCell(j);
								str[j] = String.valueOf(cl);
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
						if (cell.getColor() != null) {
							HSSFCellStyle cellStyle = workbook.createCellStyle();
							cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
							cellStyle.setFillForegroundColor(cell.getColor());//前景填充色
							cellStyle.setFillBackgroundColor(cell.getColor());
							cell1.setCellStyle(cellStyle);
						}

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
