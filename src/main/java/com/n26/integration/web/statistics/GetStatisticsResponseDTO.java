package com.n26.integration.web.statistics;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetStatisticsResponseDTO {
  private BigDecimal sum;
  private BigDecimal avg;
  private BigDecimal max;
  private BigDecimal min;
  private Long count;
}
