package com.lk.ecommerce.service.admin.adminOrder;

import com.lk.ecommerce.dto.core.AnalyticsResponse;
import com.lk.ecommerce.dto.core.OrderDTO;

import java.util.List;
import java.util.UUID;

public interface AdminOrderService {

    public List<OrderDTO> getAllPlacedOrder();
    public  OrderDTO changeOrderStatus(UUID orderId, String status);

    public AnalyticsResponse calculateAnalytics();
}
