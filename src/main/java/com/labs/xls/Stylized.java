package com.labs.xls;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

public interface Stylized {
	
	ExcelCellStyle getStyle();
	void setCellStyle(CellStyle cellStyle);
	
	default void configureStyle(final CellStyle cellStyle, final Font font){
		setCellStyle(StyleConfigurator.configureCellStyle(cellStyle, font, getStyle()));
	}
	
}
