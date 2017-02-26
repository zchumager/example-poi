package net.me.dev.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DatapoolReader {
	private String workingDirectory;
	private String datapoolFileName;
	private String datapoolFilePath;
	private FileInputStream 
		datapoolFileInputStream;
	private XSSFWorkbook workbook;
	private int numberOfSheets;
	private HashMap<String, XSSFSheet> sheets;
	private XSSFSheet currentSheet;
	private HashMap<String, ArrayList<Row>> 
		currentArraySheet;
	private ArrayList<Row> currentSheetHeaders;
	private ArrayList<Row> currentSheetRows;
	private int numberOfRows;
	private int numberOfRecords;
	
	public DatapoolReader(
			String workingDirectory
			, String datapoolFileName) {
		
		this.workingDirectory =
				workingDirectory;
		this.datapoolFileName =
				datapoolFileName;
		this.datapoolFilePath =
				this.workingDirectory
				+ File.separator 
				+ this.datapoolFileName;
		
		System.out.println("**************"
				+ "***********************");
		System.out.println("Working Directory: " 
				+ this.workingDirectory);
		System.out.println("File full path: " 
				+ this.datapoolFilePath);
	}
	
	public String getWorkingDirectory() {
		return this.workingDirectory;
	}
	
	public String getDatapoolFullPath() {
		return this.datapoolFilePath;
	}
	
	private XSSFWorkbook loadWorbook(
			String datapoolFilePath
			, FileInputStream 
				datapoolFileInputStream)
					throws IOException {
		
		datapoolFileInputStream =
				new FileInputStream(
						new File(datapoolFilePath));
		XSSFWorkbook workbook =
				new XSSFWorkbook(datapoolFileInputStream);
		System.out.println("**************"
				+ "***********************");
		System.out.println("The Workbook has been loaded");
		
		return workbook;
	}
	
	private HashMap<String, XSSFSheet> 
		loadAllSheetsFromWorkBook(
			XSSFWorkbook workbook) {
		
		this.numberOfSheets = 
				workbook.getNumberOfSheets();
		System.out.println("**************"
				+ "***********************");
		System.out.println("The Workbook contains: " 
				+ this.numberOfSheets);
		
		HashMap<String, XSSFSheet> sheets = 
				new HashMap<String, XSSFSheet>();
		
		System.out.println("**************"
				+ "***********************");
		for(int i=0 ; i<this.numberOfSheets; i++) {
			sheets.put(
					workbook.getSheetAt(i).getSheetName()
					,workbook.getSheetAt(i));
			System.out.println("sheet: " 
					+ workbook.getSheetAt(i).getSheetName()
					+ "has been loaded");
		}
		return sheets;
	}
	
	private XSSFSheet setCurrentSheetByName(
			String sheetName
			, HashMap<String, XSSFSheet> sheets) {
		return sheets.get(sheetName);
	}
	
	private void currentSheetAsArrayList() {
		Iterator<Row> rowsIterator =
				this.currentSheet.iterator();
		this.currentArraySheet = 
				new HashMap<String, ArrayList<Row>>();
		this.currentSheetHeaders = new ArrayList<Row>();
		this.currentSheetRows = new ArrayList<Row>();
		
		System.out.println("**************"
				+ "***********************");
		while(rowsIterator.hasNext()) {
			Row currentRow = rowsIterator.next();
			Iterator<Cell> cellsIterator = currentRow.iterator();
			this.numberOfRows++;
			
			while(cellsIterator.hasNext()) {
				Cell currentCell = cellsIterator.next();
				if(currentCell.getCellType()
						== Cell.CELL_TYPE_BLANK) {
					this.currentArraySheet.put("Headers"
							, this.currentSheetHeaders);
					this.currentArraySheet.put("Rows"
							, this.currentSheetRows);
					return;
				}
				if(currentCell.getCellType() 
						== Cell.CELL_TYPE_STRING) {
					System.out.print(
							currentCell.getStringCellValue() + " | ");
				}
				if(currentCell.getCellType() 
						== Cell.CELL_TYPE_NUMERIC) {
					System.out.print(
							currentCell.getNumericCellValue() + " | ");
				}
				if(currentCell.getCellType() 
						== Cell.CELL_TYPE_BOOLEAN) {
					System.out.print(
							currentCell.getBooleanCellValue() + " | ");
				}
			}
			if(this.numberOfRows > 1) {
				this.currentSheetRows.add(currentRow);
				this.numberOfRecords ++;
			} else {
				this.currentSheetHeaders.add(currentRow);
			}
			System.out.println();
		}
	}
	
	public void openSheet(String sheetName) throws IOException {
		this.workbook = this.loadWorbook(
				this.datapoolFilePath, this.datapoolFileInputStream);
		this.sheets = this.loadAllSheetsFromWorkBook(this.workbook);
		this.currentSheet = this.setCurrentSheetByName(
				sheetName, this.sheets);
		this.currentSheetAsArrayList();
	}
	
	public int getNumberOfRecords() {
		return this.numberOfRecords;
	}
	
	public HashMap<String, ArrayList<Row>> getCurrentArraySheet() {
		return this.currentArraySheet;
	}
	
	public ArrayList<Row> getCurrentArraySheetHeaders() {
		return this.currentSheetHeaders;
	}
	
	public ArrayList<Row> getCurrentArraySheetRows() {
		return this.currentSheetRows;
	}
	
	public String getHostAtRow(int rowIndex) {
		String host = "";
		host = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.HOST.getValue())
				.getStringCellValue();
		System.out.println("Host:" + host);
		return host;
	}
	
	public String getUserNameAtRow(int rowIndex) {
		String username = "";
		username = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.USERNAME.getValue())
				.getStringCellValue();
		System.out.println("Username:" + username);
		return username;
	}
	
	public String getPasswordAtRow(int rowIndex) {
		String password = "";
		password = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.PASSWORD.getValue())
				.getStringCellValue();
		System.out.println("Password:" + password);
		return password;
	}
	
	public String getCommandAtRow(int rowIndex) {
		String command = "";
		command = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.COMMAND.getValue())
				.getStringCellValue();
		System.out.println("Command:" + command);
		return command;
	}
	
	public boolean getExecutionCommandFlagAtRow(int rowIndex) {
		boolean executionCommandFlag = false;
		executionCommandFlag = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.EXECUTE_COMMAND_FLAG.getValue())
				.getBooleanCellValue();
		System.out.println("Execution Command Flag:" + executionCommandFlag);
		return executionCommandFlag;
	}
	
	public String getSourceEnvironmentIdAtRow(int rowIndex) {
		String sourceEnvironmentID = "";
		sourceEnvironmentID = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.SOURCE_ENVIRONMENT_ID.getValue())
				.getStringCellValue();
		System.out.println("Source Environment ID:" + sourceEnvironmentID);
		return sourceEnvironmentID;
	}
	
	public String getSourceInputTypeAtRow(int rowIndex) {
		String sourceInputType = "";
		sourceInputType = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.SOURCE_INPUT_TYPE.getValue())
				.getStringCellValue();
		System.out.println("Source Input Type:" + sourceInputType);
		return sourceInputType;
	}
	
	public String getSourceTableOrQueryAtRow(int rowIndex) {
		String sourceTableOrQuery = "";
		sourceTableOrQuery = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.SOURCE_TABLENAME_OR_QUERY.getValue())
				.getStringCellValue();
		System.out.println("Source Table Or Query:" + sourceTableOrQuery);
		return sourceTableOrQuery;
	}
	
	public String getSourcePrimaryKeyAtRow(int rowIndex) {
		String sourcePrimaryKey = "";
		sourcePrimaryKey = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.SOURCE_PRIMARY_KEY.getValue())
				.getStringCellValue();
		System.out.println("Source Primary Key:" + sourcePrimaryKey);
		return sourcePrimaryKey;
	}
	
	public String getTargetEnvironmentIdAtRow(int rowIndex) {
		String targetEnvironmentID = "";
		targetEnvironmentID = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.TARGET_ENVIRONMENT_ID.getValue())
				.getStringCellValue();
		System.out.println("Target Environment ID:" + targetEnvironmentID);
		return targetEnvironmentID;
	}
	
	public String getTargetInputTypeAtRow(int rowIndex) {
		String targetInputType = "";
		targetInputType = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.TARGET_INPUT_TYPE.getValue())
				.getStringCellValue();
		System.out.println("Target Input Type:" + targetInputType);
		return targetInputType;
	}
	
	public String getTargetTableOrQueryAtRow(int rowIndex) {
		String targetTableOrQuery = "";
		targetTableOrQuery = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.TARGET_TABLENAME_OR_QUERY.getValue())
				.getStringCellValue();
		System.out.println("Target Table Or Query:" + targetTableOrQuery);
		return targetTableOrQuery;
	}
	
	public String getTargetPrimaryKeyAtRow(int rowIndex) {
		String targetPrimaryKey = "";
		targetPrimaryKey = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.TARGET_PRIMARY_KEY.getValue())
				.getStringCellValue();
		System.out.println("Target Primary Key:" + targetPrimaryKey);
		return targetPrimaryKey;
	}
	
	public boolean getComparisonFlagAt(int rowIndex) {
		boolean comparisonFlag = false;
		comparisonFlag = this.currentArraySheet.get("Rows").get(rowIndex)
				.getCell(DATAPOOL_FIELD.COMPARISON_FLAG.getValue())
				.getBooleanCellValue();
		return comparisonFlag;
	}
	
	public HashMap<String, String> getRowMap(int rowIndex) {
		HashMap<String, String> rowMap = new HashMap<String, String>();
		
		rowMap.put("host", this.getHostAtRow(rowIndex));
		rowMap.put("serverUsername", this.getUserNameAtRow(rowIndex));
		rowMap.put("ServerPassword", this.getPasswordAtRow(rowIndex));
		rowMap.put("command", this.getCommandAtRow(rowIndex));
		rowMap.put("executionCommandFlag"
				, Boolean.toString(
						this.getExecutionCommandFlagAtRow(rowIndex)));
		rowMap.put("sourceEnvironmentId"
				, this.getSourceEnvironmentIdAtRow(rowIndex));
		rowMap.put("sourceInputType"
				, this.getSourceInputTypeAtRow(rowIndex));
		rowMap.put("sourceTableOrQuery"
				, this.getSourceTableOrQueryAtRow(rowIndex));
		rowMap.put("sourcePrimaryKey"
				, this.getSourcePrimaryKeyAtRow(rowIndex));
		rowMap.put("targetEnvironmentId"
				, this.getTargetEnvironmentIdAtRow(rowIndex));
		rowMap.put("targetInputType"
				, this.getTargetInputTypeAtRow(rowIndex));
		rowMap.put("targetTableOrQuery"
				, this.getTargetTableOrQueryAtRow(rowIndex));
		rowMap.put("targetPrimaryKey"
				, this.getTargetPrimaryKeyAtRow(rowIndex));
		rowMap.put("comparisonFlag"
				, Boolean.toString(
						this.getComparisonFlagAt(rowIndex)));
		return rowMap;
	}
	
	public void closeResources() {
		try {
			this.workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}