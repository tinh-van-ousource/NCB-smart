package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFunctionAndProductFeeRequest {

    private CreateFunctionRequest function;

    private CreateProductFeeRequest productFee;
}
