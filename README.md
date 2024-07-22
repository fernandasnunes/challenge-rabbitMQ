# Desafio Técnico - Aplicação Spring com RabbitMQ e MongoDB

## Descrição da Aplicação

Esta aplicação é uma API REST desenvolvida em Java 11 utilizando o framework Spring Boot. A aplicação segue uma arquitetura hexagonal, o que permite uma melhor organização e manutenção da lógica de negócios, desacoplando-a das interfaces externas.

### Arquitetura da Aplicação

A aplicação segue uma arquitetura hexagonal. O diagrama a seguir ilustra a estrutura da aplicação:

# Estrutura

- **Interface Externa (API REST)**
  - HTTP Requests
    - Adapters (REST)
      - Calls to
        - **Application Business Logic**
          - Data Access
            - Ports (Interfaces)
              - Messages / Events
                - RabbitMQ Broker
                - MongoDB
## Benefícios da Arquitetura Hexagonal

A arquitetura hexagonal, também conhecida como Arquitetura de Portas e Adaptadores, oferece diversos benefícios:

- **Desacoplamento**: A lógica de negócios (o domínio) é isolada das interfaces externas (como APIs e bancos de dados). Isso permite mudanças na interface sem impactar a lógica de negócios, e vice-versa.

- **Flexibilidade**: A aplicação pode ser facilmente adaptada para diferentes interfaces ou tecnologias. Se precisar trocar o banco de dados ou o sistema de mensageria, você só precisa ajustar os adaptadores, mantendo a lógica de negócios inalterada.

- **Manutenibilidade**: A arquitetura hexagonal promove uma estrutura organizada e modular, o que facilita a manutenção e a evolução da aplicação ao longo do tempo.

### Dependências

- **RabbitMQ**: Sistema de mensageria utilizado para comunicação assíncrona.
- **MongoDB**: Banco de dados NoSQL utilizado para armazenar informações dos pedidos.

### Tecnologias Utilizadas

- **Linguagens**: Java 11
- **Framework**: Spring Boot
- **IDE**: IntelliJ IDEA
- **SO**: Qualquer sistema operacional que suporte Docker

### Endpoints

A aplicação expõe os seguintes endpoints:

1. **POST /pedidos/enviar**

   - **Descrição**: Cria um novo pedido e o publica no RabbitMQ.
   - **Request Body**:
     ```json
     { 
       "codigoPedido": 14,
       "codigoCliente": 123,
       "itens": [
         {
           "codigoProduto": 456,
           "quantidade": 2
         }
       ]
     }
     ```
   - **Resposta**:
     ```json
     {
       "mensagem": "Pedido criado com sucesso",
       "codigoCliente": 123
     }
     ```

2. **GET /pedidos/valorTotal/{codigoCliente}**

   - **Descrição**: Retorna o valor total dos pedidos para um cliente.
   - **Resposta**:
     ```json
     {
       "codigoCliente": 123,
       "valorTotal": 150.0
     }
     ```

3. **GET /pedidos/quantidadeTotal/{codigoCliente}**

   - **Descrição**: Retorna a quantidade total de pedidos para um cliente.
   - **Resposta**:
     ```json
     5
     ```

4. **GET /pedidos/listar/{codigoCliente}**
   - **Descrição**: Lista todos os pedidos de um cliente.
   - **Resposta**:
     ```json
     [
       {
         "codigoPedido": "789",
         "codigoCliente": 123
       }
     ]
     ```

### Erros

- **Erro de Validação**:
  ```json
  {
    "codigo": 900,
    "mensagem": "O pedido não é válido",
    "detalhes": [
      "Pedido não pode ser nulo"
    ]
  }
  ```

- **Erro de Negócio**:

```json
{
"codigo": 100,
"mensagem": "O cliente informado não possui pedido.",
"detalhes": [
  "Nenhum pedido encontrado para o cliente com código: 600"
]
}
```

## Erro Técnico

Exemplo:
Erros não conhecidos, como problemas ao inserir um pedido, são registrados nos logs.

## Execução da Aplicação

### Utilizando Docker

Inicie os containers com Docker Compose:

**Na pasta da aplicação, execute o comando:**
```
docker-compose up
```

## Dockerfile e Docker Compose:

**O Dockerfile e o docker-compose.yml estão localizados na pasta da aplicação.
As coleções para MongoDB estão na pasta collections.**

## Instalando RabbitMQ e MongoDB Manualmente
## RabbitMQ:

docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management

# MongoDB:

**Exemplo:**

docker run -d -p 28017:27017 --name mongodb -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=adminpass mongo:latest

**Alterar application.yml:**

Após iniciar os containers, altere o arquivo application.yml da aplicação para refletir a configuração dos serviços.

# Visualizar Dados:

Utilize o MongoDB Compass para visualizar e gerenciar os dados armazenados no MongoDB.
## Testes
A aplicação contém testes manuais e de componente para garantir a validade das funcionalidades e a integridade da aplicação. Os testes ajudam a identificar erros e garantir que a aplicação esteja funcionando conforme o esperado.

