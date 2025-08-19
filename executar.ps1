# Script PowerShell para compilar e executar o conversor de moedas
Write-Host "Compilando o projeto..." -ForegroundColor Green
javac -cp "lib\gson-2.10.1.jar;." -d . src\br\com\conversormoeda\*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Erro na compilação. Verifique os erros acima." -ForegroundColor Red
    Read-Host "Pressione ENTER para sair"
    exit $LASTEXITCODE
}

Write-Host "Projeto compilado com sucesso!" -ForegroundColor Green
Write-Host ""
Write-Host "Executando o conversor de moedas em uma nova janela..." -ForegroundColor Yellow

# Inicia o programa Java em uma nova janela de terminal
Start-Process cmd -ArgumentList "/c java -cp `.;lib\gson-2.10.1.jar` br.com.conversormoeda.ConversorApp & pause"

Write-Host "Verifique a nova janela aberta para interagir com o programa." -ForegroundColor Cyan
