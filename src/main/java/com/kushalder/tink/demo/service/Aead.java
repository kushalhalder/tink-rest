package com.kushalder.tink.demo.service;

import com.kushalder.tink.demo.primitives.AeadPrimitive;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Aead {
  @Autowired
  private AeadPrimitive aeadPrimitive;

  @PostConstruct
  public void init() throws GeneralSecurityException {
    encrypt("kushalder_aead", "tink");
  }

  public String encrypt(String plaintext, String aad) throws GeneralSecurityException {
    byte[] ciphertext = aeadPrimitive.aaed.encrypt(
        plaintext.getBytes(StandardCharsets.UTF_8),
        aad.getBytes(StandardCharsets.UTF_8)
    );

    log.info("Aead CipherText: " + Base64.getEncoder().encodeToString(ciphertext));

    return Base64.getEncoder().encodeToString(ciphertext);
  }

  public String decrypt(String cipherText, String aad) throws GeneralSecurityException {
    byte[] decrypted =
        aeadPrimitive.aaed.decrypt(cipherText.getBytes(), aad.getBytes(StandardCharsets.UTF_8));
    return new String(decrypted);
  }
}
