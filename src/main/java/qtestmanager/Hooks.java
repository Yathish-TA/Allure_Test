package qtestmanager;

import io.cucumber.java.Scenario;

import java.util.HashMap;
import java.util.Map;

public class Hooks {

    private static final Map<String, String> scenarioResults = new HashMap<>();

    public static void collectScenarioStatus(Scenario scenario) {
        String status = scenario.isFailed() ? "FAIL" : "PASS";
        String tags = String.join(", ", scenario.getSourceTagNames());
        scenarioResults.put(tags, status);
    }

    public static Map<String, String> getScenarioResults() {
        return scenarioResults;
    }

}
