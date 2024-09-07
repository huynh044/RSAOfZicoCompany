package com.api_simulation_zicocompany.controllers;

import org.springframework.web.bind.annotation.*;
import com.api_simulation_zicocompany.request.ReceiptRequest;
import com.api_simulation_zicocompany.request.VerifySignatureRequest;
import com.api_simulation_zicocompany.responsive.ApiResponsive;
import com.api_simulation_zicocompany.responsive.ReceiptResponsive;
import com.api_simulation_zicocompany.services.ReceiptServices;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/receipt")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReceiptControllers {
    ReceiptServices receiptServices;

    @PostMapping("/create")
    public ApiResponsive<ReceiptResponsive> createReceipt(@RequestBody ReceiptRequest request) {
        try {
            ReceiptResponsive receiptResponse = receiptServices.createReceipt(request);
            return ApiResponsive.<ReceiptResponsive>builder()
                    .result(receiptResponse)
                    .message("Receipt created successfully.")
                    .build();
        } catch (Exception e) {
            return ApiResponsive.<ReceiptResponsive>builder()
                    .code(9999)
                    .message("Error creating receipt.")
                    .build();
        }
    }

    @PostMapping("/verify")
    public ApiResponsive<String> verifySignature(@RequestBody VerifySignatureRequest request) {
        try {
            boolean isValid = receiptServices.verifySignature(request);
            if (isValid) {
                return ApiResponsive.<String>builder()
                        .message("Signature is valid")
                        .build();
            } else {
                return ApiResponsive.<String>builder()
                        .code(1001)
                        .message("Signature is invalid")
                        .build();
            }
        } catch (Exception e) {
            return ApiResponsive.<String>builder()
                    .code(9999)
                    .message(e.toString())
                    .build();
        }
    }
}
