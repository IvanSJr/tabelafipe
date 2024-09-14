package com.github.ivansjr.tabelafipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverterService implements IDataConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T dataConverter(String json, Class<T> tclass) {
        try {
            return objectMapper.readValue(json, tclass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
