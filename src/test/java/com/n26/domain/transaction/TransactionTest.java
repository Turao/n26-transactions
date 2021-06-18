package com.n26.domain.transaction;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TransactionTest {
  @Test
  public void canCreate() {
    BigDecimal amount = new BigDecimal("12.3343");
    OffsetDateTime timestamp = OffsetDateTime.now();

    Transaction transaction = new Transaction(
      amount,
      timestamp
    );

    assertThat(transaction.transactionId).isNotNull();
    assertThat(transaction.amount).isEqualTo(amount);
    assertThat(transaction.timestamp).isEqualTo(timestamp);
  }

  @Test
  public void givenOlderTimestamp_whenAskingIfTransactionIsOlder_shouldReturnFalse() {
    BigDecimal amount = new BigDecimal("12.3343");
    OffsetDateTime timestamp = OffsetDateTime.now();
    OffsetDateTime olderTimestamp = OffsetDateTime.now().minusMinutes(1);

    Transaction transaction = new Transaction(
      amount,
      timestamp
    );

    assertThat(transaction.isOlderThan(olderTimestamp)).isFalse();
  }

  @Test
  public void givenNewerTimestamp_whenAskingIfTransactionIsOlder_shouldReturnFalse() {
    BigDecimal amount = new BigDecimal("12.3343");
    OffsetDateTime timestamp = OffsetDateTime.now();
    OffsetDateTime newerTimestamp = OffsetDateTime.now().plusMinutes(1);

    Transaction transaction = new Transaction(
      amount,
      timestamp
    );

    assertThat(transaction.isOlderThan(newerTimestamp)).isTrue();
  }
}
