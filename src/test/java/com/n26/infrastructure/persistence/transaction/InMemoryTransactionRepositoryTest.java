package com.n26.infrastructure.persistence.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;

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

  @Test
  public void givenATransactionLessThanOneMinuteOld_whenGettingLastMinuteTransactions_shouldGetTransaction() {
    Transaction lessThanAMinuteOldTransaction = new Transaction(
      new BigDecimal("12.12345"),
      OffsetDateTime.now()
    );

    repository.transactions.put(
      lessThanAMinuteOldTransaction.getTransactionId(),
      lessThanAMinuteOldTransaction
    );

    Collection<Transaction> lastMinuteTransactions = repository.getLastMinuteTransactions();
    
    assertThat(lastMinuteTransactions)
      .contains(lessThanAMinuteOldTransaction);
  }

  @Test
  public void givenNoTransactionLessThanOneMinuteOld_whenGettingLastMinuteTransactions_shouldGetNothing() {
    Transaction moreThanAMinuteOldTransaction = new Transaction(
      new BigDecimal("12.12345"),
      OffsetDateTime.now().minusSeconds(61)
    );

    repository.transactions.put(
      moreThanAMinuteOldTransaction.getTransactionId(),  
      moreThanAMinuteOldTransaction
    );

    Collection<Transaction> lastMinuteTransactions = repository.getLastMinuteTransactions();
    
    assertThat(lastMinuteTransactions).isEmpty();
  }

  @Test
  public void givenSomeTransactionLessThanOneMinuteOld_whenGettingLastMinuteTransactions_shouldGetThem() {
    Transaction lessThanAMinuteOldTransaction = new Transaction(
      new BigDecimal("12.12345"),
      OffsetDateTime.now().minusSeconds(30)
    );
    
    Transaction moreThanAMinuteOldTransaction = new Transaction(
      new BigDecimal("12.12345"),
      OffsetDateTime.now().minusSeconds(61)
    );

    repository.transactions.put(
      lessThanAMinuteOldTransaction.getTransactionId(),
      lessThanAMinuteOldTransaction
    );
    
    repository.transactions.put(
      moreThanAMinuteOldTransaction.getTransactionId(),
      moreThanAMinuteOldTransaction
    );


    Collection<Transaction> lastMinuteTransactions = repository.getLastMinuteTransactions();
    
    assertThat(lastMinuteTransactions)
      .contains(lessThanAMinuteOldTransaction)
      .doesNotContain(moreThanAMinuteOldTransaction);
  }

  @Test
  public void givenATransactionId_whenRemovingOneTransaction_shouldRemoveFromDatabase() {
    Transaction transaction = new Transaction(
      new BigDecimal("12.12345"),
      OffsetDateTime.now()
    );

    repository.transactions.put(transaction.getTransactionId(), transaction);

    assertThat(repository.transactions).hasSize(1);
    
    repository.removeOne(transaction.getTransactionId());
    
    assertThat(repository.transactions).isEmpty();
  }
}
