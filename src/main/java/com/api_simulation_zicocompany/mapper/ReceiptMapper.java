package com.api_simulation_zicocompany.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api_simulation_zicocompany.entity.*;
import com.api_simulation_zicocompany.responsive.ReceiptResponsive;

@Mapper(componentModel = "spring")
public interface ReceiptMapper {
	@Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "digitalSignature.id", target = "digitalSignatureId")
	ReceiptResponsive toReceiptResponsive (Receipt receipt);
}
