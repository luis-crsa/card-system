# Modelagem DDD – Sistema de Cartões

## 1. Modelagem Estratégica

### 1.1 Domínio Principal

**Domínio:** Gestão de Cartões Bancários e Transações Financeiras

O sistema oferece funcionalidades relacionadas à criação, ativação, segurança e controle dos cartões, além do acompanhamento financeiro via transações e faturas. Regras específicas de negócio incluem:

- Validação de idade mínima (≥ 18 anos)
- Validação de CPF
- Verificação de renda mínima conforme o tipo do cartão
- Geração automática do número do cartão
- Controle de status e bandeira
- Definição e alteração de senha com critérios de segurança
- Cálculo do valor da fatura do cartão
- Filtros aplicáveis ao extrato (período, valor, tipo de transação)

**Estratégia:**  
Domínio tratado com dois contextos principais (Cartão e Transações), ainda dentro de uma arquitetura coesa e enxuta, facilitando a manutenção e evolução.

---

### 1.2 Bounded Contexts (Contextos Delimitados)

Para organizar o domínio de cartões de forma coerente e manter a clareza das responsabilidades, o sistema atualmente possui dois contextos delimitados:

-  **Cartão (Cartao)**  
   Responsável por gerenciar o ciclo de vida completo do cartão, desde a solicitação até o bloqueio ou cancelamento.

-  **Transações (Lancamento)**
   Responsável pelo registro, consulta e filtragem de transações vinculadas ao cartão, bem como acompanhamento de fatura e extrato.

**Funcionalidades principais:**
- Solicitação de Cartão
- Aprovação de cartão
- Ativação de Cartão com cadastro de senha
- Redefinição de Senha
- Bloqueio Temporário
- Comunicação de Perda ou Roubo
- Cancelamento Definitivo
- Consulta de Fatura
- Consulta de Extrato
- Consulta de Extrato com filtros por período, tipo de transação e valor

> **Observação:** Não há, no momento, contextos separados para Cliente ou Segurança, pois o foco está na gestão do cartão e transações.

---

### 1.3 Linguagem Ubíqua

| Termo                      | Definição                                                        |
|----------------------------|-----------------------------------------------------------------|
| **Cartão**                 | Meio de pagamento emitido para o cliente                        |
| **Solicitação**            | Processo de criação de um novo cartão                            |
| **Ativação**               | Ato de habilitar um cartão para uso                              |
| **Cadastro de senha**      | Ato de definir a senha do cartão pela primeira vez, exigido junto à ativação|
| **Bloqueio Temporário**    | Interrupção reversível do uso do cartão                         |
| **Cancelamento definitivo**| Interrupção irreversível do uso do cartão                         |
| **Senha**                  | Código de segurança de 6 dígitos vinculado ao cartão           |
| **Redefinição de Senha**   | Ato de alterar a senha já cadastrada                            |
| **Tipo de Cartão**         | Indica se é de CRÉDITO ou DÉBITO                               |
| **Bandeira**               | Instituição responsável pela rede do cartão (VISA, MASTERCARD) |
| **Status do Cartão**       | Estado atual do cartão (SOLICITADO, ATIVO, BLOQUEADO, CANCELADO)|
| **CPF**                    | Identificador único do titular, com validação obrigatória      |
| **Renda Mensal**           | Valor informado para análise do tipo de cartão                  |
| **Data de Nascimento**     | Usada para validar se o cliente tem idade suficiente           |
| **Número do Cartão**       | Código gerado automaticamente para representar o cartão       |
| **Transação (Lançamento)** | Registro financeiro vinculado a um cartão                      |
| **Fatura**                 | Soma das transações atuais em aberto para pagamento            |
| **Extrato**                | Histórico de transações realizadas                             |
| **Filtro de extrato**      | Critérios aplicados à consulta do extrato                      |

---

## 2. Modelagem Tática

### 2.1 Agregados

-  **Cartão (Cartao)**  
   Agregado raiz que encapsula dados e regras do ciclo de vida do cartão, incluindo:
    - Solicitação com validações (CPF, idade, renda)
    - Geração do número do cartão
    - Controle de status (Solicitado, Ativo, Bloqueado, Cancelado)
    - Armazenamento e validação da senha

-  **Transação (Lancamento)**
   Agregado responsável pelo registro das movimentações financeiras do cartão, com regras para:
    - Cálculo do valor total da fatura
    - Geração de extrato com filtros

---

### 2.2 Entidades e Value Objects

#### Entidades (com identidade própria)

| Entidade    | Identificador | Descrição                                 |
|-------------|---------------|--------------------------------------------|
| `Cartao`    | `UUID`        | Representa o cartão emitido                |
| `Lancamento`| `UUID`        | Representa uma transação realizada com o cartão |

> Nota: A entidade `Cliente` não foi criada para simplificação; dados do cliente estão incorporados no Cartão.

#### Value Objects (sem identidade)

| VO                | Responsabilidade                                               |
|-------------------|---------------------------------------------------------------|
| `CPF`             | Validação de formato e consistência do CPF                    |
| `DataDeNascimento`| Verificação de maioridade (≥ 18 anos)                         |
| `RendaMensal`     | Representa valor positivo e verifica renda mínima por tipo    |
| `Senha`           | Validação de regras de segurança (6 dígitos, sem repetições)  |

> Todos os VOs são imutáveis e validam regras ao serem criados.

---

### 2.3 Casos de Uso

| Caso de Uso                        | Descrição                                            |
|------------------------------------|------------------------------------------------------|
| `SolicitarCartaoUseCase`           | Solicitação de emissão de novo cartão                |
| `AprovarCartaoUseCase`             | Aprovação do cartão solicitado                       |
| `AtivarCartaoUseCase`              | Ativação do cartão e cadastro da senha após validações|
| `RedefinirSenhaUseCase`            | Redefinição da senha do cartão                       |
| `BloquearCartaoUseCase`            | Bloqueio temporário do cartão                        |
| `ComunhicarPerdaRouboUseCase`      | Bloqueio imediato do cartão em virtude de perda ou roubo|
| `CancelarCartaoUseCase`            | Cancelamento permanente do cartão                    |
| `FaturaCartaoUseCase`              | Consulta o total das transações não pagas do cartão  |
| `ExtratoCartaoUseCase`             | Consulta todas as transações do cartão               |
| `ExtratoFiltradoUseCase`           | Consulta transações filtradas por critérios específicos|

> Casos de uso implementados na camada de aplicação, isolando regras de negócio da infraestrutura.

---

### 2.4 Repositórios

Interface para persistência e recuperação de dados no domínio:

```java
public interface CartaoRepository {
    void salvar(Cartao cartao);
    Optional<Cartao> buscarPorNumero(String numero);
}

public interface LancamentoRepository {
    List<Lancamento> buscarPorNumeroCartao(String numeroCartao);
}
```

> As implementações (ex: `ImplementacaoCartaoRepositoryJpa`) estão na camada de infraestrutura, baseadas em Spring Data JPA.
