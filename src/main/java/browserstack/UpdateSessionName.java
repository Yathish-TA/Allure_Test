package browserstack;

import org.openqa.selenium.remote.SessionId;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import static azurevault.VaultData.getVaultData;

public class UpdateSessionName {

    public static void updateBS_SessionName(SessionId sessionID, String sessionName, String BS_Type) {
        try {
            String urlString = null;
            if(BS_Type.equals("App Automate")){
                 urlString = "https://api.browserstack.com/app-automate/sessions/" + sessionID.toString() + ".json";
            }else{
                 urlString = "https://api.browserstack.com/automate/sessions/" + sessionID.toString() + ".json";
            }
            // Construct the URL
            URL url = new URL(urlString);

            // Create HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");

            // Set the Basic Auth credentials
            String encodedCredentials = Base64.getEncoder().encodeToString((getVaultData("BROWSERSTACK-USER") + ":" + getVaultData("BROWSERSTACK-PASSWORD")).getBytes(StandardCharsets.UTF_8));
            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);

            // Create JSON payload
            String jsonInputString = "{\"name\":\"" + sessionName + "\"}";

            // Send the request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get the response
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode +" "+connection.getResponseMessage());

            // Optionally, you can read the response here

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
