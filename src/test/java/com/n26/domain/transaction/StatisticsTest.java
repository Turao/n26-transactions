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
    Amount sum = Statistics.computeSum(new ArrayList<Transaction>());
    assertThat(sum).isEqualTo(new Amount(0));
  }

  @Test
  public void givenOneTransaction_whenComputingSum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));

    Amount sum = Statistics.computeSum(transactions);

    assertThat(sum).isEqualTo(new Amount(10));
  }

  @Test
  public void givenMultipleTransactions_whenComputingSum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));
    transactions.add(new Transaction(new BigDecimal(20), OffsetDateTime.now()));

    Amount sum = Statistics.computeSum(transactions);

    assertThat(sum).isEqualTo(new Amount(30));
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

    Amount average = Statistics.computeAverage(transactions);

    assertThat(average).isEqualTo(new Amount(10));
  }

  @Test
  public void givenMultipleTransactions_whenComputingAverage_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));
    transactions.add(new Transaction(new BigDecimal(20), OffsetDateTime.now()));

    Amount average = Statistics.computeAverage(transactions);

    assertThat(average).isEqualTo(new Amount(15));
  }


  @Test
  public void givenNoTransactions_whenComputingMaximum_ShouldBeZero() {
    Amount maximum = Statistics.computeMaximum(new ArrayList<Transaction>());
    assertThat(maximum).isEqualTo(new Amount(0));
  }

  @Test
  public void givenOneTransaction_whenComputingMaximum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));

    Amount maximum = Statistics.computeMaximum(transactions);

    assertThat(maximum).isEqualTo(new Amount(10));
  }

  @Test
  public void givenMultipleTransactions_whenComputingMaximum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));
    transactions.add(new Transaction(new BigDecimal(20), OffsetDateTime.now()));

    Amount maximum = Statistics.computeMaximum(transactions);

    assertThat(maximum).isEqualTo(new Amount(20));
  }


  @Test
  public void givenNoTransactions_whenComputingMinimum_ShouldBeZero() {
    Amount minimum = Statistics.computeMinimum(new ArrayList<Transaction>());
    assertThat(minimum).isEqualTo(new Amount(0));
  }

  @Test
  public void givenOneTransaction_whenComputingMinimum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));

    Amount minimum = Statistics.computeMinimum(transactions);

    assertThat(minimum).isEqualTo(new Amount(10));
  }

  @Test
  public void givenMultipleTransactions_whenComputingMinimum_ShouldBeCorrect() {
    Collection<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));
    transactions.add(new Transaction(new BigDecimal(20), OffsetDateTime.now()));

    Amount minimum = Statistics.computeMinimum(transactions);

    assertThat(minimum).isEqualTo(new Amount(10));
  }
}
