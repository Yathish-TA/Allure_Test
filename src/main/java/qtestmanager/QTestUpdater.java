package qtestmanager;

import azurevault.VaultData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QTestUpdater {

    private static final String QTEST_URL = "https://tractorsupply.qtestnet.com/api/v3/projects/";
    private static final String API_TOKEN = VaultData.getVaultData("qtest-api-token");
    private static final String PROJECT_NAME = System.getProperty("projectName", "Sample Project");
    private static final String TESTSUITE_ID_AND_NAME = System.getProperty("testSuiteIdAndName", "TS-155 One Platform - Java");
    private static final List<Integer> testRunIds = new ArrayList<>();
    private static int projectID;

    public static void updateQTestStatus() {
        Map<String, String> scenarioResults = Hooks.getScenarioResults();
        System.out.println("ScenarioResults - " + scenarioResults.toString());
        for (Map.Entry<String, String> entry : scenarioResults.entrySet()) {
            String tagName = entry.getKey();
            String status = entry.getValue();
            projectID = getProjectId(PROJECT_NAME);
            updateQTestResult(TESTSUITE_ID_AND_NAME, projectID, tagName, status);
        }
    }

    public static void uploadReportToQtest(String filePath) {
        uploadReportForFirstTestRun(projectID, filePath);
    }

    public static int getProjectId(String projectName) {
        try {
            URL url = new URL(QTEST_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Handle empty or invalid JSON
            if (response.length() == 0) {
                System.out.println("Empty response from API.");
                return -1;
            }
            // Check if response starts with [
            if (response.toString().startsWith("[")) {
                JSONArray jsonResponse = new JSONArray(response.toString());
                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject testRun = jsonResponse.getJSONObject(i);
                    String name = testRun.getString("name");
                    if (name.contains(projectName)) {
                        return testRun.getInt("id");
                    }
                }
            } else {
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray testRuns = jsonResponse.getJSONArray("items");
                for (int i = 0; i < testRuns.length(); i++) {
                    JSONObject testRun = testRuns.getJSONObject(i);
                    String name = testRun.getString("name");
                    if (name.contains(projectName)) {
                        return testRun.getInt("id");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if no match is found
    }

    public static void updateQTestResult(String testSuiteName, int projectId, String tagName, String status) {
        try {
            URL url = new URL(QTEST_URL + projectId + "/search");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            // Correct JSON payload
            String payload = "{"
                    + "\"object_type\": \"test-runs\","
                    + "\"fields\": [\"testCaseId\",\"id\",\"pid\"],"
                    + "\"query\": \"'Test Suite' = '" + testSuiteName + "'\""
                    + "}";
            // Write payload to request body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            // Read the response properly
            int statusCode = conn.getResponseCode();
            InputStream inputStream = (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Parse response as JSON object
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray testRuns = jsonResponse.getJSONArray("items");
            for (int i = 0; i < testRuns.length(); i++) {
                JSONObject testRun = testRuns.getJSONObject(i);
                int testCaseId = testRun.getInt("testCaseId");
                int testRunId = testRun.getInt("id");
                // Fetch Test Case Details
                JSONObject testCaseDetails = getTestCaseDetails(projectId, testCaseId);
                if (testCaseDetails != null) {
                    String pid = testCaseDetails.getString("pid");
                    String[] tags = tagName.split(",");
                    for (String tag : tags) {
                        String cleanTag = tag.replaceAll("@", "").trim();
                        if (cleanTag.equals(pid)) {
                            System.out.println("Matching Test Case found: " + pid);
                            sendTestResultToQTest(projectId, testRunId, status);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get Test Case Details by testCaseId
    public static JSONObject getTestCaseDetails(int projectId, int testCaseId) {
        try {
            String urlString = QTEST_URL + projectId + "/test-cases/" + testCaseId;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return new JSONObject(response.toString());
            } else {
                System.err.println("Failed to get Test Case details for ID: " + testCaseId);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void sendTestResultToQTest(int projectId, int testRunId, String status) {
        String dateAndTime = Instant.now()
                .atZone(ZoneId.of("UTC"))
                .truncatedTo(java.time.temporal.ChronoUnit.SECONDS) // Remove nanoseconds
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        try {
            URL url = new URL(QTEST_URL + projectId + "/test-runs/" + testRunId + "/auto-test-logs");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String payload = "{"
                    + "\"status\": \"" + status + "\","
                    + "\"exe_start_date\": \"" + dateAndTime + "\","
                    + "\"exe_end_date\": \"" + dateAndTime + "\","
                    + "\"name\": \"Execution from Automation\""
                    + "}";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode - " + responseCode);
            if (responseCode == 200 || responseCode == 201) {
                System.out.println("Updated TestRun ID " + testRunId + " with status: " + status);
            } else {
                System.out.println("Failed to update testUpdater for TestRun ID " + testRunId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllTestRuns(String testSuiteName, int projectId) {
        try {
            URL url = new URL(QTEST_URL + projectId + "/search");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            // Correct JSON payload
            String payload = "{"
                    + "\"object_type\": \"test-runs\","
                    + "\"fields\": [\"id\"],"
                    + "\"query\": \"'Test Suite' = '" + testSuiteName + "'\""
                    + "}";
            // Write payload to request body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            // Read the response properly
            int statusCode = conn.getResponseCode();
            InputStream inputStream = (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Parse response as JSON object
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray testRuns = jsonResponse.getJSONArray("items");
            for (int i = 0; i < testRuns.length(); i++) {
                JSONObject testRun = testRuns.getJSONObject(i);
                int testRunId = testRun.getInt("id");
                // Store the testRunId in the list
                testRunIds.add(testRunId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uploadReportForFirstTestRun(int projectID, String filePath) {
        //Loads all the test runs present under test suites into a List 'testRunIds'
        getAllTestRuns(TESTSUITE_ID_AND_NAME, projectID);
        int testRunId = testRunIds.getFirst();
        System.out.println("testRunId = " + testRunId);
        try {
            URL url = new URL(QTEST_URL + projectID + "/test-runs/" + testRunId + "/test-logs");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Parse as JSONObject
            JSONObject jsonResponse = new JSONObject(response.toString());
            // Extract the array of test runs (typically in 'items' or 'data')
            JSONArray items = jsonResponse.getJSONArray("items");
            if (!items.isEmpty()) {
                int firstLogId = items.getJSONObject(0).getInt("id");
                Thread.sleep(5000);
                // Step 2: Upload HTML Report to the First Test Log
                uploadHtmlReport(projectID, firstLogId, filePath);
            } else {
                System.out.println("No test logs found for Test Run ID: " + testRunId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uploadHtmlReport(int projectId, int testLogId, String filePath) {
        try {
            File file = new File(filePath);
            URL url = new URL(QTEST_URL + projectId + "/test-logs/" + testLogId + "/blob-handles");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
            conn.setRequestProperty("Content-Type", "text/html");
            conn.setRequestProperty("File-Name", file.getName());  // Add File-Name header
            try (OutputStream outputStream = conn.getOutputStream();
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true)) {
                // Write the file content
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                }
                writer.append("\r\n");
                writer.flush();
            }
            int responseCode = conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (responseCode == 201) {
                System.out.println("Attachment uploaded successfully: " + response.toString());
            } else {
                System.out.println("Failed to upload attachment: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
