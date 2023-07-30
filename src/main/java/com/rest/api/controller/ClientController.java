package com.rest.api.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.api.dto.ClientDTO;
import com.rest.api.entity.Client;
import com.rest.api.service.ClientService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/v1/client")
public class ClientController {

	private static final String ID = "/{id}";

	public final ClientService clientService;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}
	
	@Transactional
	@ApiOperation(value="Register a new customer")
	@PostMapping(path = "/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveClient(@RequestBody @Valid Client client) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Client clientCreated = clientService.save(client);
		map.put("status", HttpStatus.CREATED.value());
		map.put("message", "The record has been saved successfully!");
		map.put("dateTime", LocalDateTime.now());
		map.put("body", clientCreated);
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	@ApiOperation(value="List client by code")
	@GetMapping(path = {ID})
	public ResponseEntity<ClientDTO> getClient(@PathVariable long id){
		return ResponseEntity.ok().body(mapper.map(clientService.findById(id), ClientDTO.class));
	}
	
	@ApiOperation(value="List all clients")
	@GetMapping(path = "/listAll")
	public ResponseEntity<List<ClientDTO>> getAllClients(){
		return ResponseEntity.ok()
				.body(clientService.findAll()
						.stream().map(c -> mapper.map(c, ClientDTO.class)).collect(Collectors.toList()));
	}
	
	@Transactional
	@ApiOperation(value="Update client")
	@PutMapping(value=ID)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody @Valid Client client) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		client.setId(id);
		Client clientUpdated = clientService.updateClient(client);
		map.put("status", HttpStatus.OK.value());
		map.put("message", "Record has been updated successfully!");
		map.put("dateTime", LocalDateTime.now());
		map.put("body", clientUpdated);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@Transactional
	@ApiOperation(value="Delete client")
	@DeleteMapping(path ={ID})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity <?> delete(@PathVariable long id) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		clientService.deleteClient(id);
		map.put("status", HttpStatus.NO_CONTENT.value());
		map.put("message", "Record has been deleted successfully!");
		map.put("dateTime", LocalDateTime.now());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
}
