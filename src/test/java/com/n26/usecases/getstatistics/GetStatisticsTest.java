package com.n26.usecases.getstatistics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.n26.domain.transaction.Statistics;
import com.n26.domain.transaction.Transaction;
import com.n26.domain.transaction.TransactionRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetStatisticsTest {
  @InjectMocks private GetStatistics getStatistics;
  @Mock private TransactionRepository transactionRepository;

  @Test
  public void givenAGetStatisticsRequest_whenProcessing_shouldReturnStatisticsFromLastMinuteTransactions() {
    GetStatisticsRequest request = new GetStatisticsRequest();

    Collection<Transaction> lastMinuteTransactions = new ArrayList<>();
    lastMinuteTransactions.add(new Transaction(new BigDecimal(10), OffsetDateTime.now()));
    lastMinuteTransactions.add(new Transaction(new BigDecimal(20), OffsetDateTime.now()));
    lastMinuteTransactions.add(new Transaction(new BigDecimal(30), OffsetDateTime.now()));

    given(transactionRepository.getStatistics())
      .willReturn(Statistics.from(lastMinuteTransactions));
    
    GetStatisticsResponse response = getStatistics.execute(request);

    Statistics statistics = response.getStatistics();
    
    // damn! got so proud of look how nice this assertion reads
    assertThat(statistics).isEqualTo(Statistics.from(lastMinuteTransactions));
  }
}
