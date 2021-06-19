package com.n26.integration.web.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Data
@AllArgsConstructor
public class InsertTransactionRequestDTO {
  @NotNull private BigDecimal amount;
  @NotNull @PastOrPresent private OffsetDateTime timestamp; 
}
