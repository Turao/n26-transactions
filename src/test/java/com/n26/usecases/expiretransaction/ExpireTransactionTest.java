package com.n26.usecases.expiretransaction;

import java.util.UUID;

import com.n26.domain.transaction.TransactionRepository;
import com.n26.usecases.updatestatistics.UpdateStatistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ExpireTransactionTest {
  
  @InjectMocks private ExpireTransaction expireTransaction;
  @Mock private TransactionRepository transactionRepository;
  @Mock private UpdateStatistics updateStatistics;
  
  @Test
  public void givenATransactionId_whenExpiring_shouldRemoveFromDatabase() {
    UUID transactionId = UUID.randomUUID();
    
    expireTransaction.execute(new ExpireTransactionRequest(transactionId));
    
    then(transactionRepository)
      .should(times(1))
      .removeOne(transactionId);
  }

  @Test
  public void givenATransactionId_whenExpiring_shouldUpdateStatistics() {
    UUID transactionId = UUID.randomUUID();

    expireTransaction.execute(new ExpireTransactionRequest(transactionId));

    then(updateStatistics)
      .should(times(1))
      .execute(any());
  }
}
