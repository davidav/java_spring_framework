package com.example.rest.scope;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
@Slf4j
public abstract class AbstractIdHolder implements IdHolder{

    private final UUID requestId;

    public AbstractIdHolder() {
        this.requestId = UUID.randomUUID();
    }

    abstract String holderType();

    @Override
    public void logId() {
        log.info("{} is: {}", holderType(), requestId);
    }
}
