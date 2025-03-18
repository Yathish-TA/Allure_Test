package api.utils;

import allurereportgeneration.AllureReportGeneration;
import apicoreutils.ApiCoreModel;
import apicoreutils.ExcelDatafetcher;
import apicoreutils.ReadApiTestData;
import database.DBC;
import org.json.JSONObject;

import java.util.*;

import static azurevault.VaultData.vaultConnect;

public class TestBaseApi {

    protected static final Map<String, List<Map<String, Object>>> featureDataMap = new LinkedHashMap<>();

    protected List<Map<String, Object>> datavalues = new ArrayList<>();
    protected List<JSONObject> responseJsonList = new ArrayList<>();
    protected static final ReadApiTestData readApiTestData = new ReadApiTestData();
//    protected static String Workbook;
//    protected static String Worksheet;
    protected ExcelDatafetcher excelDatafetcher=new ExcelDatafetcher();
    protected static AllureReportGeneration alluresetTestcase = new AllureReportGeneration();
    protected static final ApiCoreModel apiCoreModel = new ApiCoreModel();
    protected static final DBC db = new DBC();

    public TestBaseApi(){
        vaultConnect();
    }
}
