package com.n26.integration.web.transaction;

import com.n26.usecases.inserttransaction.InsertTransaction;
import com.n26.usecases.inserttransaction.InsertTransactionRequest;
import com.n26.usecases.removealltransactions.RemoveAllTransactions;
import com.n26.usecases.removealltransactions.RemoveAllTransactionsRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transactions")
public class TransactionController {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

  private final InsertTransaction insertTransaction;
  private final RemoveAllTransactions removeAllTransactions;

  public TransactionController(
    final InsertTransaction insertTransaction,
    final RemoveAllTransactions removeAllTransactions
  ) {
    this.insertTransaction = insertTransaction;
    this.removeAllTransactions = removeAllTransactions;
  }

  @PostMapping
  public void onInsertTransactionRequest(@RequestBody InsertTransactionRequestDTO request) {
    LOGGER.info("Processing new InsertTransactionRequest: {}", request);
    insertTransaction.execute(new InsertTransactionRequest(request.getAmount(), request.getTimestamp()));
  }

  @DeleteMapping
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void onRemoveAllTransactionsRequest() {
    LOGGER.info("Processing new RemoveAllTransactions request");
    removeAllTransactions.execute(new RemoveAllTransactionsRequest());
  }
}
