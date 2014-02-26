package com.ccc.dreamfile.excel;

import java.util.List;

/**
 * @author RedSword
 * @date 2012-05-06 17:50:28
 */
public class FileExcelSheet {
    private List<FileExcelRow> cells;
    private String sheetName;

    public List<FileExcelRow> getRows() {
        return this.cells;
    }

    public void setRows(List<FileExcelRow> rows) {
        this.cells = rows;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
