package com.n26.usecases.updatestatistics;

import com.n26.domain.transaction.TransactionRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsUpdaterTest {
  
  @InjectMocks private StatisticsUpdater updateStatistics;
  @Mock private TransactionRepository transactionRepository;
  
  @Test
  public void givenAnUpdateStatisticsRequest_whenProcessing_shouldUpdateStatisticsInDatabase() {
    UpdateStatisticsRequest request = new UpdateStatisticsRequest();
    
    updateStatistics.execute(request);
    
    then(transactionRepository)
      .should(times(1))
      .updateStatistics();
  }
}
