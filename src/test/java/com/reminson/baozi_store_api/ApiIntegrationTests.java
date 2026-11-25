package com.reminson.baozi_store_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiIntegrationTests {

	private final HttpClient httpClient = HttpClient.newHttpClient();

	@Value("${local.server.port}")
	private int port;

	@Test
	void supportsCustomerProductAndOrderEndpoints() throws Exception {
		HttpResponse<String> clienteResponse = request("POST", "/clientes",
				"""
				{"nome":"Reminson 5287384","clienteDesde":"2026-11-24"}
				""");
		assertEquals(201, clienteResponse.statusCode());
		assertTrue(clienteResponse.body().contains("Reminson 5287384"));
		long clienteId = idFrom(clienteResponse.body());

		assertEquals(200, request("GET", "/clientes", null).statusCode());
		assertEquals(200, request("GET", "/clientes/" + clienteId, null).statusCode());
		HttpResponse<String> clienteAtualizado = request("PUT", "/clientes/" + clienteId,
				"""
				{"nome":"Reminson atualizado","clienteDesde":"2026-11-24"}
				""");
		assertEquals(200, clienteAtualizado.statusCode());
		assertTrue(clienteAtualizado.body().contains("Reminson atualizado"));

		HttpResponse<String> produtoResponse = request("POST", "/produtos",
				"""
				{"nome":"Pãozinho chinês Baozi","preco":5.00,"estoque":true}
				""");
		assertEquals(201, produtoResponse.statusCode());
		long produtoId = idFrom(produtoResponse.body());

		assertEquals(200, request("GET", "/produtos", null).statusCode());
		assertEquals(200, request("GET", "/produtos/" + produtoId, null).statusCode());
		assertEquals(200, request("PUT", "/produtos/" + produtoId,
				"""
				{"nome":"Pãozinho chinês Baozi","preco":5.00,"estoque":true}
				""").statusCode());

		HttpResponse<String> pedidoResponse = request("POST", "/pedidos",
				"""
				{"cliente":{"id":%d},"produto":{"id":%d},"quantidade":5}
				""".formatted(clienteId, produtoId));
		assertEquals(201, pedidoResponse.statusCode());
		assertTrue(pedidoResponse.body().contains("\"quantidade\":5"));
		long pedidoId = idFrom(pedidoResponse.body());

		assertEquals(200, request("GET", "/pedidos", null).statusCode());
		assertEquals(200, request("GET", "/pedidos/" + pedidoId, null).statusCode());
		assertEquals(204, request("DELETE", "/pedidos/" + pedidoId, null).statusCode());
		assertEquals(404, request("GET", "/pedidos/" + pedidoId, null).statusCode());
		assertEquals(204, request("DELETE", "/produtos/" + produtoId, null).statusCode());
		assertEquals(204, request("DELETE", "/clientes/" + clienteId, null).statusCode());
	}

	private HttpResponse<String> request(String method, String path, String body) throws Exception {
		HttpRequest.Builder builder = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:" + port + path));
		if (body == null) {
			builder.method(method, HttpRequest.BodyPublishers.noBody());
		} else {
			builder.header("Content-Type", "application/json")
					.method(method, HttpRequest.BodyPublishers.ofString(body));
		}
		return httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
	}

	private long idFrom(String json) {
		Matcher matcher = Pattern.compile("\"id\"\\s*:\\s*(\\d+)").matcher(json);
		if (!matcher.find()) {
			throw new AssertionError("A resposta não contém o ID do recurso: " + json);
		}
		return Long.parseLong(matcher.group(1));
	}
}
