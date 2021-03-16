package com.msa.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {

  @Value("${msa.value}")
  private String value;

  @GetMapping("/value")
  public String getConfig() {
    return value;
  }
}
