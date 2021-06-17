package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StatisticsTest {
  @Test
  public void givenNoTransactions_whenCreating_ShouldNotThrowOrAnythingWeird() {
    Statistics.from(new ArrayList<Transaction>());
  }


  @Test
  public void givenNoTransactions_whenComputingSum_ShouldBeZero() {
    BigDecimal sum = Statistics.getSum(new ArrayList<Transaction>());
    assertThat(sum).isZero();
  }

  @Test
  public void givenOneTransaction_whenComputingSum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));

    BigDecimal sum = Statistics.getSum(transactions);

    assertThat(sum).isEqualTo(new BigDecimal(10));
  }

  @Test
  public void givenMultipleTransactions_whenComputingSum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));
    transactions.add(new Transaction(new BigDecimal(20), OffsetDateTime.now()));

    BigDecimal sum = Statistics.getSum(transactions);

    assertThat(sum).isEqualTo(new BigDecimal(30));
  }


  @Test
  public void givenNoTransactions_whenComputingAverage_ShouldBeZero() {
    Statistics statistics = Statistics.from(new ArrayList<Transaction>());
    assertThat(statistics.average).isZero();
  }

  @Test
  public void givenOneTransaction_whenComputingAverage_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));

    BigDecimal average = Statistics.getAverage(transactions);

    assertThat(average).isEqualTo(new BigDecimal(10));
  }

  @Test
  public void givenMultipleTransactions_whenComputingAverage_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));
    transactions.add(new Transaction(new BigDecimal(20), OffsetDateTime.now()));

    BigDecimal average = Statistics.getAverage(transactions);

    assertThat(average).isEqualTo(new BigDecimal(15));
  }


  @Test
  public void givenNoTransactions_whenComputingMaximum_ShouldBeZero() {
    BigDecimal maximum = Statistics.getMaximum(new ArrayList<Transaction>());
    assertThat(maximum).isZero();
  }

  @Test
  public void givenOneTransaction_whenComputingMaximum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));

    BigDecimal maximum = Statistics.getMaximum(transactions);

    assertThat(maximum).isEqualTo(new BigDecimal(10));
  }

  @Test
  public void givenMultipleTransactions_whenComputingMaximum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));
    transactions.add(new Transaction(new BigDecimal(20), OffsetDateTime.now()));

    BigDecimal maximum = Statistics.getMaximum(transactions);

    assertThat(maximum).isEqualTo(new BigDecimal(20));
  }


  @Test
  public void givenNoTransactions_whenComputingMinimum_ShouldBeZero() {
    BigDecimal minimum = Statistics.getMinimum(new ArrayList<Transaction>());
    assertThat(minimum).isZero();
  }

  @Test
  public void givenOneTransaction_whenComputingMinimum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));

    BigDecimal minimum = Statistics.getMinimum(transactions);

    assertThat(minimum).isEqualTo(new BigDecimal(10));
  }

  @Test
  public void givenMultipleTransactions_whenComputingMinimum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));
    transactions.add(new Transaction(new BigDecimal(20), OffsetDateTime.now()));

    BigDecimal minimum = Statistics.getMinimum(transactions);

    assertThat(minimum).isEqualTo(new BigDecimal(10));
  }
}
