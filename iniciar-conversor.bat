@echo off
start "Conversor de Moedas" cmd /k "cd /d %~dp0 & java -cp .;lib\gson-2.10.1.jar br.com.conversormoeda.ConversorApp"
