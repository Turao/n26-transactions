package com.n26.integration.web.statistics;

import com.n26.usecases.getstatistics.GetStatistics;
import com.n26.usecases.getstatistics.GetStatisticsRequest;
import com.n26.usecases.getstatistics.GetStatisticsResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/statistics")
public class StatisticsController {
  private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

  private final GetStatistics getStatistics;

  public StatisticsController(final GetStatistics getStatistics) {
    this.getStatistics = getStatistics;
  }

  @GetMapping
  public GetStatisticsResponseDTO onGetStatisticsRequest() {
    LOGGER.info("Processing new GetStatistics request");
    GetStatisticsResponse response = getStatistics.execute(new GetStatisticsRequest());

    return new GetStatisticsResponseDTO(
      response.getStatistics().getSum().toString(),
      response.getStatistics().getAverage().toString(),
      response.getStatistics().getMaximum().toString(),
      response.getStatistics().getMinimum().toString(),
      response.getStatistics().getCount()
    );
  }
}
