package com.reminson.baozi_store_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.reminson.baozi_store_api.model.Produto;
import com.reminson.baozi_store_api.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoRepository repository;

	public ProdutoController(ProdutoRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto criar(@Valid @RequestBody Produto produto) {
		produto.setId(null);
		return repository.save(produto);
	}

	@GetMapping
	public List<Produto> listar() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Produto consultar(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}

	@PutMapping("/{id}")
	public Produto atualizar(@PathVariable Long id, @Valid @RequestBody Produto dados) {
		Produto produto = consultar(id);
		produto.setNome(dados.getNome());
		produto.setPreco(dados.getPreco());
		produto.setEstoque(dados.getEstoque());
		return repository.save(produto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void apagar(@PathVariable Long id) {
		Produto produto = consultar(id);
		repository.delete(produto);
	}
}
