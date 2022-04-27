package com.alvin.jwttokenapp.model.dto;

import lombok.Data;

@Data
public class GenderPredictionResponse {
    public String name;
    public String gender;
    public double probability;
    public int count;
}
