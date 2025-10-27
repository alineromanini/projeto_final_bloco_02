package com.generation.farmacia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}

	//Procura por ID
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	//Procura por título
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getAllByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	//Criar produto
	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto){ //Valid valida algumas regras q estão na Model; RequestBody pega postagem dentro do Body
		if(categoriaRepository.existsById(produto.getCategoria().getId())) {
		produto.setId(null); //seta o id como nulo para não criar nenhum ID aqui
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria não existe", null );
	}
	
	//Atualiza produto
	@PutMapping 
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto){
		
		if (produtoRepository.existsById(produto.getId())) {
			
			if (categoriaRepository.existsById(produto.getCategoria().getId())) {
			
			return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
			}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria não existe", null );

	}
	return ResponseEntity.notFound().build();
	}

	//Deleta produto
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		Optional<Produto> produto = produtoRepository.findById(id); //verifica se o produto existe
		if(produto.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND); //se nao encontrar o produto vai mostrar a msg NOT FOUND
		
		produtoRepository.deleteById(id); //se encontrar vai deletar
		
	}
	
	//Busca todos os produtos com preço maior que um determinado valor, ordenando em ordem crescente de preço
	@GetMapping("/preco-maior/{preco}")
	public ResponseEntity<List<Produto>> getByPrecoMaior(@PathVariable double preco) {
	    return ResponseEntity.ok(produtoRepository.findByPrecoGreaterThanOrderByPreco(preco));
	}
	
	//Busca todos os produtos com preço menor que um determinado valor, ordenando em ordem decrescente de preço
		@GetMapping("/preco-menor/{preco}")
		public ResponseEntity<List<Produto>> getByPrecoMenor(@PathVariable double preco) {
		    return ResponseEntity.ok(produtoRepository.findByPrecoLessThanOrderByPrecoDesc(preco));
		}
	
}
