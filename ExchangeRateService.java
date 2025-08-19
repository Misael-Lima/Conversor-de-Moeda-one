import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExchangeRateService {
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    
    private final String apiKey;
    private final HttpClient httpClient;
    
    public ExchangeRateService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
    }
    
    /**
     * Obtém as taxas de câmbio para a moeda base especificada
     * @param baseCurrency A moeda base para a conversão (ex: "USD")
     * @return Um objeto JsonObject contendo as taxas de câmbio
     * @throws IOException Se ocorrer um erro de IO durante a requisição
     * @throws InterruptedException Se a requisição for interrompida
     */
    public JsonObject getExchangeRates(String baseCurrency) throws IOException, InterruptedException {
        String endpoint = BASE_URL + apiKey + "/latest/" + baseCurrency;
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new IOException("Falha na requisição à API. Código de status: " + response.statusCode());
        }
        
        return JsonParser.parseString(response.body()).getAsJsonObject();
    }
    
    /**
     * Converte um valor de uma moeda para outra
     * @param fromCurrency A moeda de origem
     * @param toCurrency A moeda de destino
     * @param amount O valor a ser convertido
     * @return O valor convertido
     * @throws IOException Se ocorrer um erro de IO durante a requisição
     * @throws InterruptedException Se a requisição for interrompida
     */
    public double convertCurrency(String fromCurrency, String toCurrency, double amount) 
            throws IOException, InterruptedException {
        JsonObject ratesData = getExchangeRates(fromCurrency);
        
        // Verifica se a requisição foi bem-sucedida
        String result = ratesData.get("result").getAsString();
        if (!"success".equals(result)) {
            throw new IOException("Falha na obtenção das taxas de câmbio: " + result);
        }
        
        // Obtém a taxa de conversão para a moeda de destino
        double exchangeRate = ratesData.getAsJsonObject("conversion_rates").get(toCurrency).getAsDouble();
        
        // Calcula o valor convertido
        return amount * exchangeRate;
    }
}
