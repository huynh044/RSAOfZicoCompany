package com.api_simulation_zicocompany.services;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.api_simulation_zicocompany.entity.DigitalSignature;
import com.api_simulation_zicocompany.entity.Employee;
import com.api_simulation_zicocompany.entity.Receipt;
import com.api_simulation_zicocompany.mapper.ReceiptMapper;
import com.api_simulation_zicocompany.repository.DigitalSignatureRepository;
import com.api_simulation_zicocompany.repository.EmployeeRepository;
import com.api_simulation_zicocompany.repository.ReceiptRepository;
import com.api_simulation_zicocompany.request.ReceiptRequest;
import com.api_simulation_zicocompany.request.VerifySignatureRequest;
import com.api_simulation_zicocompany.responsive.ReceiptResponsive;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

public interface ReceiptServices {
	ReceiptResponsive createReceipt(ReceiptRequest request);
	boolean verifySignature(VerifySignatureRequest request);
}
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class ReceiptServicesImpl implements ReceiptServices{
	EmployeeRepository employeeRepository;
    ReceiptRepository receiptRepository;
    ReceiptMapper receiptMapper;
    DigitalSignatureRepository digitalSignatureRepository;

    @Override
    @Transactional
    public ReceiptResponsive createReceipt(ReceiptRequest request) {
        // Find the employee who will sign the receipt
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Create the receipt entity from the request
        Receipt receipt = new Receipt();
        receipt.setReceiptContent(request.getReceiptContent());

        // Generate the digital signature
        DigitalSignature digitalSignature = generateDigitalSignature(receipt,receipt.getReceiptContent(), employee.getPrivateKey());
        digitalSignatureRepository.save(digitalSignature);
        // Associate the digital signature with the receipt
        receipt.setDigitalSignature(digitalSignature);

        // Set the employee
        receipt.setEmployee(employee);

        // Save the receipt
        receipt = receiptRepository.save(receipt);

        // Convert saved entity to responsive object
        return receiptMapper.toReceiptResponsive(receipt);
    }

    private DigitalSignature generateDigitalSignature(Receipt receipt,String content, String privateKeyBase64) {
        try {
            // Decode the private key
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // Create a signature object
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(content.getBytes());

            // Sign the content
            byte[] digitalSignatureBytes = signature.sign();

            // Encode the signature to Base64
            String digitalSignatureBase64 = Base64.getEncoder().encodeToString(digitalSignatureBytes);

            // Create and return the DigitalSignature entity
            DigitalSignature digitalSignature = new DigitalSignature();
            digitalSignature.setSignature(digitalSignatureBase64);
            digitalSignature.setReceipt(receipt);
            return digitalSignature;

        } catch (Exception e) {
            throw new RuntimeException("Error generating digital signature", e);
        }
    }

	@Override
	public boolean verifySignature(VerifySignatureRequest request) {
		Receipt receipt = receiptRepository.findById(request.getReceiptId())
                .orElseThrow(() -> new RuntimeException("Receipt not found"));

        // Retrieve the employee who should have signed the receipt
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Retrieve the digital signature associated with the receipt
        DigitalSignature digitalSignature = receipt.getDigitalSignature();
        if (digitalSignature == null) {
            throw new RuntimeException("Digital signature not found for the receipt");
        }

        // Verify the digital signature
        return verifyDigitalSignature(receipt.getReceiptContent(), digitalSignature.getSignature(), employee.getPublicKey());
	}
	
	private boolean verifyDigitalSignature(String content, String signatureBase64, String publicKeyBase64) {
        try {
            // Decode the public key
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            // Decode the signature
            byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);

            // Create a signature object
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(content.getBytes());

            // Verify the signature
            return signature.verify(signatureBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error verifying digital signature", e);
        }
    }
}
