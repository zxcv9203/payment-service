package org.example.payment.payment.adapter.in.web.api;

import lombok.RequiredArgsConstructor;
import org.example.payment.common.WebAdapter;
import org.example.payment.payment.adapter.in.web.request.TossPaymentConfirmRequest;
import org.example.payment.payment.adapter.in.web.response.ApiResponse;
import org.example.payment.payment.adapter.out.web.toss.executor.TossPaymentExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@WebAdapter
@RequestMapping("/v1/toss")
@RestController
@RequiredArgsConstructor
public class TossPaymentController {

    private final TossPaymentExecutor tossPaymentExecutor;

    @RequestMapping("/confirm")
    public ResponseEntity<ApiResponse<Mono<String>>> confirm(
            @RequestBody TossPaymentConfirmRequest request
    ) {
        return ResponseEntity.ok()
                .body(ApiResponse.success(
                        HttpStatus.OK,
                        "success",
                        tossPaymentExecutor.execute(request)));
    }
}
