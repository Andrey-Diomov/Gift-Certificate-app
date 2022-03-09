package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
    @Pattern(regexp = "\\d*", message = "{idPositiveNumberMessage}")
    private String userId;

    @Pattern(regexp = "\\d+", message = "{idPositiveNumberMessage}")
    private String certificateId;
}
