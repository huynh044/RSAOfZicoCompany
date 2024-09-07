package com.api_simulation_zicocompany.responsive;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponsive<T> {
	@Builder.Default
    int code = 1000; // Mã trạng thái của phản hồi, mặc định là 1000
    String message; // Thông điệp liên quan đến phản hồi
    T result; // Kết quả dữ liệu, kiểu generics
}
