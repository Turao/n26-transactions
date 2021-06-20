package com.n26.usecases.scheduletransactionforexpiration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.n26.usecases.expiretransaction.ExpireTransaction;
import com.n26.usecases.expiretransaction.ExpireTransactionRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionExpirationSchedulerTest {
  
  @InjectMocks private TransactionExpirationScheduler transactionExpirationScheduler;
  @Mock private ScheduledExecutorService scheduler;
  @Mock private ExpireTransaction expireTransaction;
  
  @Test
  public void givenATransaction_whenSchedulingForExpiration_shouldScheduleForExpiration() {
    UUID transactionId = UUID.randomUUID();
    OffsetDateTime timestamp = OffsetDateTime.now().minusSeconds(59);

    transactionExpirationScheduler.execute(
      new ScheduleTransactionForExpirationRequest(transactionId, timestamp)
    );

    then(scheduler)
      .should(times(1))
      .schedule(any(Runnable.class), any(long.class), any(TimeUnit.class));
  }

  @Test
  public void givenATransactionThatShouldHaveBeenExpiredAlready_whenSchedulingForExpiration_shouldExpire() {
    UUID transactionId = UUID.randomUUID();
    OffsetDateTime timestamp = OffsetDateTime.now().minusSeconds(61);

    transactionExpirationScheduler.execute(
      new ScheduleTransactionForExpirationRequest(transactionId, timestamp)
    );

    then(expireTransaction)
      .should(times(1))
      .execute(any());
  }
}
