package com.n26.infrastructure.integration.web.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetStatisticsResponseDTO {
  private String sum;
  private String avg;
  private String max;
  private String min;
  private Long count;
}
