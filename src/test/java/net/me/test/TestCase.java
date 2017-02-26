package net.me.test;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import net.me.dev.utils.DatapoolReader;

public class TestCase {
	@Test
	public void testDatapoolReader() {
		String workingDirectory = 
				"C:\\Users\\Zchumager\\Software Development\\"
				+ "development eclipse workspaces\\java ee\\"
				+ "example-poi\\src\\main\\resources";
		
		String datapoolFileName = "datapool.xlsx";
		String sheetName = "Hoja1";
		DatapoolReader dpr = 
				new DatapoolReader(workingDirectory, datapoolFileName);
		try {
			dpr.openSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int index = 0;
		System.out.println("****************************************");
		System.out.println("Row at index " + index);
		HashMap<String, String> rowMap = dpr.getRowMap(index);
	}
}
