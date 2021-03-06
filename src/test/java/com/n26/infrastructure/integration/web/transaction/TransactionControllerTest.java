package com.n26.infrastructure.integration.web.transaction;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.usecases.inserttransaction.InsertTransaction;
import com.n26.usecases.inserttransaction.MoreThanAMinuteOldException;
import com.n26.usecases.removealltransactions.RemoveAllTransactions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.willThrow;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private InsertTransaction insertTransaction;
  @MockBean private RemoveAllTransactions removeAllTransactions;

  @Test
  public void onInsertTransactionRequest_shouldInsertTransaction() throws Exception {
    InsertTransactionRequestDTO body = new InsertTransactionRequestDTO(
      new BigDecimal("123.123"),
      OffsetDateTime.parse("2018-07-17T09:59:51.312Z")
    );

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .post("/transactions")
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(objectMapper.writeValueAsString(body));

    mvc.perform(request)
      .andExpect(status().isCreated());
  }

  @Test
  public void onInsertTransactionRequestWithTransactionInTheFuture_shouldReturnUnprocessable() throws Exception {
    InsertTransactionRequestDTO body = new InsertTransactionRequestDTO(
      new BigDecimal("123.123"),
      OffsetDateTime.now().plusSeconds(1)
    );

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .post("/transactions")
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(objectMapper.writeValueAsString(body));

    mvc.perform(request)
      .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void onInsertTransactionRequestWithTransactionOlderThanAMinute_shouldReturnNoContent() throws Exception {
    InsertTransactionRequestDTO body = new InsertTransactionRequestDTO(
      new BigDecimal("123.123"),
      OffsetDateTime.now().minusMinutes(2)
    );

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
    .post("/transactions")
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .content(objectMapper.writeValueAsString(body));
    
    willThrow(MoreThanAMinuteOldException.class)
      .given(insertTransaction)
      .execute(any());
    
    mvc.perform(request)
      .andExpect(status().isNoContent());
  }

  @Test
  public void onRemoveAllTransactionsRequest_shouldRemoveAllTransactions() throws Exception {
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders
      .delete("/transactions");

    mvc.perform(request)
      .andExpect(status().isNoContent());
  }
}
