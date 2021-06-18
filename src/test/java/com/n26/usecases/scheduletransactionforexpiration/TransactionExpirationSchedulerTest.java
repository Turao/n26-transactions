package com.n26.usecases.scheduletransactionforexpiration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.n26.domain.transaction.TransactionRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.after;

@RunWith(MockitoJUnitRunner.class)
public class TransactionExpirationSchedulerTest {
  
  @InjectMocks private TransactionExpirationScheduler transactionExpirationScheduler;
  @Mock private TransactionRepository transactionRepository;
  
  @Test
  public void givenATransactionId_whenSchedulingForExpiration_shouldWaitAndRemoveFromDatabase() {
    UUID transactionId = UUID.randomUUID();

    transactionExpirationScheduler.execute(
      new ScheduleTransactionForExpirationRequest(transactionId, TimeUnit.SECONDS.toMillis(1))
    );

    then(transactionRepository)
      .should(after(TimeUnit.MINUTES.toMillis(1)).times(1))
      .removeOne(transactionId);
  }
}
