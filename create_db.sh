docker stop postgres
docker rm postgres

docker run --name postgres \
-e POSTGRES_DB=users \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgresPassword \
-p 5432:5432 \
-v "C:\Users\SpardaRus\IdeaProjects\users\initDB":/docker-entrypoint-initdb.d \
postgres:14

