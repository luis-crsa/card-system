# Mapeamento DDD Completo – Sistema de Cartões

---

## 1. Modelagem Estratégica

### 1.1 Bounded Contexts (Contextos Delimitados)

Para organizar o domínio de cartões de forma coerente e manter a clareza das responsabilidades, o sistema atualmente possui um único contexto delimitado:

-  **Cartão (Card)**  
  Responsável por gerenciar o ciclo de vida completo do cartão, desde a solicitação até o bloqueio ou cancelamento.

**Funcionalidades principais:**
- Solicitação de Cartão
- Ativação de Cartão com cadastro de senha
- Redefinição de Senha
- Bloqueio Temporário
 Cancelamento Definitivo

> **Observação:** Não há, no momento, contextos separados para Cliente, Segurança ou Transações, pois o foco está exclusivamente na gestão do cartão para manter o projeto simples e coeso.

---

### 1.2 Domínio Principal

**Domínio:** Gestão de Cartões Bancários

O sistema oferece funcionalidades relacionadas à criação, ativação, segurança e controle dos cartões, com regras específicas de negócio, tais como:

- Validação de idade mínima (≥ 18 anos)
- Validação de CPF
- Verificação de renda mínima conforme o tipo do cartão
- Geração automática do número do cartão
- Definição de status e bandeira
- Definição e alteração de senha com critérios de segurança

**Estratégia:**  
Domínio tratado como um único contexto, sem subdivisão em subdomínios, favorecendo o aprendizado e simplicidade arquitetural.

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

---

## 2. Modelagem Tática

### 2.1 Agregados

-  **Cartão (Cartao)**  
  Agregado raiz que encapsula dados e regras do ciclo de vida do cartão, incluindo:
    - Solicitação com validações (CPF, idade, renda)
    - Geração do número do cartão
    - Controle de status (Solicitado, Ativo, Bloqueado, Cancelado)
    - Armazenamento e validação da senha

> Todos os objetos de valor (CPF, RendaMensal, DataDeNascimento, Senha) são parte do agregado Cartão e não existem isoladamente.

---

### 2.2 Entidades e Value Objects

#### Entidades (com identidade própria)

| Entidade | Identificador | Descrição                      |
|----------|---------------|-------------------------------|
| `Cartao` | `UUID`        | Representa o cartão emitido    |

> Nota: A entidade `Cliente` não foi criada para simplificação; dados do cliente estão incorporados no Cartão.

#### Value Objects (sem identidade)

| VO                | Responsabilidade                                               |
|-------------------|---------------------------------------------------------------|
| `CPF`             | Validação de formato e consistência do CPF                    |
| `DataDeNascimento`| Verificação de maioridade (≥ 18 anos)                         |
| `RendaMensal`     | Representa valor positivo e verifica renda mínima por tipo    |
| `Senha`           | Validação de regras de segurança (6 dígitos, sem repetições) |

> Todos os VOs são imutáveis e validam regras ao serem criados.

---

### 2.3 Casos de Uso

| Caso de Uso                        | Descrição                                            |
|------------------------------------|------------------------------------------------------|
| `SolicitarCartaoUseCase`           | Solicitação de emissão de novo cartão                |
| `AtivarCartaoUseCase`              | Ativação do cartão e cadatro da senha após validações|
| `RedefinirSenhaUseCase`            | Redefinição da senha do cartão                       |
| `BloquearCartaoUseCase`            | Bloqueio temporário do cartão                        |
| `CancelarCartaoUseCase`            | Cancelamento permanente do cartão                    |

> Casos de uso implementados na camada de aplicação, isolando regras de negócio da infraestrutura.

---

### 2.4 Repositórios

Interface para persistência e recuperação de dados no domínio:

```java
public interface CartaoRepository {
    void salvar(Cartao cartao);
    Optional<Cartao> buscarPorNumero(String numero);
}
```

> A implementação (ImplementacaoCartaoRepositoryJpa) está na camada de infraestrutura, baseada em Spring Data JPA.
