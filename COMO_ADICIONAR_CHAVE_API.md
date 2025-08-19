# Como adicionar sua chave de API ao Conversor de Moedas

1. Primeiro, obtenha sua chave de API gratuita no site [Exchange Rate API](https://www.exchangerate-api.com/).
   - Acesse o site e clique em "Get Free Key".
   - Complete o cadastro com seu e-mail.
   - Confirme seu e-mail e obtenha sua chave API.

2. Abra o arquivo `src\br\com\conversormoeda\ConversorApp.java` em seu editor de código.

3. Localize a linha 10 que contém:
   ```java
   private static final String API_KEY = "SUA_CHAVE_API"; // Substitua aqui pela sua chave da API
   ```

4. Substitua `"SUA_CHAVE_API"` pela chave que você obteve, mantendo as aspas. Por exemplo:
   ```java
   private static final String API_KEY = "1234abcd5678efgh9012ijkl"; // Substitua aqui pela sua chave da API
   ```

5. Salve o arquivo.

6. Execute o script `compilar-e-executar.bat` para compilar e executar o programa.

## Observações

- A chave de API é necessária para fazer requisições à API Exchange Rate.
- No plano gratuito, você tem acesso a 1.500 requisições por mês.
- Não compartilhe sua chave de API publicamente.
