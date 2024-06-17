package com.example.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationResponse {

    public static final Integer RESULT_CODE__OK = 0;
    public static final Integer RESULT_CODE__ERROR = 1;

    public static final String RESULT_MESSAGE__OK = "OK";

    @NonNull
    private Integer resultCode;
    @NonNull
    private String resultMessage;

    public static OperationResponse ok() {
        return new OperationResponse(RESULT_CODE__OK, RESULT_MESSAGE__OK);
    }

    public static OperationResponse error(String errorMessage) {
        return new OperationResponse(RESULT_CODE__ERROR, errorMessage);
    }

}
