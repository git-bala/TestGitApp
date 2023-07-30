package com.rest.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rest.api.ClientRestApiApplication;

@ExtendWith(SpringExtension.class)
class ClientRestApiApplicationTests {

	@Test
	void main() {
		ClientRestApiApplication.main(new String[] {});
	}

}
