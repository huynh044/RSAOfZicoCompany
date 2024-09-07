package com.api_simulation_zicocompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api_simulation_zicocompany.entity.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, String> {

}
