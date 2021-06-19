package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Amount implements Comparable<Amount> {

  public static final int SCALE = 2;
  public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
  
  BigDecimal value;
  
  public Amount(final BigDecimal value) {
    if (value.longValue() < 0) throw new IllegalArgumentException("Amount value must be positive");

    this.value = value.setScale(SCALE, ROUNDING_MODE);
  }

  public Amount(final int value) {
    if (value < 0) throw new IllegalArgumentException("Amount value must be positive");

    this.value = new BigDecimal(value).setScale(SCALE, ROUNDING_MODE);
  }

  @Override // auto-generated
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    Amount other = (Amount) obj;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }

  public Amount add(Amount other) {
    return new Amount(value.add(other.value));
  }

  public Amount subtract(Amount other) {
    return new Amount(value.subtract(other.value));
  }

  public Amount divide(Amount other) {
    return new Amount(value.divide(other.value, SCALE, ROUNDING_MODE));
  }

  public BigDecimal asBigDecimal() {
    return new BigDecimal(0).add(value);
  }

  @Override
  public int compareTo(Amount other) {
    return value.compareTo(other.value);
  }

}
