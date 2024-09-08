package com.api_simulation_zicocompany.services;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api_simulation_zicocompany.entity.Employee;
import com.api_simulation_zicocompany.mapper.EmployeeMapper;
import com.api_simulation_zicocompany.repository.EmployeeRepository;
import com.api_simulation_zicocompany.request.EmployeeRequest;
import com.api_simulation_zicocompany.responsive.EmployeeResponsive;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

public interface EmployeeServices {
	EmployeeResponsive createEmployeeResponsive(EmployeeRequest request);
	List<EmployeeResponsive> getAll();
}

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class EmployeeServiceImpl implements EmployeeServices{
	EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponsive createEmployeeResponsive(EmployeeRequest request) {
        KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();

            // Encode keys to Base64 strings
            String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

            // Create Employee entity from request
            Employee employee = employeeMapper.toEmployee(request);

            // Set the Base64-encoded public and private keys
            employee.setPublicKey(publicKey);
            employee.setPrivateKey(privateKey); // Add privateKey field in Employee entity

            // Save the employee entity
            employee = employeeRepository.save(employee);

            // Convert saved entity to responsive object
            return employeeMapper.toEmployeeResponsive(employee);

        } catch (NoSuchAlgorithmException e) {
            // Handle the exception appropriately
            throw new RuntimeException("Error generating RSA key pair", e);
        }
    }

	@Override
	public List<EmployeeResponsive> getAll() {
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream()
				.map(employeeMapper::toEmployeeResponsive)
				.collect(Collectors.toList());
	}
}
