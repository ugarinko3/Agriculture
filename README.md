# Реестр сельскохозяйственных угодий

REST-приложение с документацией на OpenAPI v3.

## Установка

1. **Склонируйте репозиторий:**

   ```bash
   git clone https://github.com/ugarinko3/Agriculture.git
   cd Agriculture
   ```
2. **Установка JDK 1.8v**

3. **Установка Maven через brew**
   ```bash
   brew install maven
   ```
4. **Создайте базу данных**
   ```bash
    sh create_db.sh
   ```
5. **Запуск проекта**
   ```bash
   mvn spring-boot:run
   ```

6. **Просмотр документации в Swagger**

   [Открыть Swagger UI](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)

