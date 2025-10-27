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

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController //Controlador REST
@RequestMapping("/categorias") //define a URL base para todos os endpoints da classe
@CrossOrigin(origins = "*", allowedHeaders = "*") //Permite que qualquer aplicação acesse os endpoints e também permite todos os cabeçalhos

public class CategoriaController {
	
	@Autowired //Injeta automaticamente uma instancia de TemaRepository
	private CategoriaRepository categoriaRepository;
	
	//Lista todos os temas
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll(){
		return ResponseEntity.ok(categoriaRepository.findAll()); //Retorna status 200 com os dados no corpo da resposta
	}

	//Busca tema por ID
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id){ //Captura o id da URL
		return categoriaRepository.findById(id) //retorna um Optional<Tema> (pode existir ou não)
				.map(resposta -> ResponseEntity.ok(resposta))//Se existir, retorna status 200 
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());//se não existir retorna 404 NOT FOUND
	}
	
	//Busca por descrição	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Categoria>> getAllByDescricao(@PathVariable String descricao){
		return ResponseEntity.ok(categoriaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	//Cria nova categoria
	@PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
		categoria.setId(null);//garante que o ID seja nulo para o banco gerar automaticamente
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
	}
	
	//Atualiza categoria
	@PutMapping
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
		return categoriaRepository.findById(categoria.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(categoriaRepository.save(categoria)))
						.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	//Deleta categoria
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		if(categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		categoriaRepository.deleteById(id);
	}
	
}
