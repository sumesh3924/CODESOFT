import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Real-Time Currency Converter Program.
 * This program fetches real-time exchange rates from an external API
 * (like ExchangeRate-API.com) and performs a currency conversion.
 *
 * NOTE: This requires Java 11 or later for the HttpClient.
 */
public class CurrencyConverter {

    // --- Configuration ---

    // IMPORTANT: Replace this placeholder with your actual API key!
    // You can get a free key from services like exchangerate-api.com
    private static final String API_KEY = "import java.io.IOException;\n" + //
                "import java.net.URI;\n" + //
                "import java.net.http.HttpClient;\n" + //
                "import java.net.http.HttpRequest;\n" + //
                "import java.net.http.HttpResponse;\n" + //
                "import java.util.InputMismatchException;\n" + //
                "import java.util.Scanner;\n" + //
                "\n" + //
                "/**\n" + //
                " * Real-Time Currency Converter Program.\n" + //
                " * This program fetches real-time exchange rates from an external API\n" + //
                " * (like ExchangeRate-API.com) and performs a currency conversion.\n" + //
                " *\n" + //
                " * NOTE: This requires Java 11 or later for the HttpClient.\n" + //
                " */\n" + //
                "public class CurrencyConverter {\n" + //
                "\n" + //
                "    // --- Configuration ---\n" + //
                "\n" + //
                "    // IMPORTANT: Replace this placeholder with your actual API key!\n" + //
                "    // You can get a free key from services like exchangerate-api.com\n" + //
                "    private static final String API_KEY = \"YOUR_API_KEY_HERE\";\n" + //
                "\n" + //
                "    // API URL template: fetches the latest rates based on the chosen base currency.\n" + //
                "    // %s will be replaced by API_KEY and BASE_CURRENCY.\n" + //
                "    private static final String API_URL_TEMPLATE = \n" + //
                "        \"https://v6.exchangerate-api.com/v6/%s/latest/%s\";\n" + //
                "\n" + //
                "    // --- Main Logic ---\n" + //
                "\n" + //
                "    public static void main(String[] args) {\n" + //
                "        // NOTE ON COMPILATION ERROR: If you encounter \"Unresolved compilation problem\" \n" + //
                "        // after modifying the code, please ensure your build system (like your IDE or terminal) \n" + //
                "        // successfully compiles CurrencyConverter.java before running it. This error often \n" + //
                "        // means the compiled class file is stale.\n" + //
                "\n" + //
                "        // Check for placeholder key before starting\n" + //
                "        if (API_KEY.equals(\"YOUR_API_KEY_HERE\")) {\n" + //
                "            System.err.println(\"ERROR: Please replace \\\"YOUR_API_KEY_HERE\\\" with your actual API key in the code.\");\n" + //
                "            System.err.println(\"The program cannot function without a valid key.\");\n" + //
                "            return;\n" + //
                "        }\n" + //
                "\n" + //
                "        Scanner scanner = new Scanner(System.in);\n" + //
                "\n" + //
                "        try {\n" + //
                "            System.out.println(\"--- Real-Time Currency Converter ---\");\n" + //
                "\n" + //
                "            // 1. Currency Selection: Base Currency\n" + //
                "            System.out.print(\"Enter the base currency code (e.g., USD, EUR, GBP): \");\n" + //
                "            String baseCurrency = scanner.next().toUpperCase();\n" + //
                "\n" + //
                "            // 2. Currency Selection: Target Currency\n" + //
                "            System.out.print(\"Enter the target currency code (e.g., JPY, CAD, AUD): \");\n" + //
                "            String targetCurrency = scanner.next().toUpperCase();\n" + //
                "\n" + //
                "            // 3. Amount Input\n" + //
                "            System.out.print(\"Enter the amount to convert: \");\n" + //
                "            double amountToConvert = scanner.nextDouble();\n" + //
                "\n" + //
                "            if (amountToConvert <= 0) {\n" + //
                "                System.out.println(\"Amount must be positive.\");\n" + //
                "                return;\n" + //
                "            }\n" + //
                "\n" + //
                "            System.out.println(\"\\n" + //
                "Fetching exchange rates...\");\n" + //
                "\n" + //
                "            // 4. Fetch Currency Rates\n" + //
                "            double rate = fetchExchangeRate(baseCurrency, targetCurrency);\n" + //
                "\n" + //
                "            if (rate == -1.0) {\n" + //
                "                System.out.println(\"Conversion failed. Check your currency codes and API key.\");\n" + //
                "                return;\n" + //
                "            }\n" + //
                "\n" + //
                "            // 5. Currency Conversion\n" + //
                "            double convertedAmount = amountToConvert * rate;\n" + //
                "\n" + //
                "            // 6. Display Result\n" + //
                "            System.out.println(\"\\n" + //
                "--- Conversion Result ---\");\n" + //
                "            System.out.printf(\"Base Currency: %s\\n" + //
                "\", baseCurrency);\n" + //
                "            System.out.printf(\"Target Currency: %s\\n" + //
                "\", targetCurrency);\n" + //
                "            System.out.printf(\"Exchange Rate (1 %s = %.4f %s)\\n" + //
                "\", baseCurrency, rate, targetCurrency);\n" + //
                "            System.out.printf(\"%.2f %s converts to %.2f %s\\n" + //
                "\", \n" + //
                "                                amountToConvert, baseCurrency, convertedAmount, targetCurrency);\n" + //
                "            System.out.println(\"-------------------------\");\n" + //
                "\n" + //
                "\n" + //
                "        } catch (InputMismatchException e) {\n" + //
                "            System.err.println(\"Invalid input. Please enter valid currency codes and a number for the amount.\");\n" + //
                "        } catch (Exception e) {\n" + //
                "            System.err.println(\"An unexpected error occurred: \" + e.getMessage());\n" + //
                "        } finally {\n" + //
                "            // Ensure the scanner is closed regardless of outcome\n" + //
                "            scanner.close();\n" + //
                "        }\n" + //
                "    }\n" + //
                "\n" + //
                "    /**\n" + //
                "     * Fetches the exchange rate for a target currency relative to a base currency.\n" + //
                "     * @param baseCurrency The base currency code (e.g., USD).\n" + //
                "     * @param targetCurrency The target currency code (e.g., EUR).\n" + //
                "     * @return The exchange rate (1 Base = X Target), or -1.0 if fetching fails.\n" + //
                "     */\n" + //
                "    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) {\n" + //
                "        String apiUrl = String.format(API_URL_TEMPLATE, API_KEY, baseCurrency);\n" + //
                "\n" + //
                "        // HttpClient setup (using the default client)\n" + //
                "        HttpClient client = HttpClient.newHttpClient();\n" + //
                "        HttpRequest request = HttpRequest.newBuilder()\n" + //
                "                .uri(URI.create(apiUrl))\n" + //
                "                .build();\n" + //
                "\n" + //
                "        try {\n" + //
                "            // Send the request and get the JSON response\n" + //
                "            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());\n" + //
                "\n" + //
                "            String jsonResponse = response.body();\n" + //
                "\n" + //
                "            // Check if the API call was successful\n" + //
                "            if (response.statusCode() != 200 || jsonResponse.contains(\"\\\"result\\\":\\\"error\\\"\")) {\n" + //
                "                System.err.println(\"API Request Failed. Status: \" + response.statusCode());\n" + //
                "                // For better debugging, display the full response body if it contains an error message.\n" + //
                "                if (jsonResponse.contains(\"error\")) {\n" + //
                "                     System.err.println(\"API Error Details: \" + jsonResponse);\n" + //
                "                }\n" + //
                "                return -1.0;\n" + //
                "            }\n" + //
                "            \n" + //
                "            // Simplified Manual JSON Parsing\n" + //
                "            // We look for the section: \"TARGET_CURRENCY\":RATE\n" + //
                "            String searchString = \"\\\"\" + targetCurrency + \"\\\":\";\n" + //
                "            int startIndex = jsonResponse.indexOf(searchString);\n" + //
                "\n" + //
                "            if (startIndex == -1) {\n" + //
                "                System.err.println(\"Error: Target currency code not found in API response.\");\n" + //
                "                return -1.0;\n" + //
                "            }\n" + //
                "\n" + //
                "            // Move past the search string to the rate value\n" + //
                "            startIndex += searchString.length();\n" + //
                "            \n" + //
                "            // Find the end of the rate value (where the comma or closing brace is)\n" + //
                "            int endIndex = jsonResponse.indexOf(\",\", startIndex);\n" + //
                "            if (endIndex == -1) {\n" + //
                "                // If it's the last rate in the list, look for the closing curly brace '}'\n" + //
                "                endIndex = jsonResponse.indexOf(\"}\", startIndex);\n" + //
                "            }\n" + //
                "            \n" + //
                "            // Extract the substring containing the rate value\n" + //
                "            String rateString = jsonResponse.substring(startIndex, endIndex).trim();\n" + //
                "\n" + //
                "            // Parse the string to a double\n" + //
                "            return Double.parseDouble(rateString);\n" + //
                "\n" + //
                "        } catch (IOException | InterruptedException e) {\n" + //
                "            System.err.println(\"Network error while connecting to API: \" + e.getMessage());\n" + //
                "            return -1.0;\n" + //
                "        } catch (NumberFormatException e) {\n" + //
                "            System.err.println(\"Failed to parse exchange rate value from API response.\");\n" + //
                "            return -1.0;\n" + //
                "        }\n" + //
                "    }\n" + //
                "}\n" + //
                "";

