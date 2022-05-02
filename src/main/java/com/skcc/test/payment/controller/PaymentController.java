package com.skcc.test.payment.controller;

import com.skcc.test.payment.domain.Payment;
import com.skcc.test.payment.domain.PaymentRepository;
import com.skcc.test.payment.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @RestController라고 설정한 클래스는 외부에서 요청한 HTTP Request를 내부의 서비스와 연결하는 역할을 합니다.
 *                   URL 주소를 보고 일치하는 메서드로 연결됩니다.
 */
@RestController
@RequiredArgsConstructor // pom.xml의 dependency로 lombok이 설정되어있어야 합니다. 아래 private final로 선언된 class를 초기화
                         // 하는데 사용됩니다.
public class PaymentController {

    private PaymentRepository repository;

    // 생성
    @PostMapping("/payment") // "@PostMapping" 은 "@RequestMapping(method = RequestMethod.POST)"을 축약한 것입니다.
    public PaymentDto saveNewPayment(@RequestBody Payment src) {
        /**
         * [ID를 null 값으로 설정해 준 이유]
         * JPA에서 엔티티의 ID 값이 null이거나 DB에 해당 ID값이 없으면 Insert를 실행하고, 이미 DB에 해당 ID 값이 있으면
         * Update를 실행합니다.
         * 그런데, Payment 엔티티의 ID가 null 일때, 자동 생성되도록 설정되어 있습니다. "@GeneratedValue" <- 자동 생성
         * 그래서 항상 insert를 수행하기 위해서 ID 값을 null로 지정해주는 방법을 선택하였습니다.
         */
        src.setId(null);
        return src.save();
    }

    // 전체 조회
    @GetMapping("/payment")
    public List<PaymentDto> getAllPayments() {
        List<Payment> allPayments = repository.findAll();
        return allPayments.stream().map(Payment::toDto).collect(Collectors.toList());
    }

    // item 하나 조회
    @GetMapping("/payment/{id}")
    public PaymentDto getPaymentById(@PathVariable Long id) {
        /**
         * @PathVariable은 URL에 있는 변수를 가져오는 방법입니다.
         *                url의 {id}라는 변수가 Method에서 사용될 수 있는 local 변수 id에 매핑됩니다.
         *                URL에서 사용된 이름과 변수명이 동일하기 때문에, 별도의 작업 없이 매핑됩니다.
         */

        Optional<Payment> byId = repository.findById(id);
        return byId.map(Payment::toDto).orElse(null);
    }
    /**
     * PathVariable와 비슷한 녀석으로 @RequestParam, @RequestBody가 있습니다.
     * 
     * @RequestParam
     *               URL의 물음표(?) 뒤에 붙는 변수입니다.
     *               구글에서 검색하면 오른쪽과 같은 예시를 만날 수 있습니다.
     *               https://www.google.com/search?q=hello
     *               여기서 q가
     *
     *
     * @RequestBody
     */
}
