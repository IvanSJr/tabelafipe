package com.github.ivansjr.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record VehicleModelData(
    @JsonAlias("nome") String name,
    @JsonAlias("codigo") String code
) {}
