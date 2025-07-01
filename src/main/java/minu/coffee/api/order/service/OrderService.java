package minu.coffee.api.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minu.coffee.api.history.service.HistoryService;
import minu.coffee.common.filter.customException.AppException;
import minu.coffee.common.filter.customException.ErrorCode;
import minu.coffee.common.model.TokenInfo;
import minu.coffee.common.util.CommonUtil;
import minu.coffee.history.HistoryDto;
import minu.coffee.memberInfo.model.MemberInfo;
import minu.coffee.memberInfo.repository.MemberInfoRepo;
import minu.coffee.order.dto.OrderDto;
import minu.coffee.product.model.Product;
import minu.coffee.product.repository.ProductRepo;
import minu.coffee.productPrice.model.ProductPrice;
import minu.coffee.productPrice.repositroy.ProductPriceRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepo productRepo;
    private final ProductPriceRepo productPriceRepo;
    private final MemberInfoRepo memberInfoRepo;
    private final HistoryService historyService;


    @Transactional
    public void productOrder(OrderDto.ProductOrder.Request param) {

        TokenInfo tokenInfo = CommonUtil.getTokenInfo();

        Product product = productRepo.findById(param.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        ProductPrice productPrice
                = productPriceRepo.findByProductAndEnableAndDeleted(product, true, false)
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        MemberInfo memberInfo = memberInfoRepo.findById(tokenInfo.getId())
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        Integer price = productPrice.getPrice() * param.getQuantity();

        if (param.getPaymentType().equals("POINT")) {

            if (memberInfo.getPoint() < price) {
                throw new AppException(ErrorCode.PAYMENT001);
            }

            memberInfo.setPoint(memberInfo.getPoint() - price);

        } else if (param.getPaymentType().equals("CASH")) {

            if (param.getCash() < price) {
                throw new AppException(ErrorCode.PAYMENT001);
            }

        } else {
            throw new AppException(ErrorCode.PAYMENT002);
        }

        HistoryDto.SetPaymentHistory.Request historyParam = HistoryDto.SetPaymentHistory.Request.builder()
                .productId(product.getId())
                .quantity(param.getQuantity())
                .paymentType(param.getPaymentType())
                .discount(param.getDiscount())
                .paymentPrice(price)
                .usePoint(param.getPoint())
                .build();

        historyService.saveHistoryAsync(historyParam);

    }

}
