package com.victorsantos.customer.transaction.application.common.log;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Response {

    private String requestId;

    private Integer statusCode;

    private String body;

    private Map<String, String> headers;
}
