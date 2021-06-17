package com.n26.infrastructure.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.n26.domain.transaction.Transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryTransactionRepositoryTest {

  @InjectMocks private InMemoryTransactionRepository repository;

  @Test
  public void givenATransaction_whenInsertingOne_shouldAddToCollection() {
    Transaction transaction = new Transaction(
      new BigDecimal("12.12345"),
      OffsetDateTime.now()
    );

    assertThat(repository.transactions).isEmpty();

    repository.insertOne(transaction);
    
    assertThat(repository.transactions).hasSize(1);
  }
}
