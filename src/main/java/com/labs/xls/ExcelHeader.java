package com.labs.xls;

public class ExcelHeader extends ExcelSection implements Stylized {
	
	public ExcelHeader(final String content, final ExcelCellStyle style) {
		super(content, style, style.getFontSize() + 15);
	}
	
	public ExcelHeader(final String content, final ExcelCellStyle style, final float rowHeight) {
		super(content, style, rowHeight);
	}
}
