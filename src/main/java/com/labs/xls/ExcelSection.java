package com.labs.xls;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

public abstract class ExcelSection {
	
	@Getter
	private final String content;
	@Getter
	private final ExcelCellStyle style;
	@Getter
	private final float rowHeight;
	
	@Setter
	private CellStyle cellStyle;
	
	protected ExcelSection(@NotNull final String content,@NotNull final ExcelCellStyle style, final float rowHeight) {
	
		Assert.hasLength(content, () -> "Content should not be null or empty");
		this.content = content;
		
		Assert.notNull(style, () -> "Style should not be null");
		this.style = style;
		
		this.rowHeight = rowHeight;
	}
	
	public CellStyle getCellStyle() {
		
		Assert.notNull(cellStyle, () -> "Cell style is not set, configure it first");
		return cellStyle;
	}
}
