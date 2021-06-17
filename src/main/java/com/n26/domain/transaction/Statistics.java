package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Statistics {
  BigDecimal sum;
  BigDecimal average;
  BigDecimal minimum;
  BigDecimal maximum;
  Long count;

  private Statistics(
    BigDecimal sum,
    BigDecimal average,
    BigDecimal minimum,
    BigDecimal maximum,
    Long count
  ) {
    this.sum = sum;
    this.average = average;
    this.minimum = minimum;
    this.maximum = maximum;
    this.count = count;
  }

  public static Statistics from(Collection<Transaction> transactions) {
    return new Statistics(
      computeSum(transactions),
      computeAverage(transactions),
      computeMinimum(transactions),
      computeMaximum(transactions),
      computeCount(transactions)
    );
  }

  static BigDecimal computeSum(Collection<Transaction> transactions) {
    return transactions.stream()
      .map(Transaction::getAmount)
      .reduce(BigDecimal::add)
      .orElseGet(() -> new BigDecimal(0));
  }

  static BigDecimal computeAverage(Collection<Transaction> transactions) {
    if (transactions.isEmpty()) {
      return new BigDecimal(0);
    }
    
    return Statistics.computeSum(transactions)
      .divide(new BigDecimal(transactions.size()));
  }

  static BigDecimal computeMaximum(Collection<Transaction> transactions) {
    return transactions.stream()
    .map(Transaction::getAmount)
    .max(Comparator.naturalOrder())
    .orElseGet(() -> new BigDecimal(0));
  }

  static BigDecimal computeMinimum(Collection<Transaction> transactions) {
    return transactions.stream()
    .map(Transaction::getAmount)
    .min(Comparator.naturalOrder())
    .orElseGet(() -> new BigDecimal(0));
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
