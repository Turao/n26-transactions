package com.n26.infrastructure.transaction;

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

    repository.transactions.add(lessThanAMinuteOldTransaction);

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

    repository.transactions.add(moreThanAMinuteOldTransaction);

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

    repository.transactions.add(lessThanAMinuteOldTransaction);
    repository.transactions.add(moreThanAMinuteOldTransaction);

    Collection<Transaction> lastMinuteTransactions = repository.getLastMinuteTransactions();
    
    assertThat(lastMinuteTransactions)
      .contains(lessThanAMinuteOldTransaction)
      .doesNotContain(moreThanAMinuteOldTransaction);
  }

  @Test
  public void givenSomeTransactions_whenRemovingLastMinuteTransactions_shouldRemoveThem() {
    Transaction lessThanAMinuteOldTransaction = new Transaction(
      new BigDecimal("12.12345"),
      OffsetDateTime.now().minusSeconds(30)
    );
    
    Transaction moreThanAMinuteOldTransaction = new Transaction(
      new BigDecimal("12.12345"),
      OffsetDateTime.now().minusSeconds(61)
    );

    repository.transactions.add(lessThanAMinuteOldTransaction);
    repository.transactions.add(moreThanAMinuteOldTransaction);

    repository.removeLastMinuteTransactions();
    
    assertThat(repository.transactions)
      .contains(lessThanAMinuteOldTransaction)
      .doesNotContain(moreThanAMinuteOldTransaction);
  }
}
