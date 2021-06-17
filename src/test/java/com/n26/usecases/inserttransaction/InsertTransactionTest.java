package com.n26.usecases.inserttransaction;

import com.n26.domain.transaction.TransactionRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class InsertTransactionTest {
  
  @InjectMocks private InsertTransaction insertTransaction;
  @Mock private TransactionRepository transactionRepository;

  @Test
  public void givenAnInsertTransactionRequest_whenProcessing_shouldInsertToDatabase() {
    InsertTransactionRequest request = new InsertTransactionRequest(
      new BigDecimal("12.345"), 
      OffsetDateTime.now()
    );

    insertTransaction.execute(request);

    then(transactionRepository)
      .should(times(1))
      .insertOne(any());
  }
}
