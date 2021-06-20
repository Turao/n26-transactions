package com.n26.infrastructure.integration.web.statistics;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import com.n26.domain.transaction.Statistics;
import com.n26.domain.transaction.Transaction;
import com.n26.usecases.getstatistics.GetStatistics;
import com.n26.usecases.getstatistics.GetStatisticsResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {
  @Autowired private MockMvc mvc;
  
  @MockBean private GetStatistics getStatistics;

  @Test
  public void onGetStatisticsRequest_whenProcessing_shouldGetStatistics() throws Exception {
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .get("/statistics");

    Statistics statistics = Statistics.from(new ArrayList<Transaction>());
    given(getStatistics.execute(any()))
      .willReturn(new GetStatisticsResponse(statistics));

    mvc.perform(request)
      .andExpect(status().isOk());
  }
}
