package com.generation.farmacia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.farmacia.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	List<Produto> findAllByNomeContainingIgnoreCase(String nome); 
	
	List<Produto> findByPrecoGreaterThanOrderByPreco(double preco); // Busca todos os produtos com preço maior que um determinado valor, ordenando em ordem crescente de preço

	List<Produto> findByPrecoLessThanOrderByPrecoDesc(double preco);// Busca todos os produtos com preço menor que um determinado valor, ordenando em ordem decrescente de preço

}