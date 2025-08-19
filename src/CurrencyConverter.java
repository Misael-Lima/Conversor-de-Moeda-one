import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class CurrencyConverter {
    private ExchangeRateService exchangeRateService;
    private Scanner scanner;
    
    // Array de moedas suportadas
    private static final String[] SUPPORTED_CURRENCIES = {
        "ARS", // Peso argentino
        "BOB", // Boliviano
        "BRL", // Real brasileiro
        "CLP", // Peso chileno
        "COP", // Peso colombiano
        "USD"  // Dólar americano
    };
    
    // Nomes das moedas para exibição
    private static final String[] CURRENCY_NAMES = {
        "Peso Argentino",
        "Boliviano",
        "Real Brasileiro",
        "Peso Chileno",
        "Peso Colombiano",
        "Dólar Americano"
    };
    
    public CurrencyConverter() {
        this.exchangeRateService = new ExchangeRateService();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Exibe o menu principal e processa a escolha do usuário
     */
    public void showMainMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n=== CONVERSOR DE MOEDAS ===");
            System.out.println("Escolha uma opção:");
            System.out.println("1. Converter moedas");
            System.out.println("2. Ver taxas de câmbio atuais");
            System.out.println("3. Sair");
            System.out.print("Sua escolha: ");
            
            int choice = readIntInput();
            
            switch (choice) {
                case 1:
                    showCurrencyConversionMenu();
                    break;
                case 2:
                    showExchangeRates();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Obrigado por usar o Conversor de Moedas! Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }
    }
    
    /**
     * Exibe o menu de conversão de moedas
     */
    private void showCurrencyConversionMenu() {
        System.out.println("\n=== CONVERSÃO DE MOEDAS ===");
        
        // Exibe as moedas disponíveis
        System.out.println("Moedas disponíveis:");
        for (int i = 0; i < SUPPORTED_CURRENCIES.length; i++) {
            System.out.println((i + 1) + ". " + CURRENCY_NAMES[i] + " (" + SUPPORTED_CURRENCIES[i] + ")");
        }
        
        // Seleciona a moeda de origem
        System.out.print("\nEscolha a moeda de origem (1-" + SUPPORTED_CURRENCIES.length + "): ");
        int fromIndex = readIntInput() - 1;
        
        if (fromIndex < 0 || fromIndex >= SUPPORTED_CURRENCIES.length) {
            System.out.println("Moeda inválida. Voltando ao menu principal.");
            return;
        }
        
        String fromCurrency = SUPPORTED_CURRENCIES[fromIndex];
        
        // Seleciona a moeda de destino
        System.out.print("Escolha a moeda de destino (1-" + SUPPORTED_CURRENCIES.length + "): ");
        int toIndex = readIntInput() - 1;
        
        if (toIndex < 0 || toIndex >= SUPPORTED_CURRENCIES.length) {
            System.out.println("Moeda inválida. Voltando ao menu principal.");
            return;
        }
        
        String toCurrency = SUPPORTED_CURRENCIES[toIndex];
        
        // Verifica se as moedas são iguais
        if (fromCurrency.equals(toCurrency)) {
            System.out.println("As moedas de origem e destino são iguais. Voltando ao menu principal.");
            return;
        }
        
        // Obtém o valor a ser convertido
        System.out.print("Digite o valor a ser convertido: ");
        double amount = readDoubleInput();
        
        if (amount <= 0) {
            System.out.println("O valor deve ser maior que zero. Voltando ao menu principal.");
            return;
        }
        
        // Realiza a conversão
        try {
            double convertedAmount = exchangeRateService.convertCurrency(fromCurrency, toCurrency, amount);
            
            // Formata o resultado para exibição
            DecimalFormat df = new DecimalFormat("#,##0.00");
            System.out.println("\nResultado da conversão:");
            System.out.println(df.format(amount) + " " + fromCurrency + " = " + df.format(convertedAmount) + " " + toCurrency);
            
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao converter moedas: " + e.getMessage());
        }
    }
    
    /**
     * Exibe as taxas de câmbio atuais para uma moeda base selecionada
     */
    private void showExchangeRates() {
        System.out.println("\n=== TAXAS DE CÂMBIO ATUAIS ===");
        
        // Exibe as moedas disponíveis
        System.out.println("Escolha a moeda base:");
        for (int i = 0; i < SUPPORTED_CURRENCIES.length; i++) {
            System.out.println((i + 1) + ". " + CURRENCY_NAMES[i] + " (" + SUPPORTED_CURRENCIES[i] + ")");
        }
        
        // Seleciona a moeda base
        System.out.print("\nSua escolha (1-" + SUPPORTED_CURRENCIES.length + "): ");
        int baseIndex = readIntInput() - 1;
        
        if (baseIndex < 0 || baseIndex >= SUPPORTED_CURRENCIES.length) {
            System.out.println("Moeda inválida. Voltando ao menu principal.");
            return;
        }
        
        String baseCurrency = SUPPORTED_CURRENCIES[baseIndex];
        
        try {
            // Obtém as taxas de câmbio
            System.out.println("\nObtendo taxas de câmbio para " + CURRENCY_NAMES[baseIndex] + "...");
            var ratesData = exchangeRateService.getExchangeRates(baseCurrency);
            
            // Verifica se a requisição foi bem-sucedida
            String result = ratesData.get("result").getAsString();
            if (!"success".equals(result)) {
                System.out.println("Falha na obtenção das taxas de câmbio: " + result);
                return;
            }
            
            // Exibe as taxas de câmbio
            System.out.println("\nTaxas de câmbio para 1 " + baseCurrency + ":");
            var rates = ratesData.getAsJsonObject("conversion_rates");
            
            DecimalFormat df = new DecimalFormat("#,##0.00####");
            
            for (String currency : SUPPORTED_CURRENCIES) {
                if (!currency.equals(baseCurrency)) {
                    double rate = rates.get(currency).getAsDouble();
                    int currencyIndex = getCurrencyIndex(currency);
                    System.out.println(currency + " (" + CURRENCY_NAMES[currencyIndex] + "): " + df.format(rate));
                }
            }
            
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao obter taxas de câmbio: " + e.getMessage());
        }
    }
    
    /**
     * Retorna o índice de uma moeda no array SUPPORTED_CURRENCIES
     * @param currency O código da moeda
     * @return O índice da moeda ou -1 se não encontrada
     */
    private int getCurrencyIndex(String currency) {
        for (int i = 0; i < SUPPORTED_CURRENCIES.length; i++) {
            if (SUPPORTED_CURRENCIES[i].equals(currency)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Lê um número inteiro da entrada do usuário
     * @return O número inteiro lido
     */
    private int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número inteiro: ");
            }
        }
    }
    
    /**
     * Lê um número decimal da entrada do usuário
     * @return O número decimal lido
     */
    private double readDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número válido: ");
            }
        }
    }
    
    /**
     * Fecha os recursos utilizados
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
