package com.cybg.email.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface EmailRecordRepository extends CrudRepository<EmailRecord, Long> {
	
	Optional<EmailRecord> findById(Long id);
}
