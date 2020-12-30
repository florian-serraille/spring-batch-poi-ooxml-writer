package com.labs.batch;

import com.labs.batch.writer.ExcelWriter;
import com.labs.model.Person;
import com.labs.xls.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class BatchConfiguration {
	
	@Bean
	Job job(final JobBuilderFactory jobBuilderFactory, final Step step) {
		
		return jobBuilderFactory.get("job").start(step).build();
	}
	
	@Bean
	Step step(final StepBuilderFactory stepBuilderFactory,
	          final ItemReader<Person> itemReader,
	          final ItemWriter<Person> itemWriter) {
		
		return stepBuilderFactory.get("step")
				       .<Person, Person>chunk(4)
				       .reader(itemReader)
				       .writer(itemWriter)
				       .build();
	}
	
	@Bean
	ItemReader<Person> itemReader(@Value("classpath:person.csv") final Resource resource) {
		
		return new FlatFileItemReaderBuilder<Person>()
				       .name("personReader")
				       .resource(resource)
				       .delimited()
				       .names(Person.PersonRecord.headers)
				       .fieldSetMapper(fieldSet -> new Person(
						       fieldSet.readLong("id"),
						       fieldSet.readString("first name"),
						       fieldSet.readString("last name"),
						       fieldSet.readString("email"),
						       fieldSet.readString("gender"),
						       LocalDate.parse(fieldSet.readString("birth date"),
						                       DateTimeFormatter.ofPattern("yyyy/MM/dd")),
						       fieldSet.readString("street address"),
						       fieldSet.readString("state"),
						       fieldSet.readString("country"),
						       fieldSet.readString("company name")
				       ))
				       .linesToSkip(1)
				       .build();
		
	}
	
	@Bean
	@JobScope
	ItemStreamWriter<Person> itemWriter() {
		
		final Path path = Paths.get("/tmp/test.xls");
		
		ExcelCellStyle titleStyle = new ExcelCellStyle(HorizontalAlignment.CENTER,
		                                               VerticalAlignment.CENTER,
		                                               IndexedColors.BLACK,
		                                               18,
		                                               IndexedColors.BLACK,
		                                               ExcelEmphasis.BOLD);
		
		final String[] headers = Person.PersonRecord.headers;
		
		final List<ExcelTitle> excelTitles = IntStream.range(0, headers.length)
		                                              .mapToObj(i -> new ExcelTitle(i, headers[i], titleStyle))
		                                              .collect(Collectors.toList());
		
		ExcelCellStyle sectionStyle = new ExcelCellStyle(HorizontalAlignment.CENTER,
		                                                 VerticalAlignment.CENTER,
		                                                 IndexedColors.BLACK,
		                                                 24,
		                                                 IndexedColors.BLACK,
		                                                 ExcelEmphasis.BOLD);
		
		final ExcelHeader header = new ExcelHeader("Header", sectionStyle);
		final ExcelFooter footer = new ExcelFooter("Footer", sectionStyle);
		
		final ExcelSheet sheet = new ExcelSheet("new sheet",
		                                             excelTitles,
		                                             header,
		                                             footer);
		
		final ExcelMetaData metadata = new ExcelMetaData(path, sheet);
		return new ExcelWriter(metadata);
	}
	
}
