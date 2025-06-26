package minu.coffee.api.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minu.coffee.api.product.service.ProductService;
import minu.coffee.common.model.ApiResponse;
import minu.coffee.common.util.CommonUtil;
import minu.coffee.product.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getList(ProductDto.GetProducts.Request param) {
        ProductDto.GetProducts.Response response = productService.getProducts(param);
        return CommonUtil.convertResponse(response);
    }

}
