@echo off
echo Certifique-se de que o projeto já está compilado!
echo.
echo Pressione qualquer tecla para iniciar o Conversor de Moedas em uma nova janela...
pause > nul
start "Conversor de Moedas" cmd /k "cd /d %~dp0 & cls & echo Iniciando Conversor de Moedas... & echo. & java -cp .;lib\gson-2.10.1.jar br.com.conversormoeda.ConversorApp"
