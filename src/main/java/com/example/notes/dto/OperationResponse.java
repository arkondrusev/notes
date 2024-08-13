package com.example.notes.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder(setterPrefix = "set")
public class OperationResponse {

    public static final Integer RESULT_CODE__OK = 0;
    public static final Integer RESULT_CODE__ERROR = 1;

    public static final String RESULT_MESSAGE__OK = "OK";

    private Integer resultCode;
    private String resultMessage;

    public static OperationResponse ok() {
        return new OperationResponse(RESULT_CODE__OK, RESULT_MESSAGE__OK);
    }

    public static OperationResponse error(String errorMessage) {
        return new OperationResponse(RESULT_CODE__ERROR, errorMessage);
    }

}
