IF NOT EXISTS(SELECT * FROM sysobjects WHERE name='usuarios' and xtype='U')

create table usuarios(
id BIGINT NOT NULL IDENTITY (1,1) PRIMARY KEY,
nome VARCHAR(45) NOT NULL,
sobrenome VARCHAR(45) NOT NULL,
email VARCHAR(45) NOT NULL,
password VARCHAR(200) NOT NULL,
tipo_principal_usuario VARCHAR(45) NULL,
data_nascimento DATETIME NULL,
cod_status_usuario BIT NULL,
UNIQUE(email)
)
