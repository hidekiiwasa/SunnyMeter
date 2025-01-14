# Sobr

<p>Aplicação para expor um conjunto de endpoints de monitoração de energia elétrica. Contendo as seguintes funções: </p>

1. Cadastrar, listar, buscar e deletar clientes
2. Cadastrar, listar, buscar e deletar instalações
3. Cadastrar, listar, buscar e deletar contratos
4. Cadastrar e calcular o consumo de energia mensal em kW/h de um cliente
5. Cadastrar a produção de energia mensal em kW/h de um cliente

---

# Tabelas

## Cliente

```sql
CREATE TABLE cliente (
  id VARCHAR(36) PRIMARY KEY NOT NULL,
  nome VARCHAR(50) NOT NULL,
  endereco VARCHAR(100) NOT NULL,
  documento VARCHAR(25) NOT NULL,
  tipo CHAR(2) NOT NULL,
  cep VARCHAR(10) NOT NULL,
  ativo BOOLEAN DEFAULT TRUE
);
```
## Instalacão

```sql
CREATE TABLE instalacao(
    id varchar(36) PRIMARY KEY NOT NULL,
    endereco varchar(100) NOT NULL,
    cep varchar(10) NOT NULL,
    ativo boolean DEFAULT TRUE
);
```

## Contrato

```sql
CREATE TABLE contrato (
    id varchar(36) PRIMARY KEY NOT NULL,
    id_cliente varchar(36) NOT NULL UNIQUE,
    id_instalacao varchar(36) NOT NULL UNIQUE,
    data_de_inicio long NOT NULL,
    duracao INT NOT NULL,
    ativo boolean DEFAULT TRUE,
    FOREIGN KEY (id_cliente) references cliente(id),
    FOREIGN KEY (id_instalacao) references instalacao(id)
);
```

## Consumo

```sql
CREATE TABLE consumo (
    id varchar(36) PRIMARY KEY NOT NULL,
    id_instalacao VARCHAR(36) NOT NULL,
    consumo_kwh DOUBLE NOT NULL,
    med_timestamp INT NOT NULL,
    FOREIGN KEY (id_instalacao) REFERENCES instalacao(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);
```

## Produção

```sql
CREATE TABLE producao(
    id varchar(36) PRIMARY KEY NOT NULL,
    id_instalacao varchar(36) NOT NULL,
    producao_kwh DOUBLE NOT NULL,
    med_timestamp INT NOT NULL,
    FOREIGN KEY (id_instalacao) REFERENCES instalacao(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);
```

---

# Camadas da Aplicação

1. **Controller**  
   Camada que gerencia os endpoints de cada entidade e seus métodos como cadastrar, listar, deletar, entre outros.

2. **Service**  
   Camada para aplicar as regras de negócio, seus métodos e validações.

---

# Classes Gerais

- **DadosCadastroNomeDaEntidade**  
  DTO para receber os dados de cadastro da entidade que chegaram do endpoint POST. A classe serve para normalizar e validar os dados.

- **DadosListagemNomeDaEntidade**  
  DTO que retorna os dados de uma entidade para listar todos os cadastros e seus valores processados no service.

- **RetornoNomeDaEntidadeDTO**  
  DTO para retornar os dados cadastrados com as chaves no formato solicitado na requisição.

- **ErroNomeDaClasseErro**  
  DTO para retornar um erro com seu código e descrição.

- **NomeDaClasseRepository**  
  Classe para comunicação com o banco de dados. Geralmente possui métodos para buscar por ID e realizar paginação. Algumas entidades podem ter métodos específicos para buscas relacionadas a outras entidades.

---

# Cliente

- A classe principal de cliente possui dois métodos importantes:
    - **Formatar endereço**
    - **Verificar se o documento é um CPF ou CNPJ**

- A entidade cliente tem um relacionamento 1 para 1 com a entidade contrato.

- **Tipo**  
  Enum para normalizar o tipo de cliente, podendo ser:
    - **PF**: Pessoa Física
    - **PJ**: Pessoa Jurídica

---

# Instalação

- A classe principal de instalação possui:
    - Método para **formatar o endereço**
    - Método para **excluir a instalação**
    - Atributos próprios da entidade

---

# Endereço

- **Endereco** possui apenas um DTO para garantir que campos como `logradouro`, `cidade` e `estado` estejam sempre presentes.
    - Informações adicionais podem ser opcionais.
- **IMPORTANTE**: tudo que tiver endereço deve se mandar no seguinte formato:
  "endereco": {
  "logradouro": "Rua jaracatia",
  "cidade": "São Paulo",
  "estado": "Sãsdfsfdo"
  }.
  - Bairro e complemento são opcionais.
---

# Contrato

- Para cadastrar um contrato:
    - O `timeframe` deve ser múltiplo de **90**, caso contrário retorna o erro `INVALID_TIMEFRAME`.
    - Deve possuir um cliente e uma instalação já existentes.

- Regras para listagem e busca por ID:
    - A cada busca, verifica se o contrato expirou comparando o tempo de contrato com a data atual em *timestamp*.
    - Caso tenha expirado, o valor de `ativo` será atualizado para **false** no banco de dados.

---

# Consumo

- Para cadastrar um consumo:
    1. Verificar se o contrato existe.
    2. Buscar outros relatórios de consumo associados ao contrato.
    3. Validar se:
        - O último registro tem um consumo maior que o enviado pelo usuário.
        - O `timestamp` do registro enviado é maior que o último.
    - Em ambos os casos, retorna um erro.

- **Calcular o consumo**:
    - Percorre a lista de registros de consumo do mês atual.
    - Realiza os cálculos conforme os requisitos do endpoint.

---

# Produção

- **Produção** segue regras similares às de consumo, porém no contexto de produção de recursos.
