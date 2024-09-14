package com.github.ivansjr.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VehicleData(
    @JsonAlias("modelos") List<VehicleModelData> models
) {}
