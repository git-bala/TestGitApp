package com.rest.api.service;

import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rest.api.entity.Client;
import com.rest.api.repository.ClientRepository;
import com.rest.api.service.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
class ClientServiceTest {

	private static final String CLIENT_NOT_FOUND = "Client not found whit id: ";
	private static final String NO_RECORD_FOUND = "No record found";
	private static final int INDEX = 0;
	private static final Long ID = 1L;
	private static final String NAME = "Bala";
	private static final String ADDRESS = "India";
	private static final String EMAIL = "example.com";

	@InjectMocks
	private ClientService service;
	
	@Mock
	private ClientRepository repository;
	
	private Client client;
	private Optional<Client> optionalClient;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startClient();
		
	}

	private void startClient() {
		client = new Client(ID, NAME,  ADDRESS, EMAIL);
		optionalClient = Optional.of(new Client(1L, NAME, ADDRESS, EMAIL));
	}
	
	@Test
	void whenFindByIdThenReturnAnClientInstance() {
		when(repository.findById(anyLong())).thenReturn(optionalClient);
		Client response = service.findById(ID);
		
		assertNotNull(response);
		assertEquals(Client.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(ADDRESS, response.getAddress());
		assertEquals(EMAIL, response.getEmail());
	}
	
	@Test
	void whenFindByIdThenReturnAnClientNotFoundException() {
		when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException(CLIENT_NOT_FOUND + ID));

		try {
			service.findById(ID);
		} catch (Exception e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
			assertEquals(CLIENT_NOT_FOUND + ID, e.getMessage());
		}
	}
	
	@Test
	void whenFindAllThenReturnAnListOfClients() {
		when(repository.findAll()).thenReturn(List.of(client));
		List<Client> response = service.findAll();
		
		assertNotNull(response);
		assertEquals(1,  response.size());
		assertEquals(Client.class, response.get(INDEX).getClass());
		assertEquals(ID, response.get(INDEX).getId());
		assertEquals(NAME, response.get(INDEX).getName());
		assertEquals(ADDRESS, response.get(INDEX).getAddress());
		assertEquals(EMAIL, response.get(INDEX).getEmail());
	}
	
	@Test
	void whenFindAllThenReturnAnListOfClientsNotFoundException() {
		when(repository.findAll()).thenReturn(List.of(client)).thenThrow(new ResourceNotFoundException(NO_RECORD_FOUND));

		try {
			service.findAll();
		} catch (Exception e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
			assertEquals(NO_RECORD_FOUND, e.getMessage());
		}
	}
	
	@Test
	void whenSaveThenReturnSuccess() {
		
		when(repository.save(any())).thenReturn(client);
		Client response = service.save(client);
		
		assertNotNull(response);
		assertEquals(Client.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(ADDRESS, response.getAddress());
		assertEquals(EMAIL, response.getEmail());
	}
	
	
	
	@Test
	void whenDeleteWithSuccess() {
		
		when(repository.findById(anyLong())).thenReturn(optionalClient);
		doNothing().when(repository).deleteById(anyLong());
		
		service.deleteClient(ID);
		verify(repository, times(0)).deleteById(anyLong());
		
	}
	
	@Test
	void whenDeleteWithClientNotFoundException() {
		when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException(CLIENT_NOT_FOUND + ID));
		
		try {
			client.setId(3L);
			service.deleteClient(ID);
			
		} catch (Exception e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
			assertEquals(CLIENT_NOT_FOUND + ID, e.getMessage());
		}
	}
	
	@Test
	void whenUpdateThenReturnSuccess() {
		when(repository.findById(anyLong())).thenReturn(optionalClient);
		when(repository.save(any())).thenReturn(client);
		
		optionalClient.get().setName(NAME + " Updated");
		Client response = service.updateClient(optionalClient.get());
		when(repository.save(any())).thenReturn(optionalClient.get());
		
		assertNotNull(response);
		assertEquals(Client.class, response.getClass());
		assertEquals(NAME + " Updated", optionalClient.get().getName());
	}
	
	@Test
	void whenUpdateWithClientNotFoundException() {
		when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException(CLIENT_NOT_FOUND + ID));
		try {
			client.setId(3L);
			service.updateClient(optionalClient.get());
			
		} catch (Exception e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
			assertEquals(CLIENT_NOT_FOUND + ID, e.getMessage());
		}
	}

}
