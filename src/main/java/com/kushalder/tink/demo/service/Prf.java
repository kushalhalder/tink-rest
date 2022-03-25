package com.kushalder.tink.demo.service;

import com.kushalder.tink.demo.primitives.PrfPrimitive;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Prf {
  @Autowired
  private PrfPrimitive prfPrimitive;

  @PostConstruct
  public void init() throws GeneralSecurityException {
    compute("kushalder_prf");
    compute("kushalder_prf_added");
    compute("kushalder_prf_added_text");
  }

  public String compute(String plaintext) throws GeneralSecurityException {
    byte[] output = prfPrimitive.prfSet.computePrimary(
        plaintext.getBytes(StandardCharsets.UTF_8),
        plaintext.length()
    );

    String s = Base64.getEncoder().encodeToString(output);
    String s1 = new String(output);
    log.info("Prf input length: " + plaintext.length());
    log.info("Prf redacted text: " + s);

    return s;
  }

  public String compute(String plaintext, int outputLength) throws GeneralSecurityException {
    byte[] output = prfPrimitive.prfSet.computePrimary(
        plaintext.getBytes(StandardCharsets.UTF_8),
        outputLength
    );

    String s = Base64.getEncoder().encodeToString(output);
    String s1 = new String(output);
    log.info("Prf input length: " + outputLength);
    log.info("Prf redacted text: " + s);

    return s;
  }
}
