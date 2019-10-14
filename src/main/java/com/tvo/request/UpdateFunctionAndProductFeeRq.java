package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFunctionAndProductFeeRq {

    private UpdateFunctionRequest function;

    private UpdateProductFeeRequest productFee;
}
