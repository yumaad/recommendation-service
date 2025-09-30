# Recommendation Service

Рекомендательная система для банка **«Стар»**.  
Сервис анализирует транзакции пользователей и предлагает новые банковские продукты на основе **статических** и **динамических** правил.

---

## 🚀 Стек технологий
- Java 17
- Spring Boot (Web, Data JPA, Cache)
- H2 Database (read-only, с импортированными данными)
- PostgreSQL (для хранения динамических правил)
- Liquibase (миграции для второй БД)
- Caffeine Cache (кеширование запросов)
- Telegram Bot API (бот для выдачи рекомендаций)
- Maven

---

## 📌 Функциональность
- Получение рекомендаций через REST API и через Telegram-бот
- Статические правила (из ДЗ1)
- Динамические правила:
   - добавление правил
   - удаление
   - получение списка
- Ведение статистики срабатываний правил (`/rule/stats`)
- Управление сервисом:
   - сброс кешей (`/management/clear-caches`)
   - информация о сервисе (`/management/info`)

---

## 📡 API

### Рекомендации
`GET /recommendation/{user_id}` — получить рекомендации для пользователя

### Динамические правила
- `POST /rule` — создать новое правило
- `GET /rule` — список всех правил
- `DELETE /rule/{product_id}` — удалить правило по id продукта

### Статистика
`GET /rule/stats` — получить статистику срабатываний правил

### Управление
- `POST /management/clear-caches` — очистить кеш запросов
- `GET /management/info` — получить название и версию сервиса

---

## 🤖 Telegram Bot
Команда `/recommend username` возвращает:
- Приветствие + имя пользователя
- Список новых продуктов с рекомендациями
- Если пользователя нет или их несколько — «Пользователь не найден»

---

## 🛠️ Запуск проекта локально

```bash
git clone https://github.com/yumaad/recommendation-service.git
cd recommendation-service
./mvnw clean package
java -jar target/recommendation-service-1.0.0.jar
```

Перед запуском можно установить переменные окружения:
```properties
TELEGRAM_BOT_USERNAME=YourBotName  
TELEGRAM_BOT_TOKEN=YourTokenHere  
```

---

## 📚 Документация
Подробности в [Wiki проекта](https://github.com/yumaad/recommendation-service/wiki):
- Требования / User Stories
- Архитектура / диаграммы
- API (Swagger / OpenAPI)
- Инструкция по развёртыванию
- Таблица трассировки требований

---

## 👤 Автор
Юмжана Доржиева — реализовала весь проект