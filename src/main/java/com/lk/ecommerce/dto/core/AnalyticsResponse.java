package com.lk.ecommerce.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {
    private Long placed;
    private Long shipped;
    private Long delivered;
    private Long currentMonthOrders;
    private Long previousMonthOrders;
    private Long currentMonthEarning;
    private Long previousMonthEarning;

}
