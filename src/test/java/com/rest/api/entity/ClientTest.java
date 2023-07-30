package com.rest.api.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rest.api.entity.Client;

@ExtendWith(SpringExtension.class)
class ClientTest {

	@Test
	void testClient() {
		Client client = new Client(1L, "Bala", "India", "example.com");
		assertEquals("Bala", client.getName());
		assertTrue(client.toString().contains("Client("));
	}

}
