package com.ccc.dreamfile.excel;

import java.util.List;

/**
 * @author RedSword
 * @date 2012-05-06 17:50:28
 */
public class FileExcelSheet {
	private final List<FileExcelRow> cells;
	private final String sheetName;

	public FileExcelSheet(String sheetName, List<FileExcelRow> cells) {
		this.sheetName = sheetName;
		this.cells = cells;
	}

	public List<FileExcelRow> getRows() {
		return this.cells;
	}

	public String getSheetName() {
		return this.sheetName;
	}

}
