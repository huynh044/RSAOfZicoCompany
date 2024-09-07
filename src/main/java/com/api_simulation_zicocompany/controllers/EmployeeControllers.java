package com.api_simulation_zicocompany.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api_simulation_zicocompany.request.EmployeeRequest;
import com.api_simulation_zicocompany.responsive.ApiResponsive;
import com.api_simulation_zicocompany.responsive.EmployeeResponsive;
import com.api_simulation_zicocompany.services.EmployeeServices;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/employee")
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmployeeControllers {
	EmployeeServices services;
	
	@PostMapping
	ApiResponsive<EmployeeResponsive> create(@Valid @RequestBody EmployeeRequest request){
		return ApiResponsive.<EmployeeResponsive>builder()
				.result(services.createEmployeeResponsive(request))
				.build();
	}
}
