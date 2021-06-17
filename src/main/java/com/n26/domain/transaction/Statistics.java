package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;

public class Statistics {
  BigDecimal sum;
  BigDecimal average;
  BigDecimal minumum;
  BigDecimal maximum;
  Long count;

  private Statistics(
    BigDecimal sum,
    BigDecimal average,
    BigDecimal minumum,
    BigDecimal maximum,
    Long count
  ) {
    this.sum = sum;
    this.average = average;
    this.minumum = minumum;
    this.maximum = maximum;
    this.count = count;
  }
  
  public static Statistics from(Collection<Transaction> transactions) {
    return new Statistics(
      getSum(transactions),
      getAverage(transactions),
      getMinimum(transactions),
      getMaximum(transactions),
      getCount(transactions)
    );
  }

  static BigDecimal getSum(Collection<Transaction> transactions) {
    return transactions.stream()
      .map(Transaction::getAmount)
      .reduce(BigDecimal::add)
      .orElseGet(() -> new BigDecimal(0));
  }

  static BigDecimal getAverage(Collection<Transaction> transactions) {
    if (transactions.isEmpty()) {
      return new BigDecimal(0);
    }
    
    return Statistics.getSum(transactions)
      .divide(new BigDecimal(transactions.size()));
  }

  static BigDecimal getMaximum(Collection<Transaction> transactions) {
    return transactions.stream()
    .map(Transaction::getAmount)
    .max(Comparator.naturalOrder())
    .orElseGet(() -> new BigDecimal(0));
  }

  static BigDecimal getMinimum(Collection<Transaction> transactions) {
    return transactions.stream()
    .map(Transaction::getAmount)
    .min(Comparator.naturalOrder())
    .orElseGet(() -> new BigDecimal(0));
  }

  static Long getCount(Collection<Transaction> transactions) {
    return new Long(transactions.size());
  }
}
