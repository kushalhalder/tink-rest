package com.kushalder.tink.demo.config;

import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeyTemplates;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.PrimitiveSet;
import com.google.crypto.tink.Registry;
import com.google.crypto.tink.prf.Prf;
import com.google.crypto.tink.prf.PrfConfig;
import com.google.crypto.tink.prf.PrfSet;
import com.google.crypto.tink.proto.KeyStatusType;
import com.google.crypto.tink.proto.Keyset;
import com.kushalder.tink.demo.primitives.PrfPrimitive;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PrfConfiguration {

  @Autowired
  private PrfPrimitive prfPrimitive;

  @PostConstruct
  public void init() throws GeneralSecurityException, IOException {
    log.info("Initialising Tink with PRF!");
    PrfConfig.register();

    String keysetFilename = "prf_keyset.json";

    KeysetHandle keysetHandle;
    // check if file exists
    try {
      keysetHandle = CleartextKeysetHandle.read(
          JsonKeysetReader.withFile(new File(keysetFilename)));
    } catch (IOException e) {
      // else generate
      keysetHandle = KeysetHandle.generateNew(
          KeyTemplates.get("HMAC_SHA256_PRF"));
      CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(
          new File(keysetFilename)));
    }
    // load keysetHandle
    log.info(keysetHandle.toString());

    PrfSet prfSet = keysetHandle.getPrimitive(PrfSet.class);
    prfPrimitive.setPrfSet(prfSet);
  }

  public static <P> PrimitiveSet<P> createPrimitiveSet(Keyset keyset, Class<P> inputClass)
      throws GeneralSecurityException {
    PrimitiveSet<P> primitives = PrimitiveSet.newPrimitiveSet(inputClass);
    for (Keyset.Key key : keyset.getKeyList()) {
      if (key.getStatus() == KeyStatusType.ENABLED) {
        P primitive = Registry.getPrimitive(key.getKeyData(), inputClass);
        PrimitiveSet.Entry<P> entry = primitives.addPrimitive(primitive, key);
        if (key.getKeyId() == keyset.getPrimaryKeyId()) {
          primitives.setPrimary(entry);
        }
      }
    }
    return primitives;
  }
}
