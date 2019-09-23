package com.app.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.app.exception.handler.APP_NotFoundException;
import com.app.model.Person;
import com.app.service.impl.PersonService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(description = "Person Controller Having all curd operations")
public class PersonController {

	@Autowired
	public PersonService personServiceImpl;

	@ApiOperation(value = "Person", notes = "Adding new Person Details", response = Person.class)
	@PostMapping(path = "/addPerson", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {
		System.out.println("Adding Person.......");
		Person person_data = personServiceImpl.addPerson(person);
		return new ResponseEntity<Person>(person_data, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Person", notes = "Getting All Persons Details", response = Person.class)
	@GetMapping(path = "/getAllPersons", produces = "application/json")
	public ResponseEntity<List<Person>> getAllPerson() {
		System.out.println("Getting All Persons Data.......");
		List<Person> persons = personServiceImpl.getAllPersons();
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}

	@ApiOperation(value = "Person", notes = "Getting Person Details Based on PersonID", response = Person.class)
	@GetMapping(path = "/getPersonByID/{Id}")
	public ResponseEntity<Person> getPersonById(@PathVariable(value = "Id") Integer Id) throws APP_NotFoundException {
		System.out.println("Getting Person Data Based on ID.........");
		Person person = personServiceImpl.getPersonById(Id);
		if (person == null) {
			throw new APP_NotFoundException("Requested PersonId Is not Present");
		} else {
			return new ResponseEntity<Person>(person, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Person", notes = "Getting Person Details Based on PersonName", response = Person.class)
	@GetMapping(path = "/getPersonByName/{Name}")
	public ResponseEntity<Person> getPersonByName(@PathVariable(value = "Name") String Name)
			throws APP_NotFoundException {
		System.out.println("Getting Person Data Based on Name.........");
		Person person = personServiceImpl.getPersonByName(Name);
		if (person == null) {
			throw new APP_NotFoundException("Requested PersonId Is not Present");
		} else {
			return new ResponseEntity<Person>(person, HttpStatus.OK);
		}
	}

	@PostMapping(path = "/updatePersonDetails{id}")
	public ResponseEntity<Person> updatePersonDetails(@RequestBody Person person) {
		return null;
	}

	@ApiOperation(value = "Person", notes = "Deleting Person Details Based on PersonID", response = Person.class)
	@DeleteMapping(path = "/deletePersonDetails/{Id}")
	public ResponseEntity<String> deletePersonById(@PathVariable(value = "Id") Integer Id)
			throws APP_NotFoundException {
		personServiceImpl.deletePersonById(Id);
		return new ResponseEntity<String>("Sussfully Deleted", HttpStatus.OK);
	}

	@GetMapping(path = "/getPersonDataInCSV")
	public void getPersonDataInCsvFormate(HttpServletResponse response)
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		// set file name and content type
		String filename = "Person.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

		// create a csv writer
		StatefulBeanToCsv<Person> writer = new StatefulBeanToCsvBuilder<Person>(response.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
				.withOrderedResults(false).build();

		// write all users to csv file
		writer.write(personServiceImpl.getAllPersons());
	}

	@GetMapping(path = "/getPersonDataInExcel")
	public void getPersonDataInExcel() throws IOException {

		// creating workbook means creating excel file
		// XSSFworkbook is for Generating .xls files
		Workbook workbook = new XSSFWorkbook();

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createhelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet("Persons");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.AUTOMATIC.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createhelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < getHeaderValues().length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(getHeaderValues()[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Other rows and cells with employees data
		int rowNum = 1;
		int i = 0;
		for (Person person : personServiceImpl.getAllPersons()) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(person.getPersonId());
			row.createCell(1).setCellValue(person.getFirstName());
			row.createCell(2).setCellValue(person.getLastName());
			row.createCell(3).setCellValue(person.getEmailIid());
			row.createCell(4).setCellValue((person.getAddress()).get(i).getHous_no());
			row.createCell(5).setCellValue((person.getAddress()).get(i).getLand_mark());
			row.createCell(6).setCellValue((person.getAddress()).get(i).getCity());
			row.createCell(7).setCellValue((person.getAddress()).get(i).getState());
			row.createCell(8).setCellValue((person.getAddress()).get(i).getPin_code());

			Cell dateOfBirthCell = row.createCell(9);
			dateOfBirthCell.setCellValue(person.getDateTime());
			dateOfBirthCell.setCellStyle(dateCellStyle);

		}
		
		FileOutputStream fileOut = new FileOutputStream("PersonsData.xlsx");
        workbook.write(fileOut);
        fileOut.close();

	}

	private String[] getHeaderValues() {
		return new String[] { "personId", "firstName", "lastName", "emailIid", "hous_no", "land_mark", "city", "state",
				"pin_code", "Added Date" };
	}

}
