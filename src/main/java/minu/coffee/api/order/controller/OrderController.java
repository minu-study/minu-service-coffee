package minu.coffee.api.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minu.coffee.api.order.service.OrderService;
import minu.coffee.common.model.ApiResponse;
import minu.coffee.common.util.CommonUtil;
import minu.coffee.order.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> productOrder(@RequestBody OrderDto.ProductOrder.Request param) {

        log.info("OrderController.productOrder() param : {}", param);

        orderService.productOrder(param);

        return CommonUtil.convertResponse();

    }


}
