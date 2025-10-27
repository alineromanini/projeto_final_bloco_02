package com.generation.farmacia.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity //Diz ao JPA/Hibernate que a classe Categoria é uma entidade, ou seja, será mapeada para uma tabela no banco de dados
@Table(name = "tb_categorias")

public class Categoria {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) //auto incremento
	private Long id;  //Hibernate gera o valor; Long corresponde bem ao BIGINT no banco
	
	@Column(length = 200)
	@NotBlank(message = "O atributo descrição é obrigatório!")
	@Size(min = 2, max = 500, message = "O atributo descrição deve conter no mínimo 10 e no máximo 500 caracteres")
	
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

		
	
}