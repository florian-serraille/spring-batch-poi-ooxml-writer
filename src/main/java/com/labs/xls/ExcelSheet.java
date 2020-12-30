package com.labs.xls;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ExcelSheet {
	
	@Getter
	private final String sheetName;
	@Getter
	private final List<ExcelTitle> columnsName;
	private final ExcelHeader header;
	private final ExcelFooter footer;
	
	public ExcelSheet(@NotNull final String sheetName, @NotNull List<ExcelTitle> columnsName, final ExcelHeader header, final ExcelFooter footer) {
		
		Assert.notNull(sheetName, () -> "Sheet name should not be null");
		this.sheetName = sheetName;
		
		Assert.notEmpty(columnsName, () -> "Columns name name should be provided");
		this.columnsName = columnsName;
		
		this.header = header;
		this.footer = footer;
	}
	
	public int getColumnsSize(){
		return columnsName.size();
	}
	
	public Optional<ExcelHeader> getHeader() {
		return Optional.ofNullable(header);
	}
	
	public Optional<ExcelFooter> getFooter() {
		return Optional.ofNullable(footer);
	}
	
	public float getHighestTitleSize() {
		
		return this.columnsName.stream()
		                       .max(Comparator.comparingDouble(t -> t.getStyle().getFontSize()))
		                       .orElseThrow(IllegalStateException::new)
		                       .getStyle()
		                       .getFontSize();
	}
}
