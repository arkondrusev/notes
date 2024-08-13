package com.example.notes.generator;

import com.example.notes.dto.OperationResponse;

public class OperationResponseGenerator {

    public static OperationResponse.OperationResponseBuilder generateOperationResponseOkBuilder() {
        return OperationResponse.builder().setResultCode(0).setResultMessage("OK");
    }

}
