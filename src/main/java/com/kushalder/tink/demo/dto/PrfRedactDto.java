package com.kushalder.tink.demo.dto;

import lombok.Data;

@Data
public class PrfRedactDto {
  public String plainText;
  public int outputLength;
}
