# Proyecto Base Implementando Clean Architecture


## Ejecutar con Docker

### Pasos para Ejecutar la aplicación con docker

1. Clona el repositorio y accede a la carpeta del proyecto:
   ```sh
   git clone https://github.com/Lau36/taller-webflux.git
   cd deployment
   ```

3. Levanta los contenedores con Docker:
   ```sh
   docker-compose up -d
   ```

4. La aplicación estará disponible en:
   ```
   http://localhost:8080
   ```

### Configuración de la Base de Datos

1. Asegúrate de que el contenedor de Postgres esté corriendo y accede a el por medio de la shell:

   ```
   docker exec -it db-postgres psql -U postgres -d apiUsers
   ```

2. Una vez se está dentro de la base de datos, hay que crear la tabla:
   ```mysql
   CREATE TABLE public.users(
    id SERIAL PRIMARY KEY,
    apiId INT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255)  NOT NULL
    );
   ```
### Configuración de aws localstack

1. Corremos el siguiente comando para la configuración de las credenciales y ponemos 12345 para el valor de estas y us-east-1 como región:

   ```
   aws configure
   ```

2. Creamos la tabla de dynamo:
   ```
   aws --endpoint-url=http://localhost:4566 dynamodb create-table --table-name users --key-schema AttributeName=id,KeyType=HASH --attribute-definitions AttributeName=id,AttributeType=N --billing-mode PAY_PER_REQUEST --region us-east-1
   ```
3. Creamos la cola sqs:
   ```
   aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name UsersQueue
   ```
4. Para ver la base de datos de dynamo se puede hacer con el siguiente comando:
   ```
   aws dynamodb scan --table-name users --endpoint-url http://localhost:4566 --region us-east-1
   ```


