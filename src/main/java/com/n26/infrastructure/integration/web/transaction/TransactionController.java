package com.n26.infrastructure.integration.web.transaction;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.usecases.inserttransaction.InsertTransaction;
import com.n26.usecases.inserttransaction.InsertTransactionRequest;
import com.n26.usecases.inserttransaction.MoreThanAMinuteOldException;
import com.n26.usecases.removealltransactions.RemoveAllTransactions;
import com.n26.usecases.removealltransactions.RemoveAllTransactionsRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
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

  @ExceptionHandler
  public void onException(final HttpMessageConversionException exception, HttpServletResponse response) throws IOException {
    Throwable cause = exception.getCause();
    if (cause instanceof InvalidFormatException || cause instanceof DateTimeParseException) {
      // ! if the request body JSON IS parseable (i.e. is a valid JSON), but not deserializable (e.g. invalid date format)
      // ! @Valid annotation will throw and return BAD_REQUEST
      // ! unless we implement this workaround
      response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage());
    } else {
      response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
  }

  @DeleteMapping
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void onRemoveAllTransactionsRequest() {
    LOGGER.info("Processing new RemoveAllTransactions request");
    removeAllTransactions.execute(new RemoveAllTransactionsRequest());
  }
}
