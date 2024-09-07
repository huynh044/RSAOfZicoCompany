package com.api_simulation_zicocompany.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
	@Size(min = 8, message = "USERNAME_INVALID" )
	String name;
}
