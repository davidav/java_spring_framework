package com.example.rest.controller;

import com.example.rest.AbstractTestController;
import com.example.rest.StringTestUtils;
import com.example.rest.dto.*;
import com.example.rest.dto.mapper.v1.OrderMapper;
import com.example.rest.exception.AppHelperException;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.service.OrderService;

import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
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
    public void whenCreateOrder_thenReturnNewOrder() throws Exception {
        Order order = createOrder(1L, 100L, null);
        OrderResponse orderResponse = createOrderResponse(1L, 100L);
        UpsertOrderRequest request = new UpsertOrderRequest(1L,"Test product 1", new BigDecimal(100) );

        Mockito.when(orderService.save(order)).thenReturn(order);
        Mockito.when(orderMapper.orderToResponse(order)).thenReturn(orderResponse);
        Mockito.when(orderMapper.requestToOrder(request)).thenReturn(order);

        String actualResponse = mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/create_order_response.json");

        Mockito.verify(orderService, Mockito.times(1)).save(order);
        Mockito.verify(orderMapper, Mockito.times(1)).requestToOrder(request);
        Mockito.verify(orderMapper, Mockito.times(1)).orderToResponse(order);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenUpdateOrder_whenReturnUpdatedOrder() throws Exception {

        Order order = createOrder(1L, 100L, null);
        OrderResponse orderResponse = createOrderResponse(1L, 100L);
        UpsertOrderRequest request = new UpsertOrderRequest(1L,"Test product 1", new BigDecimal(100));

        Mockito.when(orderService.update(order)).thenReturn(order);
        Mockito.when(orderMapper.orderToResponse(order)).thenReturn(orderResponse);
        Mockito.when(orderMapper.requestToOrder(1L, request)).thenReturn(order);


        String actualResponse = mockMvc.perform(put("/api/v1/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/update_order_response.json");

        Mockito.verify(orderService, Mockito.times(1)).update(order);
        Mockito.verify(orderMapper, Mockito.times(1)).requestToOrder(1L,request);
        Mockito.verify(orderMapper, Mockito.times(1)).orderToResponse(order);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenDeleteOrderById_thenReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/order/1"));

        Mockito.verify(orderService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenFindByIdNotExistedOrder_thenReturnError() throws Exception{
        Mockito.when(orderService.findById(500L)).thenThrow(new AppHelperException("Заказ не найден"));

        var response = mockMvc.perform(get("/api/v1/order/500"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/order_by_id_not_found_response.json");

        Mockito.verify(orderService, Mockito.times(1)).findById(500L);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenCreateOrderWithEmptyProduct_thenReturnError() throws Exception {
        var response = mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertOrderRequest(
                                1L, "" , new BigDecimal(100)))))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/empty_order_product_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateOrderWithNegativeId_thenReturnError() throws Exception {
        var response = mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertOrderRequest(
                                -1L, "Product" , new BigDecimal(100)))))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/negative_id__order_product_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateOrderWithNegativeCost_thenReturnError() throws Exception {
        var response = mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertOrderRequest(
                                1L, "Product" , new BigDecimal(-100)))))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/negative_cost__order_product_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }
}
