package com.labs.xls;

import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validation;
import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class ExcelMetaData {
	
	
	private final Path filePath;
	@Getter
	private final ExcelSheet sheet;
	
	public ExcelMetaData(@NotNull final Path filePath, @NotNull final ExcelSheet sheet) {
		
		Assert.notNull(filePath, () -> "File path should not be null");
		final Path parent = filePath.getParent();
		Assert.isTrue(Files.exists(Paths.get(parent.toUri())), () -> "Output directory does not exist: " + parent);
		this.filePath = filePath;
		
		Assert.notNull(sheet, () -> "Sheet should not be null");
		this.sheet = sheet;
		
	}
	
	public FileOutputStream openStream() throws FileNotFoundException {
		return new FileOutputStream(filePath.toFile());
	}
}