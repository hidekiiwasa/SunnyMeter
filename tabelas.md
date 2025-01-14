CREATE TABLE clientes(
id varchar(36) PRIMARY KEY NOT NULL,
nome varchar(50) NOT NULL,
endereco varchar(100) NOT NULL,
documento varchar(25) NOT NULL,
tipo char(2) NOT NULL,
cep varchar(10) NOT NULL,
ativo boolean DEFAULT TRUE
);

CREATE TABLE instalacoes(
id varchar(36) PRIMARY KEY NOT NULL,
endereco varchar(100) NOT NULL,
cep varchar(10) NOT NULL,
ativo boolean DEFAULT TRUE
);

CREATE TABLE contratos (
id varchar(36) PRIMARY KEY NOT NULL,
id_cliente varchar(36) NOT NULL UNIQUE,
id_instalacao varchar(36) NOT NULL UNIQUE,
data_de_inicio long NOT NULL,
duracao INT NOT NULL,
ativo boolean DEFAULT TRUE,
FOREIGN KEY (id_cliente) references clientes(id),
FOREIGN KEY (id_instalacao) references instalacoes(id)
);

CREATE TABLE consumos (
id varchar(36) PRIMARY KEY NOT NULL,
id_instalacao VARCHAR(36) NOT NULL,
consumo_kwh float NOT NULL,
med_timestamp INT NOT NULL,
FOREIGN KEY (id_instalacao) REFERENCES instalacoes(id)
ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE producoes(
id varchar(36) PRIMARY KEY NOT NULL,
id_instalacao varchar(36) NOT NULL,
producao_kwh float NOT NULL,
med_timestamp INT NOT NULL,
FOREIGN KEY (id_instalacao) REFERENCES instalacoes(id)
ON DELETE CASCADE ON UPDATE CASCADE
);