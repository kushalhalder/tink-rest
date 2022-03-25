package com.kushalder.tink.demo.dto;

import lombok.Data;

@Data
public class AeadEncryptDto {
  public String plainText;
  public String associatedData;
}
