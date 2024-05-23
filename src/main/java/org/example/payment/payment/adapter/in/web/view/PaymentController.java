package org.example.payment.payment.adapter.in.web.view;

import org.example.payment.common.WebAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@WebAdapter
@Controller
public class PaymentController {

    @GetMapping("/success")
    public Mono<String> success() {
        return Mono.just("success");
    }

    @GetMapping("/fail")
    public Mono<String> fail() {
        return Mono.just("fail");
    }
}
