package com.n26.usecases.inserttransaction;

import com.n26.core.Command;
import com.n26.domain.transaction.Transaction;
import com.n26.domain.transaction.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InsertTransaction implements Command<InsertTransactionRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InsertTransaction.class);

  private final TransactionRepository transactionRepository;

  public InsertTransaction(final TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public void execute(final InsertTransactionRequest request) {
    LOGGER.debug("Inserting Transaction...");
    Transaction transaction = new Transaction(
      request.getAmount(),
      request.getTimestamp()
    );
    
    transactionRepository.insertOne(transaction);
    LOGGER.debug("Transaction inserted");
  }

}
