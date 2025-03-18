package apicoreutils;

import helperfunctions.CommonUtils;
import io.qameta.allure.Allure;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pojo.CommonPojo;
import reports.CustomHtmlReport;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExcelDatafetcher extends CustomHtmlReport {
    private static List<Map<String, Object>> testData;
    private static final ReadApiTestData readApiTestData = new ReadApiTestData();
    private static final Logger log = LogManager.getLogger(helperfunctions.ExcelDataProvider.class);
    private static ApiCoreModel apiCoreModel = new ApiCoreModel();
    public ExcelDatafetcher() {
        this.commonPojo = new CommonPojo();
    }

    public static int headerIteration(XSSFSheet sheet, int row, List<String> headers) {
        if (!headers.isEmpty()) {
            headers.clear();
        }
        int columnCount = sheet.getRow(row).getLastCellNum();
        for (int j = 0; j < columnCount; j++) {
            String cellData = sheet.getRow(row).getCell(j).getStringCellValue();
            headers.add(cellData);
        }
        return columnCount;
    }

    public static void readCellData(CellType cellType, Cell cell, List<Object> values) {
        if (cellType == CellType.NUMERIC) {
            double numericValue = cell.getNumericCellValue();
            if (numericValue == Math.floor(numericValue)) {
                // It's a whole number, so cast to an integer to remove the decimal part
                int integerValue = (int) numericValue;
                values.add(String.valueOf(integerValue));
            } else {
                // It's a decimal value, keep it as a string
                String numberValue = String.valueOf(numericValue);
                values.add(numberValue);
            }

        } else
            if (cellType == CellType.STRING) {
            String cellValue = cell.getStringCellValue();
            if (cellValue.contains("(") && cellValue.contains("char)") || cellValue.contains("(") && cellValue.contains("digit)")) {

                String reqStr = StringUtils.substringBetween(cellValue, "(", ")");
                int reqLength = Integer.parseInt(reqStr.replaceAll("[^0-9]", ""));

                String randomStr;
                if (reqStr.toLowerCase().contains("digit")) {
                    randomStr = String.valueOf(CommonUtils.generateRandomNumber(reqLength));
                } else {
                    randomStr = CommonUtils.generateRandomString(reqLength);
                }
                cellValue = cellValue.replace("(" + reqStr + ")", randomStr);
            }

            values.add(cellValue);
        } else if (cellType == CellType.BOOLEAN) {
            boolean cellValue = cell.getBooleanCellValue();
            values.add(cellValue);
        } else if (cellType == null || cellType == CellType.BLANK) {
            values.add("");
        }
    }

    public static List<Map<String, Object>> readExcel(String Workbook, String Worksheet ) {

        String excelWorkBookPath = "src//test//java//api//resources//testdata//"+Workbook;
        String excelSheetName = Worksheet;

        List<Map<String, Object>> testData = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(excelWorkBookPath)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(excelSheetName);
            int rowCount = sheet.getLastRowNum();
            int columnCount = 0;
            for (int i = 0; i <= rowCount; i++) {
                if (i%2 == 0){
                    columnCount = headerIteration(sheet, i, headers);
                }
                  else{
                      if (!values.isEmpty()) {
                        values.clear();
                    }
                    for (int j = 0; j < columnCount; j++) {
                        Cell cell = sheet.getRow(i).getCell(j);
                        CellType cellType = cell != null ? cell.getCellType() : CellType.BLANK;
                        readCellData(cellType, cell, values);
                    }
                }
                if (headers.size() == values.size()) {
                    // Use LinkedHashMap to maintain the insertion order
                    Map<String, Object> rowData = IntStream.range(0, headers.size()).boxed()
                            .collect(Collectors.toMap(
                                    headers::get,
                                    values::get,
                                    (existing, replacement) -> existing,
                                    LinkedHashMap::new // Specify LinkedHashMap here
                            ));
                    testData.add(rowData);
                    values.clear();
                }
            }
        } catch (IOException e) {
            log.info("The Excel Sheet provided is not correct:");
        }
        return testData;
    }

    public void loadDataIntoMethods_Itr() throws IOException {
        System.out.println("In 1st given step definitions");
        // Placeholder method for Cucumber
        if (testData == null) {
            testData = readApiTestData.readExcelData();
            for (Map<String, Object> dataMap : testData) {
                String testCaseName = (String) dataMap.get("Test_Case_Name");
                Map<String, Object> processedData = apiCoreModel.replaceFetchValues(dataMap);
                Allure.addAttachment("Prerequisite Raw Data for " + testCaseName, processedData.toString());
                System.out.println("Prepared Test Case: " + testCaseName);
            }
        }
    }
    public static List<Map<String, Object>> getTestData() {
        return testData;
    }

    public static List<Map<String, Object>> getExcelData(String dataref,String Workbook, String Worksheet ) {
        Collection<Object[]> testData = new ArrayList<>();
        List<Map<String, Object>> tdList = readExcel(Workbook,Worksheet);
        for (Map<String, Object> map : tdList) {
            if (map.isEmpty()) {
                log.info("Empty Test Data is provided");
            } else if (map.get("Execute?").toString().equalsIgnoreCase("No")) {
                log.info("The Execute Flag is marked as No for the TestCase:");
            } else if (map.get("Execute?").toString().equalsIgnoreCase("Yes") && map.get("DataBinding").toString().equalsIgnoreCase(dataref)) {
                testData.add(new Object[]{map});
            }
        }
        // Printing testData
        System.out.println("testData:");
        for (Object[] data : testData) {
            System.out.print("[");
            for (Object obj : data) {
                System.out.print(obj + " ");
            }
            System.out.println("]");
        }
        return convertIteratorToListOfMap(testData.iterator());
    }
    public static List<Map<String, Object>> getExcelData(String Workbook, String Worksheet ) {
        Collection<Object[]> testData = new ArrayList<>();
        List<Map<String, Object>> tdList = readExcel(Workbook,Worksheet);
        for (Map<String, Object> map : tdList) {
            if (map.isEmpty()) {
                log.info("Empty Test Data is provided");
            } else{testData.add(new Object[]{map});
            }
        }
        return convertIteratorToListOfMap(testData.iterator());
    }
    public static Map<String, Object> convertIteratorToMap(Iterator<Object[]> iterator) {
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println("Boolean Result: " + iterator.hasNext());
        while (iterator.hasNext()) {
            Object[] testData = iterator.next();
            if (testData.length > 0 && testData[0] instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> dataMap = (Map<String, Object>) testData[0];
                System.out.println(dataMap);
                resultMap=dataMap;
            }
        }
        return apiCoreModel.replaceFetchValues(resultMap);
    }
    public static List<Map<String, Object>> convertIteratorToListOfMap(Iterator<Object[]> iterator) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        // Iterate through the test data from the iterator
        while (iterator.hasNext()) {
            Object[] testData = iterator.next();
            Map<String, Object> dataMap = new LinkedHashMap<>();

            // Process each element in the Object[] (which can be a Map)
            for (Object obj : testData) {
                if (obj instanceof Map) {
                    // If it's a Map, process it directly
                    Map<String, Object> mapData = (Map<String, Object>) obj;
                    dataMap.putAll(mapData);  // Merge the map into dataMap
                } else if (obj instanceof String) {
                    // If it's a String, split it into key-value pair
                    String[] keyValue = ((String) obj).split("=", 2);
                    if (keyValue.length == 2) {
                        // Add the key-value pair to the map as an Object
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                } else {
                    // Optionally handle other types if needed (e.g., Integer, Boolean, etc.)
                    dataMap.put(obj.getClass().getName(), obj);
                }
            }

            // Add the populated map to the result list
            resultList.add(dataMap);
        }
        // Return the final list of maps
        return resultList;
//        return apiCoreModel.replaceFetchValues(resultList);
    }

}
