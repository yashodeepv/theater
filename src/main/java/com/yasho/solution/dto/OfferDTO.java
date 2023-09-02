package com.yasho.solution.dto;


import com.yasho.solution.entity.OfferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {
    private Long id;
    private String offerName;

    private double discountPercentage;

    private OfferType offerType;

    private LocalDate startDate;

    private LocalDate endDate;
}
