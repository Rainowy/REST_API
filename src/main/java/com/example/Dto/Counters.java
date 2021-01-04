package com.example.Dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
public class Counters {

    private String id;
    private long seq;
}



