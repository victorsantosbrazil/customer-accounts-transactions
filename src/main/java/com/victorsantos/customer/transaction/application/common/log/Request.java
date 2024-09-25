package com.victorsantos.customer.transaction.application.common.log;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Request {

    private String id;

    private String uri;

    private String method;

    private String user;

    private String from;

    private Map<String, String> headers;

    private String body;
}
