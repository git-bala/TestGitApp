package com.rest.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.api.entity.Client;
import com.rest.api.repository.ClientRepository;
import com.rest.api.service.exception.ResourceNotFoundException;

@Service
public class ClientService {
	
	private static final String NO_RECORD_FOUND = "No record found";
	private static final String CLIENT_NOT_FOUND = "Client not found whit id: ";
	private static final String CPF_ALREADY_REGISTERED_IN_THE_SYSTEM = "CPF already registered in the system";
	
	@Autowired
	ClientRepository clientRepository;

	public Client save(Client client) {
		return clientRepository.save(client);
	}

	public Client findById(long id) {
		 Optional<Client> client = clientRepository.findById(id);
		 return client.orElseThrow(() -> new ResourceNotFoundException(CLIENT_NOT_FOUND + id));
	}
	
	public List<Client> findAll() {
		List<Client> clientList = clientRepository.findAll();
		
		if(clientList.isEmpty()) {
			throw new ResourceNotFoundException(NO_RECORD_FOUND);
		}
		return clientList;
	}

	public Client updateClient(Client client) {
		Client record = clientRepository.findById(client.getId())
				.orElseThrow(() -> new ResourceNotFoundException(CLIENT_NOT_FOUND + client.getId()));
		
		record.setName(client.getName());
		record.setAddress(client.getAddress());

		return clientRepository.save(record);
	}

	public void deleteClient(long id) {
		Client record = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CLIENT_NOT_FOUND + id));
		clientRepository.delete(record);
	}
	


}
