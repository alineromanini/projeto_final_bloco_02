package com.generation.farmacia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.farmacia.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{ //interface herda de JpaRepsitory que irá fornecer métodos CRUD sem precisar escrever SQL
	
	public List<Categoria> findAllByDescricaoContainingIgnoreCase(String descricao); 
}
