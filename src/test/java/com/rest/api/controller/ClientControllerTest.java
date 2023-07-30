package com.rest.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rest.api.controller.ClientController;
import com.rest.api.dto.ClientDTO;
import com.rest.api.entity.Client;
import com.rest.api.service.ClientService;

@ExtendWith(SpringExtension.class)
class ClientControllerTest {

	private static final Long ID = 1L;
	private static final String NAME = "Bala";
	private static final String ADDRESS = "india";
	private static final String EMAIL = "example@gmail.com";
	
	@InjectMocks
	private ClientController controller;
	
	@Mock
	private ClientService service;
	
	@Mock
	private ModelMapper mapper;
	
	private Client client;
	private ClientDTO clientDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startClient();
	}
	
	private void startClient() {
		client = new Client(ID, NAME, ADDRESS, EMAIL);
		clientDTO = new ClientDTO(ID, NAME, ADDRESS, EMAIL);
	}

	@Test
	void whenFindByIdThenReturnSuccess() {
		when(service.findById(anyLong())).thenReturn(client);
		when(mapper.map(any(), any())).thenReturn(clientDTO);
		
		ResponseEntity<ClientDTO> response = controller.getClient(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ClientDTO.class, response.getBody().getClass());
		
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(ADDRESS, response.getBody().getAddress());
		assertEquals(EMAIL, response.getBody().getEmail());
	}
	
	@Test
	void whenFindAllThenReturnAnListOfClientDTO() {
		when(service.findAll()).thenReturn(List.of(client));
		when(mapper.map(any(), any())).thenReturn(clientDTO);
		
		ResponseEntity<List<ClientDTO>> response = controller.getAllClients();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
	}
	
	@Test
	void whenSaveThenReturnCreated() {
		when(service.save(any())).thenReturn(client);
		ResponseEntity<?> response = controller.saveClient(client);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}
	
	@Test
	void whenUpdateThenReturnCuccess() {
		when(service.updateClient(client)).thenReturn(client);
		ResponseEntity<?> response = controller.update(ID, client);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
	}
	
	@Test
	void whenDeleteThenReturnSuccess() {
		doNothing().when(service).deleteClient(anyLong());
		
		ResponseEntity<?> response = controller.delete(ID);
		
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		verify(service, times(0)).deleteClient(anyLong());
	}
	
}
