package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;

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

  @Override // auto-generated
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((average == null) ? 0 : average.hashCode());
    result = prime * result + ((count == null) ? 0 : count.hashCode());
    result = prime * result + ((maximum == null) ? 0 : maximum.hashCode());
    result = prime * result + ((minimum == null) ? 0 : minimum.hashCode());
    result = prime * result + ((sum == null) ? 0 : sum.hashCode());
    return result;
  }

  @Override // auto-generated
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Statistics other = (Statistics) obj;
    if (average == null) {
      if (other.average != null)
        return false;
    } else if (!average.equals(other.average))
      return false;
    if (count == null) {
      if (other.count != null)
        return false;
    } else if (!count.equals(other.count))
      return false;
    if (maximum == null) {
      if (other.maximum != null)
        return false;
    } else if (!maximum.equals(other.maximum))
      return false;
    if (minimum == null) {
      if (other.minimum != null)
        return false;
    } else if (!minimum.equals(other.minimum))
      return false;
    if (sum == null) {
      if (other.sum != null)
        return false;
    } else if (!sum.equals(other.sum))
      return false;
    return true;
  }
}
