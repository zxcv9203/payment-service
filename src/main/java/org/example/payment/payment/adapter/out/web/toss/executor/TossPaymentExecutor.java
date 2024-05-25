package org.example.payment.payment.adapter.out.web.toss.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.payment.payment.adapter.in.web.request.TossPaymentConfirmRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TossPaymentExecutor {
    private static final String URI = "/v1/payments/confirm";
    private final WebClient tossPaymentWebClient;
    private final ObjectMapper objectMapper;

    public Mono<String> execute(TossPaymentConfirmRequest request) {
        try {
            String requestToJson = objectMapper.writeValueAsString(request);

            return tossPaymentWebClient.post()
                    .uri(URI)
                    .bodyValue(requestToJson)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
