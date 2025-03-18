package apicoreutils;

import helperfunctions.ExcelDataProvider;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReadApiTestData {
    private static final Logger log = LogManager.getLogger(ExcelDataProvider.class);
    private static final String FEATURE_FILE = "src/test/java/api/resources/features/api-run.feature";

    private static final String DIR = "src/test/java/api/resources/testdata";

    public List<Map<String, Object>> readExcelData() throws IOException {
        List<Map<String, Object>> testData = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        ReadExamples readExamples = new ReadExamples();
        Map<String, String> testDataValues = readExamples.extractTestDataValues(FEATURE_FILE);
        String excelFilePath = testDataValues.get("defineExcelPath");
        String sheetName = testDataValues.get("definesheet");
        File file = new File(Paths.get(DIR, excelFilePath).toString());

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            boolean executeColumnFound = false;

            for (Row row : sheet) {
                Cell firstCell = row.getCell(0);
                if (firstCell != null && "Execute?".equalsIgnoreCase(firstCell.getStringCellValue())) {
                    headers = readHeaderRow(row);
                    executeColumnFound = true;
                } else if (executeColumnFound) { // Skip rows until Execute? column is found
                    values = readDataRow(row);
                    if (!values.isEmpty() && isValidTestCase(values)) {
                        Map<String, Object> dataMap = createDataMap(headers, values);
                        testData.add(dataMap);
                    }
                }
            }
        }

        return testData;
    }

    private boolean isValidTestCase(List<Object> values) {
        String executeValue = values.get(0).toString().toUpperCase();
//        String testTypeValue = values.get(2).toString().toUpperCase();
        //&& testTypeValue.contains(TEST_TYPE.toUpperCase()
        return ("Y".equals(executeValue) || "AUTH".equals(executeValue));
    }


    private List<String> readHeaderRow(Row row) {
        List<String> headers = new ArrayList<>();
        for (Cell cell : row) {
            headers.add(cell.getStringCellValue());
        }
        return headers;
    }

    private List<Object> readDataRow(Row row) {
        List<Object> values = new ArrayList<>();
        for (Cell cell : row) {
            values.add(getCellValue(cell));
        }
        return values;
    }

    private Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        if (CellType.NUMERIC.equals(cellType)) {
            return cell.getNumericCellValue();
        } else if (CellType.STRING.equals(cellType)) {
            return cell.getStringCellValue();
        } else if (CellType.BOOLEAN.equals(cellType)) {
            return cell.getBooleanCellValue();
        } else if (cellType == null || CellType.BLANK.equals(cellType)) {
            return "";
        } else {
            // Handle other cell types if needed
            return null;
        }
    }

    private Map<String, Object> createDataMap(List<String> headers, List<Object> values) {
        Map<String, Object> dataMap = new HashMap<>();
        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            Object value = values.get(i);
            dataMap.put(header, value);
        }
        return dataMap;
    }

}
