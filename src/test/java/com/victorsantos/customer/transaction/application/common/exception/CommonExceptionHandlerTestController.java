package com.victorsantos.customer.transaction.application.common.exception;

import com.victorsantos.customer.transaction.application.common.dto.ErrorResponse;
import java.util.Map;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommonExceptionHandlerTestController.TEST_EXCEPTIONS_PATH)
class CommonExceptionHandlerTestController {

    public static final String TEST_EXCEPTIONS_PATH = "/test/exceptions";

    @PostMapping("/api-exception/{status}")
    public void throwApiException(
            @PathVariable("status") int status, @RequestBody ErrorResponse expectedErrorResponse) {
        throw new ApiException(
                expectedErrorResponse.getType(),
                expectedErrorResponse.getTitle(),
                expectedErrorResponse.getDetail(),
                status);
    }

    @PostMapping("/method-argument-not-valid")
    public void throwMethodArgumentNotValidException(@RequestBody Map<String, String> expectedErrors)
            throws MethodArgumentNotValidException {
        var bindingResult = new BeanPropertyBindingResult(null, "exampleObject");
        for (var entry : expectedErrors.entrySet()) {
            bindingResult.addError(new FieldError("exampleObject", entry.getKey(), entry.getValue()));
        }
        throw new MethodArgumentNotValidException(null, bindingResult);
    }

    @GetMapping("/unexpected")
    public void throwUnexpectedException() {
        throw new RuntimeException("Unexpected exception");
    }
}
