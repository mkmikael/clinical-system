SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `smedim` ;
CREATE SCHEMA IF NOT EXISTS `java.smedim` DEFAULT CHARACTER SET utf8 ;
USE `smedim` ;

-- -----------------------------------------------------
-- Table `smedim`.`convenio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smedim`.`convenio` ;

CREATE TABLE IF NOT EXISTS `java.smedim`.`convenio` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `smedim`.`servico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smedim`.`servico` ;

CREATE TABLE IF NOT EXISTS `java.smedim`.`servico` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `smedim`.`agenda`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smedim`.`agenda` ;

CREATE TABLE IF NOT EXISTS `java.smedim`.`agenda` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome_cliente` VARCHAR(45) NOT NULL,
  `marcado` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `servico` INT(11) NOT NULL,
  `convenio` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_agenda_servico1_idx` (`servico` ASC),
  INDEX `fk_agenda_convenio1_idx` (`convenio` ASC),
  CONSTRAINT `fk_agenda_convenio1`
    FOREIGN KEY (`convenio`)
    REFERENCES `java.smedim`.`convenio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_agenda_servico1`
    FOREIGN KEY (`servico`)
    REFERENCES `java.smedim`.`servico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `smedim`.`cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smedim`.`cliente` ;

CREATE TABLE IF NOT EXISTS `java.smedim`.`cliente` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(60) NOT NULL,
  `endereco` VARCHAR(80) NOT NULL,
  `nascimento` DATE NOT NULL,
  `fone1` VARCHAR(20) NULL DEFAULT NULL,
  `fone2` VARCHAR(20) NULL DEFAULT NULL,
  `convenio` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cliente_convenio1_idx` (`convenio` ASC),
  CONSTRAINT `fk_cliente_convenio1`
    FOREIGN KEY (`convenio`)
    REFERENCES `java.smedim`.`convenio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `smedim`.`prontuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smedim`.`prontuario` ;

CREATE TABLE IF NOT EXISTS `java.smedim`.`prontuario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `antecedentes_pessoais` VARCHAR(150) NOT NULL,
  `antecedentes_familiares` VARCHAR(150) NOT NULL,
  `cliente` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_prontuario_cliente1_idx` (`cliente` ASC),
  CONSTRAINT `fk_prontuario_cliente1`
    FOREIGN KEY (`cliente`)
    REFERENCES `java.smedim`.`cliente` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `smedim`.`servico_convenio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smedim`.`servico_convenio` ;

CREATE TABLE IF NOT EXISTS `java.smedim`.`servico_convenio` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `preco` DOUBLE NOT NULL,
  `servico` INT(11) NOT NULL,
  `convenio` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tabela_de_preco_servico1_idx` (`servico` ASC),
  INDEX `fk_servico_convenio_convenio1_idx` (`convenio` ASC),
  CONSTRAINT `fk_servico_convenio_convenio1`
    FOREIGN KEY (`convenio`)
    REFERENCES `java.smedim`.`convenio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tabela_de_preco_servico1`
    FOREIGN KEY (`servico`)
    REFERENCES `java.smedim`.`servico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `smedim`.`sub_prontuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smedim`.`sub_prontuario` ;

CREATE TABLE IF NOT EXISTS `java.smedim`.`sub_prontuario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `queixa_principal` VARCHAR(150) NOT NULL,
  `historico_atual` VARCHAR(150) NOT NULL,
  `exame_clinico` VARCHAR(150) NOT NULL,
  `impressao_diagnostica` VARCHAR(150) NOT NULL,
  `conduta_terapeutica` VARCHAR(150) NOT NULL,
  `evolucao` VARCHAR(150) NOT NULL,
  `atendimento` DATETIME NOT NULL,
  `prontuario` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sub_prontuario_prontuario1_idx` (`prontuario` ASC),
  CONSTRAINT `fk_sub_prontuario_prontuario1`
    FOREIGN KEY (`prontuario`)
    REFERENCES `java.smedim`.`prontuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `smedim`.`usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smedim`.`usuario` ;

CREATE TABLE IF NOT EXISTS `java.smedim`.`usuario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `login` VARCHAR(40) NOT NULL,
  `senha` VARCHAR(30) NOT NULL,
  `perfil` CHAR(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `smedim`.`faturamento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smedim`.`faturamento` ;

CREATE TABLE IF NOT EXISTS `java.smedim`.`faturamento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_do_faturamento` DATE NOT NULL,
  `num_de_atendimento` INT NOT NULL,
  `preco` DOUBLE NOT NULL,
  `servico_convenio` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_faturamento_servico_convenio1_idx` (`servico_convenio` ASC),
  CONSTRAINT `fk_faturamento_servico_convenio1`
    FOREIGN KEY (`servico_convenio`)
    REFERENCES `java.smedim`.`servico_convenio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
