package com.github.ivansjr.tabelafipe.service;

public interface IDataConverter {

    <T> T dataConverter(String json, Class<T> tclass);

}
