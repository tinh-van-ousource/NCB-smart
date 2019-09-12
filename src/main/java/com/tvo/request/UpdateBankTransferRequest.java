package com.tvo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UpdateBankTransferRequest {
    private String bankCode;

    private String bankName;

    private String shtname;

    private String bin;

    private String citad_gt;

    private String citad_tt;

    private String status;
}