    // API URL template: fetches the latest rates based on the chosen base currency.
    // %s will be replaced by API_KEY and BASE_CURRENCY.
    private static final String API_URL_TEMPLATE = 
        "https://v6.exchangerate-api.com/v6/%s/latest/%s";

    // --- Main Logic ---

    public static void main(String[] args) {
        // NOTE ON COMPILATION ERROR: If you encounter "Unresolved compilation problem" 
        // after modifying the code, please ensure your build system (like your IDE or terminal) 
        // successfully compiles CurrencyConverter.java before running it. This error often 
        // means the compiled class file is stale.

        // Check for placeholder key before starting
        if (API_KEY.equals("YOUR_API_KEY_HERE")) {
            System.err.println("ERROR: Please replace \"YOUR_API_KEY_HERE\" with your actual API key in the code.");
            System.err.println("The program cannot function without a valid key.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("--- Real-Time Currency Converter ---");

            // 1. Currency Selection: Base Currency
            System.out.print("Enter the base currency code (e.g., USD, EUR, GBP): ");
            String baseCurrency = scanner.next().toUpperCase();

            // 2. Currency Selection: Target Currency
            System.out.print("Enter the target currency code (e.g., JPY, CAD, AUD): ");
            String targetCurrency = scanner.next().toUpperCase();

            // 3. Amount Input
            System.out.print("Enter the amount to convert: ");
            double amountToConvert = scanner.nextDouble();

            if (amountToConvert <= 0) {
                System.out.println("Amount must be positive.");
                return;
            }

            System.out.println("\nFetching exchange rates...");

            // 4. Fetch Currency Rates
            double rate = fetchExchangeRate(baseCurrency, targetCurrency);

            if (rate == -1.0) {
                System.out.println("Conversion failed. Check your currency codes and API key.");
                return;
            }

            // 5. Currency Conversion
            double convertedAmount = amountToConvert * rate;

            // 6. Display Result
            System.out.println("\n--- Conversion Result ---");
            System.out.printf("Base Currency: %s\n", baseCurrency);
            System.out.printf("Target Currency: %s\n", targetCurrency);
            System.out.printf("Exchange Rate (1 %s = %.4f %s)\n", baseCurrency, rate, targetCurrency);
            System.out.printf("%.2f %s converts to %.2f %s\n", 
                                amountToConvert, baseCurrency, convertedAmount, targetCurrency);
            System.out.println("-------------------------");


        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter valid currency codes and a number for the amount.");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            // Ensure the scanner is closed regardless of outcome
            scanner.close();
        }
    }

    /**
     * Fetches the exchange rate for a target currency relative to a base currency.
     * @param baseCurrency The base currency code (e.g., USD).
     * @param targetCurrency The target currency code (e.g., EUR).
     * @return The exchange rate (1 Base = X Target), or -1.0 if fetching fails.
     */
    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        String apiUrl = String.format(API_URL_TEMPLATE, API_KEY, baseCurrency);

        // HttpClient setup (using the default client)
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();

        try {
            // Send the request and get the JSON response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();

            // Check if the API call was successful
            if (response.statusCode() != 200 || jsonResponse.contains("\"result\":\"error\"")) {
                System.err.println("API Request Failed. Status: " + response.statusCode());
                // For better debugging, display the full response body if it contains an error message.
                if (jsonResponse.contains("error")) {
                     System.err.println("API Error Details: " + jsonResponse);
                }
                return -1.0;
            }
            
            // Simplified Manual JSON Parsing
            // We look for the section: "TARGET_CURRENCY":RATE
            String searchString = "\"" + targetCurrency + "\":";
            int startIndex = jsonResponse.indexOf(searchString);

            if (startIndex == -1) {
                System.err.println("Error: Target currency code not found in API response.");
                return -1.0;
            }

            // Move past the search string to the rate value
            startIndex += searchString.length();
            
            // Find the end of the rate value (where the comma or closing brace is)
            int endIndex = jsonResponse.indexOf(",", startIndex);
            if (endIndex == -1) {
                // If it's the last rate in the list, look for the closing curly brace '}'
                endIndex = jsonResponse.indexOf("}", startIndex);
            }
            
            // Extract the substring containing the rate value
            String rateString = jsonResponse.substring(startIndex, endIndex).trim();

            // Parse the string to a double
            return Double.parseDouble(rateString);

        } catch (IOException | InterruptedException e) {
            System.err.println("Network error while connecting to API: " + e.getMessage());
            return -1.0;
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse exchange rate value from API response.");
            return -1.0;
        }
    }
}