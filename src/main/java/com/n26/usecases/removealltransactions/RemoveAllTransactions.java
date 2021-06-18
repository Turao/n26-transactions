package com.n26.usecases.removealltransactions;

import com.n26.core.Command;
import com.n26.domain.transaction.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RemoveAllTransactions implements Command<RemoveAllTransactionsRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoveAllTransactions.class);

  private final TransactionRepository transactionRepository;

  public RemoveAllTransactions(final TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public void execute(RemoveAllTransactionsRequest request) {
    LOGGER.info("Removing all transactions...");
    transactionRepository.removeAll();
  }
  
}
