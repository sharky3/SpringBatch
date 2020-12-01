# SpringBatch
Spring Boot app for importing CSV files into DB in chunks and accessing it via REST

Задача:
1) Загрузить в таблицу БД содержимое файла source.csv
   Предполагая, что размер файла может быть очень большим, выполнять загрузку данных в таблицу в пакетном режиме. 
   Успешно загруженный файл переносить в каталог processed. 
2) REST-сервис по предоставлению в json данных из полученной таблицы

