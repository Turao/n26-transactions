package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Statistics {
  final BigDecimal sum;
  final BigDecimal average;
  final BigDecimal minimum;
  final BigDecimal maximum;
  final Long count;

  private Statistics(
    BigDecimal sum,
    BigDecimal average,
    BigDecimal minimum,
    BigDecimal maximum,
    Long count
  ) {
    this.sum = sum.setScale(2, RoundingMode.HALF_UP);
    this.average = average.setScale(2, RoundingMode.HALF_UP);
    this.minimum = minimum.setScale(2, RoundingMode.HALF_UP);
    this.maximum = maximum.setScale(2, RoundingMode.HALF_UP);
    this.count = count;
  }

  public static Statistics from(Collection<Transaction> transactions) {
    return new Statistics(
      computeSum(transactions).asBigDecimal(),
      computeAverage(transactions).asBigDecimal(),
      computeMinimum(transactions).asBigDecimal(),
      computeMaximum(transactions).asBigDecimal(),
      computeCount(transactions)
    );
  }

  static Amount computeSum(Collection<Transaction> transactions) {
    return transactions.stream()
      .map(Transaction::getAmount)
      .reduce(Amount::add)
      .orElseGet(() -> new Amount(0));
  }

  static Amount computeAverage(Collection<Transaction> transactions) {
    if (transactions.isEmpty()) {
      return new Amount(0);
    }

    return Statistics
      .computeSum(transactions)
      .divide(new Amount(transactions.size()));
  }

  static Amount computeMaximum(Collection<Transaction> transactions) {
    return transactions.stream()
    .map(Transaction::getAmount)
    .max(Comparator.naturalOrder())
    .orElseGet(() -> new Amount(0));
  }

  static Amount computeMinimum(Collection<Transaction> transactions) {
    return transactions.stream()
    .map(Transaction::getAmount)
    .min(Comparator.naturalOrder())
    .orElseGet(() -> new Amount(0));
  }

  static Long computeCount(Collection<Transaction> transactions) {
    return new Long(transactions.size());
  }

  public BigDecimal getSum() {
    return sum;
  }
  
  public BigDecimal getAverage() {
    return average;
  }

  public BigDecimal getMaximum() {
    return maximum;
  }

  public BigDecimal getMinimum() {
    return minimum;
  }

  public Long getCount() {
    return count;
  }
}
