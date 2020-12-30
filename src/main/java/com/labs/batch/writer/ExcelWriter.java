package com.labs.batch.writer;

import com.labs.model.Person;
import com.labs.xls.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
public class ExcelWriter implements ItemStreamWriter<Person> {
	
	private final ExcelMetaData metaData;
	
	private FileOutputStream outputStream;
	private Workbook workBook;
	private Sheet sheet;
	private CellStyle tableStyle;
	private int rowCursor;
	
	@Override
	public void open(final ExecutionContext executionContext) throws ItemStreamException {
		
		openOutputStream();
		initWorkbook();
		createSheet();
		
		initStyle();
		
		writeHeader();
		writeTitle();
	}
	
	private void openOutputStream() {
		
		try {
			this.outputStream = metaData.openStream();
			
		} catch (FileNotFoundException e) {
			throw new ItemStreamException("Error top open stream", e);
		}
	}
	
	public void initWorkbook() {
		this.workBook = new HSSFWorkbook();
	}
	
	private void createSheet() {
		this.sheet = workBook.createSheet(metaData.getSheet().getSheetName());
	}
	
	private void initStyle() {
		
		final ExcelSheet sheet = metaData.getSheet();
		
		sheet.getColumnsName()
		     .forEach(column -> column.configureStyle(workBook.createCellStyle(), workBook.createFont()));
		
		sheet.getHeader()
		     .ifPresent(header -> header.configureStyle(workBook.createCellStyle(), workBook.createFont()));
		
		sheet.getFooter()
		     .ifPresent(footer -> footer.configureStyle(workBook.createCellStyle(), workBook.createFont()));
		
		this.tableStyle = StyleConfigurator.configureCellStyle(workBook.createCellStyle(),
		                                                       workBook.createFont(),
		                                                       new ExcelCellStyle());
	}
	
	private void writeHeader() {
		
		final ExcelSheet excelSheet = metaData.getSheet();
		excelSheet.getHeader().ifPresent(writeSection(excelSheet));
	}
	
	private int incrementCursor() {
		return rowCursor++;
	}
	
	private void writeTitle() {
		
		final ExcelSheet excelSheet = metaData.getSheet();
		final Row row = this.sheet.createRow(incrementCursor());
		row.setHeightInPoints(excelSheet.getHighestTitleSize() + 5);
		
		excelSheet
				.getColumnsName()
				.forEach(column -> {
					createCell(row, column.getIndex(), column.getName(), column.getCellStyle());
					this.sheet.autoSizeColumn(column.getIndex());
				});
	}
	
	@Override
	public void update(final ExecutionContext executionContext) {
	
	}
	
	@Override
	public void close() throws ItemStreamException {
		
		writeFooter();
		adjustColumnWidth();
		
		try {
			workBook.write(outputStream);
		} catch (IOException e) {
			log.error("Error to write data");
		}
		
		try {
			workBook.close();
		} catch (IOException e) {
			throw new ItemStreamException("Error to close stream", e);
		}
		
	}
	
	private void adjustColumnWidth() {
		
		IntStream.range(0, metaData.getSheet().getColumnsName().size())
		         .forEach(columnIndex -> sheet.autoSizeColumn(columnIndex));
	}
	
	private void writeFooter() {
		
		final ExcelSheet excelSheet = metaData.getSheet();
		excelSheet.getFooter().ifPresent(writeSection(excelSheet));
	}
	
	private Consumer<ExcelSection> writeSection(final ExcelSheet excelSheet) {
		
		return excelSection -> {
			
			final Row row = this.sheet.createRow(rowCursor);
			CellRangeAddress address = new CellRangeAddress(rowCursor, rowCursor, 0,
			                                                excelSheet.getColumnsSize() - 1);
			sheet.addMergedRegion(address);
			row.setHeightInPoints(excelSection.getRowHeight());
			
			createCell(row, 0, excelSection.getContent(), excelSection.getCellStyle());
			incrementCursor();
		};
	}
	
	private void createCell(final Row row, final int column, final String content, final CellStyle cellStyle) {
		CellUtil.createCell(row, column, content, cellStyle);
	}
	
	@Override
	public void write(final List<? extends Person> items) {
		
		items.forEach(item -> {
			
			final Row row = sheet.createRow(incrementCursor());
			final ArrayList<String> list = item.getRowAsString();
			IntStream.range(0, list.size()).forEach(i -> createCell(row, i, list.get(i), this.tableStyle));
		});
	}
}
