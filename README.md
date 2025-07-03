# 💳Sistema de Cartões
Sistema de gerenciamento do ciclo de vida de cartões bancários, permitindo a solicitação, aprovação, ativação e cadastro de senha, redefinição de senha, bloqueio, cancelamento e acompanhamento de fatura/extrato. Foi desenvolvido com foco em **Domain-Driven Design (DDD)** e **Clean Architecture**, garantindo separação de responsabilidades, modularidade e clareza no domínio.

## 🛠️Tecnologias utilizadas
- Java
- Spring Boot
- PostgreSQL

## 🧠 Modelagem DDD

A modelagem completa do domínio está documentada em:

📄 [`/docs/modelagem-ddd.md`](./docs/modelagem-ddd.md)

Essa modelagem inclui:
- Estratégia de contextos delimitados
- Linguagem ubíqua
- Agregados e VOs
- Casos de uso
- Repositórios

📊 Diagramas C4 (Contexto, Containers e Componentes) estão na pasta:

🗂️ [`/docs/c4-model/`](./docs/c4-model/)

## 📍Endpoints da API

- **POST** `/cartoes/solicitar` - Realiza a solicitação do cartão
    #### Exemplo de corpo de requisição
    ```json
    {
        "cpf": "13453830024",
        "nomeCompleto": "João Oliveira Silva",
        "dataNascimento": "1990-08-18",
        "rendaMensal": 8000.00,
        "tipoCartao": "DEBITO",
        "bandeiraCartao": "MASTERCARD"
    }
    ```
- **PUT** `/cartoes/{numero}/aprovar` - Realiza a aprovação do cartão
- **PUT** `/cartoes/ativar` - Realiza a ativação com cadastro da senha do cartão
    #### Exemplo de corpo de requisição
    ```json
    {
        "numero": "9428405014782974",
        "cpf": "13453830024",
        "senha": "837190"
    }
    ```
- **PUT** `/cartoes/redefinir-senha` - Realiza a redefinição de senha do cartão
  #### Exemplo de corpo de requisição
    ```json
    {
        "numero": "9428405014782974",
        "cpf": "13453830024",
        "senhaAntiga": "837190",
        "senhaNova": "178977"
    }
    ```
- **PUT** `/cartoes/comunicar-perda-roubo` - Realiza o bloqueio imediato do cartão em virtude de perda ou roubo
  #### Exemplo de corpo de requisição
    ```json
    {
      "numero":"9428405014782974",
      "cpf":"13453830024",
      "tipoDeOcorrencia":"ROUBO"
    }
    ```
- **PUT** `/cartoes/bloquear-temporariamente` - Realiza o bloqueio temporário do cartão
- **PUT** `/cartoes/cancelar` - Realiza o cancelamento permanente do cartão
  #### Exemplo de corpo de requisição
    ```json
    {
        "numero": "9428405014782974",
        "cpf": "13453830024",
        "motivo": "Suspeita de fraude"
    }
    ```
- **GET** `/cartoes/fatura/{numeroCartao}` - Realiza o cálculo da fatura do cartão
- **GET** `/cartoes/extrato/{numeroCartao}` - Realiza a consulta do extrato do cartão
- **GET** `/cartoes/extrato-filtrado` - Realiza a consulta do extrato do cartão com filtros
  #### Tipos de filtro (Parâmetros de requisição)
  | Filtro           | Exemplo                        |
  |------------------|--------------------------------|
  | Número do Cartao | ?numeroCartao=9428405014782974 |
  | Período inicial  | &inicio=2025-06-01             |
  | Período final    | &final= 2025-06-30             |
  | Tipo de operação | &tipo=DEBITO                   |
  | Valor mínimo     | &valorMin=100                  |
  | Valor máximo     | &valorMax=2000                 |

## 💻Execução do projeto
Pré-requisitos: Java 21
```bash
# Clone o repositório
git clone https://github.com/luis-crsa/card-system.git

# Acesse a pasta do projeto
cd card-system

# Execute o projeto
./mvnw spring-boot:run
```

# 👨‍💻Autores
- Luís Cláudio Rodrigues Sarmento
- Renato Xavier Portela Giordano
- Ana Beatriz Carvalho Freire Nunes
