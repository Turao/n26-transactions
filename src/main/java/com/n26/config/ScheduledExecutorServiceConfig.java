package com.n26.config;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduledExecutorServiceConfig {

  @Value("${executors.scheduler.corePoolSize}")
  private int corePoolSize;

  @Bean
  public ScheduledExecutorService scheduledExecutor() {
      return new ScheduledThreadPoolExecutor(corePoolSize);
  }

}
