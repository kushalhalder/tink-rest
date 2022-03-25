package com.kushalder.tink.demo.controller;

import com.kushalder.tink.demo.dto.AeadEncryptDto;
import com.kushalder.tink.demo.dto.Output;
import com.kushalder.tink.demo.dto.PrfRedactDto;
import com.kushalder.tink.demo.service.Aead;
import com.kushalder.tink.demo.service.Prf;
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
@RequestMapping("/api/v1/prf")
public class PrfController {
  @Autowired
  public Prf prfService;

  @PostMapping("/redact")
  public Mono<ResponseEntity<Output>> encrypt(@Validated @RequestBody PrfRedactDto prfRedactDto) {
    return Mono.fromCallable(() -> prfService.compute(prfRedactDto.plainText, prfRedactDto.outputLength))
        .map(Output::new)
        .map(ResponseEntity::ok)
        .subscribeOn(Schedulers.boundedElastic());
  }
}
