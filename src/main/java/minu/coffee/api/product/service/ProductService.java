package minu.coffee.api.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minu.coffee.common.model.TokenInfo;
import minu.coffee.common.util.CommonUtil;
import minu.coffee.product.ProductDto;
import minu.coffee.product.repository.ProductQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductQueryRepository productQueryRepository;

    @Transactional(readOnly = true)
    public ProductDto.GetProducts.Response getProducts(ProductDto.GetProducts.Request param) {

        TokenInfo tokenInfo = CommonUtil.getTokenInfo();

        List<ProductDto.ProductSummaryView> productSummaryViewList
                = productQueryRepository.getProductSummaryView(tokenInfo.getShopInfoId(), LocalDateTime.now(), param.getSubcategoryId());

        return ProductDto.GetProducts.Response.builder()
                .list(productSummaryViewList)
                .build();
    }


}
