package com.example.rest.dto.mapper.v2;

import com.example.rest.dto.OrderListResponse;
import com.example.rest.dto.OrderResponse;
import com.example.rest.dto.UpsertOrderRequest;
import com.example.rest.model.Order;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@DecoratedWith(OrderMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapperV2 {

    Order requestToOrder(UpsertOrderRequest request);

    @Mapping(source = "orderId", target = "id")
    Order requestToOrder(Long orderId, UpsertOrderRequest request);

    OrderResponse orderToResponse(Order order);

    List<OrderResponse> orderListToResponseList(List<Order> orders);

    default OrderListResponse orderListToOrderListResponse(List<Order> orders){
        OrderListResponse response = new OrderListResponse();
        response.setOrders(orderListToResponseList(orders));

        return response;
    }
}
