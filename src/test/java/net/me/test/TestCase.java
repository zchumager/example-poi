package net.me.test;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import org.testng.annotations.Test;
import net.me.dev.utils.DatapoolReader;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCase {
	@Test
	public void testDatapoolReader() {
		String datapoolFileName = "datapool.xlsx";

		URL resource = getClass().getClassLoader().getResource(datapoolFileName);

		String sheetName = "Hoja1";
		DatapoolReader dpr = 
				new DatapoolReader(resource.getPath());
		try {
			dpr.openSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int index = 0;
		System.out.println("****************************************");
		System.out.println("Row at index " + index);
		HashMap<String, String> rowMap = dpr.getRowMap(index);

		assertThat(rowMap.get("comparisonFlag")).isEqualTo("true");
		assertThat(rowMap.get("targetEnvironmentId")).isEqualTo("target_db");
		assertThat(rowMap.get("serverUsername")).isEqualTo("Bar");
		assertThat(rowMap.get("targetInputType")).isEqualTo("sql");
		assertThat(rowMap.get("sourceEnvironmentId")).isEqualTo("source_db");
		assertThat(rowMap.get("executionCommandFlag")).isEqualTo("false");
		assertThat(rowMap.get("targetPrimaryKey")).isEqualTo("ID_LOAN_DWH");
		assertThat(rowMap.get("command")).isEqualTo("ls > listOfFiles.txt");
		assertThat(rowMap.get("targetTableOrQuery")).isEqualTo("target_db_1.sql");
		assertThat(rowMap.get("host")).isEqualTo("FooServer");
		assertThat(rowMap.get("sourceTableOrQuery")).isEqualTo("source_db_1.sql");
		assertThat(rowMap.get("sourcePrimaryKey")).isEqualTo("ID_LOAN");
		assertThat(rowMap.get("sourceInputType")).isEqualTo("sql");
	}
}
