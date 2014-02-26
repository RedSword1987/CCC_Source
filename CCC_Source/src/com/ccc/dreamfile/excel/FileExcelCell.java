package com.ccc.dreamfile.excel;

/**
 * @author RedSword
 * @date 2012-05-06 17:49:49
 */
public class FileExcelCell {
    private int length = 1;
    private Object content = null;
    //1:String 2:int
    private int contentType = 1;

    public FileExcelCell(Object content) {
        this.content = content;
    }
    public FileExcelCell(Object content,int contentType) {
        this.content = content;
        this.contentType = contentType;
    }
    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Object getContent() {
        if (this.content==null) {
            return "";
        }
        return this.content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
    /**
     * @return the contentType
     */
    public int getContentType() {
        return this.contentType;
    }
    /**
     * @param contentType the contentType to set
     */
    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

}
