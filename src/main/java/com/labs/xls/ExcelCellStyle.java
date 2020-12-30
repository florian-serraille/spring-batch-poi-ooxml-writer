package com.labs.xls;

import lombok.Getter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

@Getter
public class ExcelCellStyle {

	private final HorizontalAlignment horizontalAlign;
	private final VerticalAlignment verticalAlign;
	private final IndexedColors fontColor;
	private final short fontSize;
	private final IndexedColors backGroundColor;
	private final ExcelEmphasis emphasis;
	
	public ExcelCellStyle(final HorizontalAlignment horizontalAlign, final VerticalAlignment verticalAlign,
	                      final IndexedColors fontColor, final Integer fontSize,
	                      final IndexedColors backGroundColor, final ExcelEmphasis emphasis) {
		
		this.horizontalAlign = horizontalAlign == null ? HorizontalAlignment.CENTER : horizontalAlign;
		this.verticalAlign = verticalAlign == null ? VerticalAlignment.CENTER : verticalAlign;
		this.fontColor = fontColor == null ? IndexedColors.AUTOMATIC : fontColor;
		this.fontSize = (short) (fontSize == null ? 12 : fontSize);
		this.backGroundColor = backGroundColor == null ? IndexedColors.AUTOMATIC : backGroundColor;
		this.emphasis = emphasis == null ? ExcelEmphasis.NONE : emphasis;
	}
	
	public ExcelCellStyle() {
		
		this.horizontalAlign = HorizontalAlignment.CENTER;
		this.verticalAlign = VerticalAlignment.CENTER;
		this.fontColor = IndexedColors.AUTOMATIC;
		this.fontSize = (short) 12;
		this.backGroundColor = IndexedColors.AUTOMATIC;
		this.emphasis = ExcelEmphasis.NONE;
	}
}
