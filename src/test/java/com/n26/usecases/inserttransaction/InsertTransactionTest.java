package com.n26.usecases.inserttransaction;

import com.n26.domain.transaction.TransactionRepository;
import com.n26.usecases.scheduletransactionforexpiration.ScheduleTransactionForExpiration;
import com.n26.usecases.updatestatistics.UpdateStatistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.BDDMockito.then;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class InsertTransactionTest {
  
  @InjectMocks private InsertTransaction insertTransaction;
  @Mock private TransactionRepository transactionRepository;
  @Mock private ScheduleTransactionForExpiration scheduleTransactionForExpiration;
  @Mock private UpdateStatistics updateStatistics;

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

  @Test
  public void givenAnInsertTransactionRequest_whenProcessing_shouldScheduleTransactionForExpiration() {
    InsertTransactionRequest request = new InsertTransactionRequest(
      new BigDecimal("12.345"), 
      OffsetDateTime.now()
    );

    insertTransaction.execute(request);

    then(scheduleTransactionForExpiration)
      .should(times(1))
      .execute(any());
  }

  @Test
  public void givenAnInsertTransactionRequest_whenProcessing_shouldUpdateStatistics() {
    InsertTransactionRequest request = new InsertTransactionRequest(
      new BigDecimal("12.345"), 
      OffsetDateTime.now()
    );

    insertTransaction.execute(request);

    then(updateStatistics)
      .should(times(1))
      .execute(any());
  }

  @Test
  public void givenAnInsertTransactionRequestWithAnOldTransaction_whenProcessing_shouldThrow() {
    InsertTransactionRequest request = new InsertTransactionRequest(
      new BigDecimal("12.345"), 
      OffsetDateTime.now().minusSeconds(61)
    );

    assertThatThrownBy(() -> insertTransaction.execute(request))
      .isInstanceOf(MoreThanAMinuteOldException.class);
  }
}
