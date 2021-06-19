package com.n26.usecases.scheduletransactionforexpiration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.n26.domain.transaction.TransactionRepository;
import com.n26.usecases.updatestatistics.UpdateStatistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.after;

@RunWith(MockitoJUnitRunner.class)
public class TransactionExpirationSchedulerTest {
  
  @InjectMocks private TransactionExpirationScheduler transactionExpirationScheduler;
  @Mock private TransactionRepository transactionRepository;
  @Mock private UpdateStatistics updateStatistics;
  
  @Test
  public void givenATransactionId_whenSchedulingForExpiration_shouldWaitAndRemoveFromDatabase() {
    UUID transactionId = UUID.randomUUID();

    long timeToLive = TimeUnit.SECONDS.toMillis(1);
    transactionExpirationScheduler.execute(
      new ScheduleTransactionForExpirationRequest(transactionId, timeToLive)
    );

    then(transactionRepository)
      .should(after(timeToLive).times(1))
      .removeOne(transactionId);
  }

  @Test
  public void givenATransactionId_whenSchedulingForExpiration_shouldWaitUpdateStatistics() {
    UUID transactionId = UUID.randomUUID();

    long timeToLive = TimeUnit.SECONDS.toMillis(1);
    transactionExpirationScheduler.execute(
      new ScheduleTransactionForExpirationRequest(transactionId, timeToLive)
    );

    then(updateStatistics)
      .should(after(timeToLive).times(1))
      .execute(any());
  }
}
