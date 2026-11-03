# Проект ["Планировщик задач"](https://zhukovsd.github.io/java-backend-learning-course/projects/task-tracker/)

Микросервисное приложение для управления своими задачами, используется в качестве TODO листа.

### Функциональность
Работа с пользователями:

* Регистрация
* Авторизация
* Logout

Работа с задачами:

* Создание, редактирование (каждая задача имеет заголовок и описание)
* Пометка задачи как сделанной
* Удаление

Оповещение пользователей по email:

* Приветственное письмо
* Каждый день в полночь отчёт о том, какие задачи были сделаны за прошедшие сутки,
  какое количество осталось невыполненными

### Архитектура
* [Backend Service](https://github.com/makson4986/TaskTrackerBackend) - приложение предоставляющие API для работы с пользователя и задачами
* [Scheduler Service](https://github.com/makson4986/TaskTrackerScheduler) - планирощик, запускающий ежедневную генерацию отчетов
* [Mail Service](https://github.com/makson4986/TaskTrackerMail) - сервис для рассылки привественных писем и ежедневных отчетов
* [Summarization Service](https://github.com/makson4986/TaskTrackerSummarization) - приложение, отвечающее за генерацию отчетов и интеграцию с API GigaChat

### Технологический стек
* Java 25
* Spring Boot
* Spring Security
* Spring Validation
* Apache Kafka
* Java Mail Sender
* Lombok
* MapStruct
* PostgreSQL
* GigaChat API
* Docker & Docker Compose
* JUnit
* Mockito
* Testcontainers
* Nginx

### Деплой

Для запуска приложений