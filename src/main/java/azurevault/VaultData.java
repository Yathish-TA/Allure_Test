package azurevault;

import apicoreutils.ApiCoreModel;
import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.log4j.Logger;

import java.io.File;

public class VaultData {

    private static String clientId;
    private static String tenantId;
    private static String clientSecret;
    private static String vaultName;
    private static SecretClient secretClient;
    private static final Logger logger = Logger.getLogger(VaultData.class);


    public static void vaultConnect() {
        String filePath = ".env";
        File file = new File(filePath);
        logger.info(file.exists()?".env file Exists":".env file is missing");

        String env = System.getenv("ENV");

        if (file.exists()) {
            Dotenv dotenv = Dotenv.load();
            clientId = dotenv.get("AZURE_CLIENT_ID");
            tenantId = dotenv.get("AZURE_TENANT_ID");
            clientSecret = dotenv.get("AZURE_CLIENT_SECRET");
            vaultName = dotenv.get("AZURE_KEY_VAULT_NAME");
        }
        else {
            clientId = System.getenv("AZURE_CLIENT_ID");
            tenantId = System.getenv("AZURE_TENANT_ID");
            clientSecret = System.getenv("AZURE_CLIENT_SECRET");
            vaultName = System.getenv("AZURE_KEY_VAULT_NAME");
        }

        TokenCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        // Construct the Key Vault URL
        String vaultUrl = "https://" + vaultName + ".vault.azure.net/";

        // Build the secret client
        secretClient = new SecretClientBuilder()
                .vaultUrl(vaultUrl)
                .credential(clientSecretCredential)
                .buildClient();

    }

    public static String getVaultData(String secretKey){
        KeyVaultSecret secret = secretClient.getSecret(secretKey);
        return secret.getValue();
    }

}
