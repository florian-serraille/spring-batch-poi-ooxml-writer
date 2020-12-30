package com.labs.xls;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.util.Assert;

@RequiredArgsConstructor
public class ExcelTitle implements Stylized {
	
	@Getter
	private final int index;
	@Getter
	private final String name;
	@Getter
	private final ExcelCellStyle style;
	
	@Setter
	private CellStyle cellStyle;
	
	public CellStyle getCellStyle() {
		
		Assert.notNull(cellStyle, () -> "Cell style is not set, configure it first");
		return cellStyle;
	}
}