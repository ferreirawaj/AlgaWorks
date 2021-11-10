package com.ferreirawaj.algalog.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreirawaj.algalog.domain.exception.NegocioException;
import com.ferreirawaj.algalog.domain.model.Cliente;
import com.ferreirawaj.algalog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoClienteService {

	private ClienteRepository clienteReposity;

	public Cliente buscar(Long clienteId) {
		return clienteReposity.findById(clienteId)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
	}

	@Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailEmUso = clienteReposity.findByEmail(cliente.getEmail()).stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente));

		if (emailEmUso) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}

		return clienteReposity.save(cliente);
	}

	@Transactional
	public void excluir(Long clienteId) {
		clienteReposity.deleteById(clienteId);
	}

}
