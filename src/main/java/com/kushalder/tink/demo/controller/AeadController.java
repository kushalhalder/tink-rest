package com.kushalder.tink.demo.controller;

import com.kushalder.tink.demo.dto.AeadDecryptDto;
import com.kushalder.tink.demo.dto.AeadEncryptDto;
import com.kushalder.tink.demo.dto.Output;
import com.kushalder.tink.demo.service.Aead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/v1/aaed")
public class AeadController {
  @Autowired
  public Aead aeadService;

  @PostMapping("/encrypt")
  public Mono<ResponseEntity<Output>> encrypt(@Validated @RequestBody AeadEncryptDto aeadEncryptDto) {
    return Mono.fromCallable(() -> aeadService.encrypt(aeadEncryptDto.plainText, aeadEncryptDto.associatedData))
        .map(Output::new)
        .map(ResponseEntity::ok)
        .subscribeOn(Schedulers.boundedElastic());
  }

  @PostMapping("/decrypt")
  public Mono<ResponseEntity<Output>> decrypt(@Validated @RequestBody AeadDecryptDto aeadDecryptDto) {
    return Mono.fromCallable(()-> aeadService.decrypt(aeadDecryptDto.cipherText, aeadDecryptDto.associatedData))
        .map(Output::new)
        .map(ResponseEntity::ok)
        .subscribeOn(Schedulers.boundedElastic());
  }
}
