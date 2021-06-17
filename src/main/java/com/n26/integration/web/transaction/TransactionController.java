package com.n26.integration.web.transaction;

import com.n26.usecases.inserttransaction.InsertTransaction;
import com.n26.usecases.inserttransaction.InsertTransactionRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transactions")
public class TransactionController {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

  private final InsertTransaction insertTransaction;

  public TransactionController(final InsertTransaction insertTransaction) {
    this.insertTransaction = insertTransaction;
  }

  @PostMapping
  public void onInsertTransactionRequest(InsertTransactionRequestDTO request) {
    LOGGER.info("Processing new InsertTransactionRequest: {}", request);
    insertTransaction.execute(new InsertTransactionRequest(request.getAmount(), request.getTimestamp()));
  }
}
