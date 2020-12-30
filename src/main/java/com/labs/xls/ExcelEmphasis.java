package com.labs.xls;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExcelEmphasis {

	NONE(false, false),
	BOLD(true, false),
	ITALIC(false, true);
	
	private final boolean bold;
	private final boolean italic;
	
	boolean isBold() {
		return this.bold;
	}
	
	boolean isItalic() {
		return this.italic;
	}
	
}
