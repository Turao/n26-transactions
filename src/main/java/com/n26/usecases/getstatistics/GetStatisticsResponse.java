package com.n26.usecases.getstatistics;

import com.n26.domain.transaction.Statistics;

public class GetStatisticsResponse {
  Statistics statistics;

  public GetStatisticsResponse(final Statistics statistics) {
    this.statistics = statistics;
  }

  public Statistics getStatistics() {
    return statistics;
  }
}
