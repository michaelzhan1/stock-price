package org.example.stock;

import org.json.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class Stock {
    private static final Logger LOGGER = Logger.getLogger(Stock.class.getName());

    /**
     * Make Stock non-instantiable
     */
    private Stock() {}

    /**
     * Wrapper for Finnhub API to get price of given stock
     * @param symbol the symbol of stock to get
     * @return the current price of the stock
     */
    public static double getPriceFromSymbol(String symbol) {
        try {
            FileHandler fh = new FileHandler("logs/stock.log");
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        symbol = symbol.toUpperCase();
        Dotenv dotenv = Dotenv.configure().load();
        String apiKey = dotenv.get("API_KEY");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=" + symbol))
                .header("X-Finnhub-Token", apiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try (HttpClient client = HttpClient.newHttpClient()) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException|InterruptedException e) {
            LOGGER.severe(e.toString());
        }
        if (response != null) {
            try {
                JSONObject data = new JSONObject(response.body());
                if (data.get("d") == JSONObject.NULL) return -1;
                else return ((BigDecimal) data.get("c")).doubleValue();
            }
            catch (JSONException e) {
                LOGGER.severe(e.toString());
            }
        }
        return 0;
    }
}
