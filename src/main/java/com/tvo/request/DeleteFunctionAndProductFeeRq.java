package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteFunctionAndProductFeeRq {

    private FunctionRequest function;

    private ProductFeeRequest productFee;
}
