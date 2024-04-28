package org.acme.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter @Setter @Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AppResponse<T> {
    private Boolean success;
    private String errorCode;
    private Long count;
    private T data;
}
