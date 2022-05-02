package com.skcc.test.payment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skcc.test.payment.PaymentApplication;
import com.skcc.test.payment.dto.PaymentDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id; // 관리 번호
	String userId; // 정산 받는 사용자 ID
	Integer amount; // 정산 금액

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime serviceDate; // 서비스 일자

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime PaymentDate;// 정산일자

	public PaymentDto toDto() {
		return PaymentDto.builder()
				.id(id)
				.serviceDate(serviceDate)
				.PaymentDate(PaymentDate)
				.userId(userId)
				.amount(amount)
				.build();
	}

	public PaymentDto save() {
		PaymentRepository repository = PaymentApplication.getApplicationContext().getBean(PaymentRepository.class);
		Payment saved = repository.save(this);
		return saved.toDto();
	}
}
