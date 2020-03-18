package com.cybg.email.repository;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class EmailRecordRepositoryTests {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EmailRecordRepository emailRecordRepo;
	
	@Test
	public void testFindById() {
		EmailRecord emailRecord = new EmailRecord(1, "last");
		entityManager.persist(emailRecord);
		
		Optional<EmailRecord> emailRecord1 = emailRecordRepo.findById(Long.valueOf(1));

		//assertThat(emailRecord1).extracting(EmailRecord::getId).containsOnly(emailRecord.getId());
	}
}
