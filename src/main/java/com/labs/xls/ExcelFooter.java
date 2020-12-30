package com.labs.xls;

public class ExcelFooter extends ExcelSection implements Stylized {
	
	public ExcelFooter(final String content, final ExcelCellStyle style) {
		super(content, style, style.getFontSize() + 10);
	}
	
	public ExcelFooter(final String content, final ExcelCellStyle style, final float rowHeight) {
		super(content, style, rowHeight);
	}
}
