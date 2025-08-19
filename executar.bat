@echo off
echo Compilando o projeto...
javac -cp "lib\gson-2.10.1.jar;." -d . src\br\com\conversormoeda\*.java
echo Projeto compilado com sucesso!
echo.
echo Executando o conversor de moedas em uma nova janela...
start "Conversor de Moedas" cmd /k java -cp ".;lib\gson-2.10.1.jar" br.com.conversormoeda.ConversorApp
echo Verifique a nova janela aberta para interagir com o programa.
