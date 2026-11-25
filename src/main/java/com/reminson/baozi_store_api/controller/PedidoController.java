package com.reminson.baozi_store_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.reminson.baozi_store_api.model.Cliente;
import com.reminson.baozi_store_api.model.Pedido;
import com.reminson.baozi_store_api.model.Produto;
import com.reminson.baozi_store_api.repository.ClienteRepository;
import com.reminson.baozi_store_api.repository.PedidoRepository;
import com.reminson.baozi_store_api.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoRepository pedidoRepository;
	private final ClienteRepository clienteRepository;
	private final ProdutoRepository produtoRepository;

	public PedidoController(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
			ProdutoRepository produtoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido criar(@Valid @RequestBody Pedido pedido) {
		if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o ID do cliente");
		}
		if (pedido.getProduto() == null || pedido.getProduto().getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o ID do produto");
		}

		Long clienteId = pedido.getCliente().getId();
		Long produtoId = pedido.getProduto().getId();
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
		Produto produto = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

		pedido.setId(null);
		pedido.setCliente(cliente);
		pedido.setProduto(produto);
		return pedidoRepository.save(pedido);
	}

	@GetMapping
	public List<Pedido> listar() {
		return pedidoRepository.findAll();
	}

	@GetMapping("/{id}")
	public Pedido consultar(@PathVariable Long id) {
		return pedidoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void apagar(@PathVariable Long id) {
		pedidoRepository.delete(consultar(id));
	}
}
