package com.api_simulation_zicocompany.mapper;

import org.mapstruct.*;

import com.api_simulation_zicocompany.entity.Employee;
import com.api_simulation_zicocompany.request.EmployeeRequest;
import com.api_simulation_zicocompany.responsive.EmployeeResponsive;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	@Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "receipts", ignore = true)
	@Mapping(target = "publicKey", ignore = true)
	@Mapping(target = "privateKey", ignore = true)
	Employee toEmployee(EmployeeRequest request);
	
	EmployeeResponsive toEmployeeResponsive (Employee employee);
}
