package com.nutrispace.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Superclasse mapeada — reutiliza a coluna NOME da tabela do astronauta sem alterar o DDL.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class PessoaBase {

	@Column(name = "NOME", length = 50)
	private String nome;
}
