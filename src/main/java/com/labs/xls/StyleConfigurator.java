package com.labs.xls;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.util.Assert;

import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StyleConfigurator {
	
	public static CellStyle configureCellStyle(final CellStyle cellStyle, final Font font,
	                                           ExcelCellStyle excelCellStyle) {
		
		Assert.notNull(cellStyle, () -> "Cell style should not be null to be configured");
		cellStyle.setVerticalAlignment(excelCellStyle.getVerticalAlign());
		cellStyle.setAlignment(excelCellStyle.getHorizontalAlign());
		cellStyle.setFillBackgroundColor(excelCellStyle.getBackGroundColor().getIndex());
		
		final Font configuredFont = configureFont(font, excelCellStyle);
		cellStyle.setFont(configuredFont);
		
		cellStyle.setFillPattern(SOLID_FOREGROUND);
		cellStyle.setBorderBottom(THIN);
		cellStyle.setBorderTop(THIN);
		cellStyle.setBorderRight(THIN);
		cellStyle.setBorderLeft(THIN);
		
		return cellStyle;
	}
	
	private static Font configureFont(final Font font, ExcelCellStyle excelCellStyle) {
		
		Assert.notNull(font, () -> "Font should not be null to be configured");
		
		font.setFontHeightInPoints(excelCellStyle.getFontSize());
		
		final ExcelEmphasis emphasis = excelCellStyle.getEmphasis();
		font.setBold(emphasis.isBold());
		font.setItalic(emphasis.isItalic());
		
		return font;
	}
	
}
