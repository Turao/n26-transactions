package com.n26.usecases.getstatistics;

import java.util.Collection;

import com.n26.core.Query;
import com.n26.domain.transaction.Statistics;
import com.n26.domain.transaction.Transaction;
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

    LOGGER.info("Retrieving last minute transactions...");
    Collection<Transaction> transactions = transactionRepository.getLastMinuteTransactions();

    LOGGER.info("Compiling statistics...");
    Statistics statistics = Statistics.from(transactions);

    return new GetStatisticsResponse(statistics);
  }  
}
