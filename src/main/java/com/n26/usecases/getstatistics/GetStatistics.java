package com.n26.usecases.getstatistics;

import com.n26.core.Query;
import com.n26.domain.transaction.Statistics;
import com.n26.domain.transaction.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetStatistics implements Query<GetStatisticsRequest, GetStatisticsResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(GetStatistics.class);

  private final TransactionRepository transactionRepository;

  public GetStatistics(final TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public GetStatisticsResponse execute(GetStatisticsRequest request) {
    LOGGER.info("Getting statistics...");
    Statistics statistics = transactionRepository.getStatistics();
    return new GetStatisticsResponse(statistics);
  }  
}
