# Recommendation Service (MVP) — Bank "Star"

Spring Boot service that recommends bank products to users based on transaction history in provided H2 database.

## Запуск

Требования:
- Java 17+
- Maven 3.8+

1. Скопируй файл `transaction.mv.db` в `src/main/resources/db/transaction.mv.db`.
2. Открой проект в IntelliJ IDEA → **Import Project** → выбери `pom.xml` (Maven).
3. Собери проект: `mvn -U clean package`
4. Запусти приложение:
   ```bash
   mvn spring-boot:run
   ```
   или запусти класс `RecommendationApplication` из IDE.

5. REST API:
   ```
   GET http://localhost:8080/recommendation/{userId}
   ```

Пример тестовых пользователей (в БД):
- `cd515076-5d8a-44be-930e-8d4fcb79f42d`
- `d4a4d619-9a0c-4fc5-b0cb-76c49409546b`
- `1f9b149c-6577-448a-bc94-16bea229b71a`

H2 Console: http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:file:./src/main/resources/db/transaction`  
User: `sa` (пароль пустой)
