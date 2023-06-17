# users-service

Сервис для работы с пользователями и их подписками

## Сборка и запуск

### Что бы собрать образ через Dockerfile выполни команды:

1. ```bash  
      ./gradlew clean build
    ```
2. ```bash  
      docker build -t users-service .
    ```

### Что бы запустить образ выполни команды:

1. Запуск контейнера postgresql
   ```bash  
     docker-compose up
   ```
2. Запуск контейнера сервиса
   ```bash  
     docker run --rm --name users-service-container --network users_default -p 8080:8080 users-service
   ```

### Проверка работоспособности приложения

1. Опрос статуса приложения через актуатор

   ```bash  
     curl --location 'http://localhost:8080/actuator/health'
   ```

2. Должен быть ответ
   ```json
   {
       "status": "UP"
   }
   ```