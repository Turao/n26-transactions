package com.n26.usecases.removealltransactions;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.n26.domain.transaction.TransactionRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RemoveAllTransactionsTest {
  
  @InjectMocks private RemoveAllTransactions removeAllTransactions;
  @Mock private TransactionRepository transactionRepository;

  @Test
  public void givenARemoveAllTransactionsRequest_whenProcessing_shouldRemoveAllFromDatabase() {
    RemoveAllTransactionsRequest request = new RemoveAllTransactionsRequest();

    removeAllTransactions.execute(request);

    then(transactionRepository)
      .should(times(1))
      .removeAll();
  }
}
