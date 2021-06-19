package com.n26.domain.transaction;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

public class AmountTest {
  
  @Test
  public void givenABigDecimal_whenCreating_shouldCreate() {
    Amount amount = new Amount(new BigDecimal(1));
    assertThat(amount.value).isEqualTo(new BigDecimal("1.00"));
  }

  @Test
  public void givenANegativeBigDecimal_whenCreating_shouldThrow() {
    BigDecimal negativeValue = new BigDecimal(-1);
    assertThatThrownBy(() -> new Amount(negativeValue))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void givenAnInteger_whenCreating_shouldCreate() {
    Amount amount = new Amount(1);
    assertThat(amount.value).isEqualTo(new BigDecimal("1.00"));
  }

  @Test
  public void givenANegativeInteger_whenCreating_shouldThrow() {
    assertThatThrownBy(() -> new Amount(-1))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void hasScale2() {
    Amount amount = new Amount(2);
    assertThat(amount.value).isEqualTo(new BigDecimal(2).setScale(2));
  }

  @Test
  public void roundsHalfUp() {
    Amount amount = new Amount(new BigDecimal("1.999"));
    assertThat(amount.value).isEqualTo(new BigDecimal("2.00"));
  }

  @Test
  public void canAdd() {
    Amount a = new Amount(new BigDecimal(1));
    Amount b = new Amount(new BigDecimal(1));
    
    Amount resul = a.add(b);
    assertThat(resul).isEqualTo(new Amount(2));
  }

  @Test
  public void canSubtract() {
    Amount a = new Amount(new BigDecimal(1));
    Amount b = new Amount(new BigDecimal(1));
    
    Amount result = a.subtract(b);
    assertThat(result).isEqualTo(new Amount(0));
  }

  @Test
  public void canDivide() {
    Amount a = new Amount(new BigDecimal(3));
    Amount b = new Amount(new BigDecimal(2));
    
    Amount result = a.divide(b);
    assertThat(result).isEqualTo(new Amount(new BigDecimal("1.5")));
  }

  @Test
  public void givenAnAmount_whenGettingAsBigDecimal_shouldReturnBigDecimal() {
    Amount a = new Amount(new BigDecimal(0));
    
    BigDecimal value = a.asBigDecimal();
    assertThat(value).isZero();
  }
}
