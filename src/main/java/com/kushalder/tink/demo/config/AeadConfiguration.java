package com.kushalder.tink.demo.config;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeyTemplates;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.kushalder.tink.demo.primitives.AeadPrimitive;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AeadConfiguration {

  @Autowired
  private AeadPrimitive aeadPrimitive;

  @PostConstruct
  public void init() throws GeneralSecurityException, IOException {
    log.info("Initialising Tink with AEAD!");
    AeadConfig.register();

    String keysetFilename = "aead_keyset.json";

    KeysetHandle keysetHandle;
    // check if file exists
    try {
      keysetHandle = CleartextKeysetHandle.read(
          JsonKeysetReader.withFile(new File(keysetFilename)));
    } catch (IOException e) {
      // else generate
      keysetHandle = KeysetHandle.generateNew(
          KeyTemplates.get("AES128_GCM"));
      CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(
          new File(keysetFilename)));
    }
    // load keysetHandle
    log.info(keysetHandle.toString());

    aeadPrimitive.setAaed(keysetHandle.getPrimitive(Aead.class));
  }
}
