package com.kushalder.tink.demo.dto;

import lombok.Data;

@Data
public class AeadDecryptDto {
  public String cipherText;
  public String associatedData;
}
