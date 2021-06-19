package com.n26.usecases.expiretransaction;

import com.n26.core.Command;
import com.n26.domain.transaction.TransactionRepository;
import com.n26.usecases.updatestatistics.UpdateStatistics;
import com.n26.usecases.updatestatistics.UpdateStatisticsRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExpireTransaction implements Command<ExpireTransactionRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExpireTransaction.class);

  private final TransactionRepository transactionRepository;
  private final UpdateStatistics updateStatistics;

  public ExpireTransaction(
    final TransactionRepository transactionRepository,
    final UpdateStatistics updateStatistics
  ) {
    this.transactionRepository = transactionRepository;
    this.updateStatistics = updateStatistics;
  }

  @Override
  public void execute(final ExpireTransactionRequest request) {
    LOGGER.info("Expiring Transaction: {}", request.getTransactionId());    
    transactionRepository.removeOne(request.getTransactionId());

    afterExpiration();
  }

  private void afterExpiration() {
    updateStatistics.execute(new UpdateStatisticsRequest());
  }
  
}
