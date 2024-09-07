package com.api_simulation_zicocompany.responsive;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponsive {
	String id;
	String receiptContent;
	String employeeId;
	String digitalSignatureId;
}
