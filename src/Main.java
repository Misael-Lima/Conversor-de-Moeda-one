package conversor;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Conversor de Moedas!");
        
        // Cria e inicia o conversor
        CurrencyConverter converter = new CurrencyConverter();
        
        try {
            // Exibe o menu principal
            converter.showMainMenu();
        } finally {
            // Fecha os recursos
            converter.close();
        }
    }
}
