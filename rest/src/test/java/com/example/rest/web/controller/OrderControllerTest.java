package com.example.rest.web.controller;

import com.example.rest.AbstractTestController;
import com.example.rest.StringTestUtils;
import com.example.rest.dto.OrderListResponse;
import com.example.rest.dto.OrderResponse;
import com.example.rest.dto.mapper.v1.OrderMapper;
import com.example.rest.model.Order;
import com.example.rest.service.OrderService;

import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OrderControllerTest extends AbstractTestController {

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    @Test
    public void whenFindAll_thenReturnAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>(List.of(
                createOrder(1L, 100L, null),
                createOrder(2L, 200L, null)));
        List<OrderResponse> orderResponseList = new ArrayList<>(List.of(
                createOrderResponse(1L, 100L),
                createOrderResponse(2L, 200L)));
        OrderListResponse orderListResponse = new OrderListResponse(orderResponseList);

        Mockito.when(orderService.findAll()).thenReturn(orders);
        Mockito.when(orderMapper.orderLisToOrderListResponse(orders)).thenReturn(orderListResponse);

        String actualResponse = mockMvc.perform(get("/api/v1/order"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/find_all_orders_response.json");

        Mockito.verify(orderService, Mockito.times(1)).findAll();
        Mockito.verify(orderMapper, Mockito.times(1)).orderLisToOrderListResponse(orders);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenFindById_thenReturnOrder() throws Exception {
        Order order = createOrder(1L, 100L, null);
        OrderResponse orderResponse = createOrderResponse(1L, 100L);

        Mockito.when(orderService.findById(1L)).thenReturn(order);
        Mockito.when(orderMapper.orderToResponse(order)).thenReturn(orderResponse);
        String actualResponse = mockMvc.perform(get("/api/v1/order/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/find_by_id_orders_response.json");

        Mockito.verify(orderService, Mockito.times(1)).findById(1L);
        Mockito.verify(orderMapper, Mockito.times(1)).orderToResponse(order);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
