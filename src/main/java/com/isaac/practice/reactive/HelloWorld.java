package com.isaac.practice.reactive;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class HelloWorld {
    public static void main(String[] args) {
        Flux<Integer> ints = Flux.range(0, 3).map(item -> 5 / (item - 3));
        ints.subscribe(i -> log.info(i.toString()), e -> log.info(e.getMessage()),() -> log.info("finish..."), sub -> sub.request(1));
    }
}
