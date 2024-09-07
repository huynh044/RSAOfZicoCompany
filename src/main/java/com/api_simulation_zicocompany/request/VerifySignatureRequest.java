package com.api_simulation_zicocompany.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifySignatureRequest {
	String employeeId;
	String receiptId;
}
