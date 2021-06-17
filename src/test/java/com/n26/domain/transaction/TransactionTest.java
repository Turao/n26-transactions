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

    assertThat(transaction.getTransactionId()).isNotNull();
    assertThat(transaction.getAmount()).isEqualTo(amount);
    assertThat(transaction.getTimestamp()).isEqualTo(timestamp);
  }
}
