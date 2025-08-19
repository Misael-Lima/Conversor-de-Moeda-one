package br.com.conversormoeda;

import java.util.Scanner;
import java.io.IOException;
import java.text.DecimalFormat;

public class ConversorApp {
    private static final String API_KEY = "fb7bccb03431b69b73836c88"; 
    private static final Scanner scanner = new Scanner(System.in);
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");
    
    // Moedas disponíveis
    private static final String[] MOEDAS = {"USD", "ARS", "BRL", "COP", "CLP", "BOB"};
    private static final String[] NOMES_MOEDAS = {"Dólar", "Peso argentino", "Real brasileiro", "Peso colombiano", "Peso chileno", "Boliviano"};
    
    public static void main(String[] args) {
        System.out.println("************************************************");
        System.out.println("Seja bem-vindo/a ao Conversor de Moeda =]");
        System.out.println("************************************************");
        
        // Cria o serviço de conversão
        ExchangeRateService exchangeService = new ExchangeRateService(API_KEY);
        
        boolean continuar = true;
        while (continuar) {
            exibirMenu();
            int opcao = lerInteiro("");
            
            switch (opcao) {
                case 1:
                    // Dólar >>> Peso argentino
                    converterMoeda(exchangeService, "USD", "ARS");
                    break;
                case 2:
                    // Peso argentino >>> Dólar
                    converterMoeda(exchangeService, "ARS", "USD");
                    break;
                case 3:
                    // Dólar >>> Real brasileiro
                    converterMoeda(exchangeService, "USD", "BRL");
                    break;
                case 4:
                    // Real brasileiro >>> Dólar
                    converterMoeda(exchangeService, "BRL", "USD");
                    break;
                case 5:
                    // Dólar >>> Peso colombiano
                    converterMoeda(exchangeService, "USD", "COP");
                    break;
                case 6:
                    // Peso colombiano >>> Dólar
                    converterMoeda(exchangeService, "COP", "USD");
                    break;
                case 7:
                    continuar = false;
                    System.out.println("Obrigado por usar o Conversor de Moedas. Até breve!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção entre 1 e 7.");
            }
        }
        
        scanner.close();
    }
    
    private static void exibirMenu() {
        System.out.println("************************************************");
        System.out.println("1) Dólar =>> Peso argentino");
        System.out.println("2) Peso argentino =>> Dólar");
        System.out.println("3) Dólar =>> Real brasileiro");
        System.out.println("4) Real brasileiro =>> Dólar");
        System.out.println("5) Dólar =>> Peso colombiano");
        System.out.println("6) Peso colombiano =>> Dólar");
        System.out.println("7) Sair");
        System.out.println("Escolha uma opção válida:");
        System.out.println("************************************************");
    }
    
    private static void converterMoeda(ExchangeRateService service, String de, String para) {
        System.out.println("\nConversão de " + getNomeMoeda(de) + " para " + getNomeMoeda(para));
        double valor = lerDecimal("Digite o valor a ser convertido: ");
        
        try {
            double resultado = service.convertCurrency(de, para, valor);
            System.out.println("Resultado: " + df.format(valor) + " " + de + " = " + df.format(resultado) + " " + para);
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao converter moedas: " + e.getMessage());
            System.out.println("Por favor, verifique sua conexão com a internet e se a chave de API está correta.");
        }
    }
    
    private static String getNomeMoeda(String codigo) {
        for (int i = 0; i < MOEDAS.length; i++) {
            if (MOEDAS[i].equals(codigo)) {
                return NOMES_MOEDAS[i];
            }
        }
        return codigo;
    }
    
    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
            }
        }
    }
    
    private static double lerDecimal(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite um número válido.");
            }
        }
    }
}
