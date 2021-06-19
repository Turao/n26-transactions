package com.n26.integration.web.transaction;

import javax.validation.Valid;

import com.n26.usecases.inserttransaction.InsertTransaction;
import com.n26.usecases.inserttransaction.InsertTransactionRequest;
import com.n26.usecases.inserttransaction.MoreThanAMinuteOldException;
import com.n26.usecases.removealltransactions.RemoveAllTransactions;
import com.n26.usecases.removealltransactions.RemoveAllTransactionsRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  @ResponseStatus(value = HttpStatus.CREATED)
  public void onInsertTransactionRequest(@RequestBody @Valid InsertTransactionRequestDTO request) {
    LOGGER.info("Processing new InsertTransactionRequest: {}", request);
    insertTransaction.execute(new InsertTransactionRequest(request.getAmount(), request.getTimestamp()));
  }

  @ResponseStatus(
    value = HttpStatus.NO_CONTENT,
    reason = "Transaction is older than 60 seconds"
  )
  @ExceptionHandler(MoreThanAMinuteOldException.class)
  public void onMoreThanAMinuteOldException() {}

  @ResponseStatus(
    value = HttpStatus.UNPROCESSABLE_ENTITY, 
    reason = "Fields are not parsable or Transaction date is in the future")
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void onMethodArgumentNotValidException() {}

  @DeleteMapping
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void onRemoveAllTransactionsRequest() {
    LOGGER.info("Processing new RemoveAllTransactions request");
    removeAllTransactions.execute(new RemoveAllTransactionsRequest());
  }
}
