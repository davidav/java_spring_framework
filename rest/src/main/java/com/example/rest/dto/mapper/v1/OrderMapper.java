package com.example.rest.dto.mapper.v1;

import com.example.rest.dto.OrderListResponse;
import com.example.rest.dto.OrderResponse;
import com.example.rest.dto.UpsertOrderRequest;
import com.example.rest.model.Order;
import com.example.rest.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ClientService clientService;

    public Order requestToOrder(UpsertOrderRequest request) {
        Order order = new Order();
        order.setCost(request.getCost());
        order.setProduct(request.getProduct());
        order.setClient(clientService.findById(request.getClientId()));
        return order;
    }
    public  Order requestToOrder(Long id, UpsertOrderRequest request){
        Order order = requestToOrder(request);
        order.setId(id);
        return order;
    }

    public OrderResponse orderToResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setProduct(order.getProduct());
        orderResponse.setCost(order.getCost());
        return orderResponse;
    }

    public List<OrderResponse> orderListToResponseList(List<Order> orders){
        return orders.stream()
                .map(this::orderToResponse)
                .collect(Collectors.toList());
    }

    public OrderListResponse orderLisToOrderListResponse(List<Order> orders){
        OrderListResponse response = new OrderListResponse();
        response.setOrders(orderListToResponseList(orders));
        return response;

    }






}
