package com.neklo.demo.csv;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.neklo.demo.model.ObjectPosition;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Emulates a small stream of data
 */
@Component
@Slf4j
public class CoordsCsvParser {

	@SneakyThrows
	public List<ObjectPosition> getData(int startPosition, int batchSize) {
		log.info("Processing batch {} to {}", startPosition * batchSize, batchSize * (startPosition + 1) - 1);
		List<ObjectPosition> result = new ArrayList<>();
		Path file = Paths.get("src/main/resources/coords.csv");
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		MappingIterator<Map<String, String>> it = csvMapper
				.readerFor(Map.class)
				.with(schema)
				.readValues(Files.newInputStream(file));
		List<Map<String, String>> allList = it.readAll();
		if (batchSize * (startPosition + 1) - 1 > allList.size()) {
			return Collections.emptyList();
		}
		List<Map<String, String>> list = allList.subList(startPosition * batchSize, batchSize * (startPosition + 1) - 1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss a");
		list.forEach(element -> {
			ObjectPosition objectPosition = new ObjectPosition();
			objectPosition.setObject(element.get("object"));
			objectPosition.setLatitude(new BigDecimal(element.get("latitude")));
			objectPosition.setLongitude(new BigDecimal(element.get("longitude")));
			LocalDateTime dateTime = LocalDateTime.parse(element.get("timestamp"), formatter);
			objectPosition.setTimestamp(dateTime);
			result.add(objectPosition);
		});

		return result;
	}
}
