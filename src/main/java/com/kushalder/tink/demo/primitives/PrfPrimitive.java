package com.kushalder.tink.demo.primitives;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.prf.Prf;
import com.google.crypto.tink.prf.PrfSet;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PrfPrimitive {
  public PrfSet prfSet;
}
