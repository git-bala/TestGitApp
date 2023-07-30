package com.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.api.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


}
