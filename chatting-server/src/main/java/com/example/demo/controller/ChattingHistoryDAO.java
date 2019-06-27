package com.example.demo.controller;

import com.example.demo.model.ChattingMessage;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ChattingHistoryDAO {
    //최대 20개의 메시지를 10분동안 저장
    private final Cache<UUID, ChattingMessage> chatHistoryCache = CacheBuilder
            .newBuilder().maximumSize(20).expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    public void save(ChattingMessage chatObj) {
        this.chatHistoryCache.put(UUID.randomUUID(), chatObj);
    }

    public List<ChattingMessage> get() {
        return chatHistoryCache.asMap().values().stream()
                .sorted(Comparator.comparing(ChattingMessage::getTimeStamp))
                .collect(Collectors.toList());
    }

}