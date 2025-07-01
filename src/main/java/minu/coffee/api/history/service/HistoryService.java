package minu.coffee.api.history.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import minu.coffee.common.filter.customException.AppException;
import minu.coffee.common.filter.customException.ErrorCode;
import minu.coffee.common.model.TokenInfo;
import minu.coffee.common.util.CommonUtil;
import minu.coffee.history.HistoryDto;
import minu.coffee.memberInfo.model.MemberInfo;
import minu.coffee.memberInfo.repository.MemberInfoRepo;
import minu.coffee.paymentHistory.model.PaymentHistory;
import minu.coffee.paymentHistory.repository.PaymentHistoryRepo;
import minu.coffee.pointHistory.model.PointHistory;
import minu.coffee.pointHistory.repository.PointHistoryRepo;
import minu.coffee.product.model.Product;
import minu.coffee.product.repository.ProductRepo;
import minu.coffee.productPrice.model.ProductPrice;
import minu.coffee.productPrice.repositroy.ProductPriceRepo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryService {

    private final PaymentHistoryRepo paymentHistoryRepo;
    private final PointHistoryRepo pointHistoryRepo;
    private final ProductRepo productRepo;
    private final ProductPriceRepo productPriceRepo;
    private final MemberInfoRepo memberInfoRepo;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveHistoryAsync(HistoryDto.SetPaymentHistory.Request param) {

        TokenInfo tokenInfo = CommonUtil.getTokenInfo();

        Product product = productRepo.findById(param.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        ProductPrice productPrice
                = productPriceRepo.findByProductAndEnableAndDeleted(product, true, false)
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        MemberInfo memberInfo = memberInfoRepo.findById(tokenInfo.getId())
                .orElseThrow(() -> new AppException(ErrorCode.DB001));


        PaymentHistory paymentHistory = PaymentHistory.builder()
                .product(product)
                .memberInfo(memberInfo)
                .quantity(param.getQuantity())
                .originalPrice(productPrice.getPrice())
                .discount(param.getDiscount())
                .quantity(param.getQuantity())
                .paymentPrice(param.getPaymentPrice())
                .usePoint(param.getUsePoint())
                .build();
        paymentHistoryRepo.save(paymentHistory);

        if (! ObjectUtils.isEmpty(param.getUsePoint())) {
            HistoryDto.SetPointHistory.Request pointHistoryRequest
                    = HistoryDto.SetPointHistory.Request.builder()
                    .paymentHistoryId(paymentHistory.getId())
                    .transactionType("PAYMENT")
                    .transactionPoint(param.getUsePoint())
                    .pointBalanceAfter(memberInfo.getPoint() - param.getUsePoint())
                    .build();
            savePointHistory(pointHistoryRequest);
        }

    }

    @Transactional
    public void saveHistory(HistoryDto.SetPaymentHistory.Request param) {

        TokenInfo tokenInfo = CommonUtil.getTokenInfo();

        Product product = productRepo.findById(param.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        ProductPrice productPrice
                = productPriceRepo.findByProductAndEnableAndDeleted(product, true, false)
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        MemberInfo memberInfo = memberInfoRepo.findById(tokenInfo.getId())
                .orElseThrow(() -> new AppException(ErrorCode.DB001));


        PaymentHistory paymentHistory = PaymentHistory.builder()
                .product(product)
                .memberInfo(memberInfo)
                .quantity(param.getQuantity())
                .originalPrice(productPrice.getPrice())
                .discount(param.getDiscount())
                .quantity(param.getQuantity())
                .paymentPrice(param.getPaymentPrice())
                .usePoint(param.getUsePoint())
                .build();
        paymentHistoryRepo.save(paymentHistory);

        if (! ObjectUtils.isEmpty(param.getUsePoint())) {
            HistoryDto.SetPointHistory.Request pointHistoryRequest
                    = HistoryDto.SetPointHistory.Request.builder()
                    .paymentHistoryId(paymentHistory.getId())
                    .transactionType("PAYMENT")
                    .transactionPoint(param.getUsePoint())
                    .pointBalanceAfter(memberInfo.getPoint() - param.getUsePoint())
                    .build();
            savePointHistory(pointHistoryRequest);
        }

    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void savePointHistoryAsync(HistoryDto.SetPointHistory.Request param) {
        TokenInfo tokenInfo = CommonUtil.getTokenInfo();

        MemberInfo memberInfo = memberInfoRepo.findById(tokenInfo.getId())
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        PaymentHistory paymentHistory = null;
        if (! ObjectUtils.isEmpty(param.getPaymentHistoryId())) {
            paymentHistory = paymentHistoryRepo.findById(param.getPaymentHistoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.DB001));
        }

        PointHistory pointHistory = PointHistory.builder()
                .memberInfo(memberInfo)
                .paymentHistory(paymentHistory)
                .transactionType(param.getTransactionType())
                .pointAmount(param.getTransactionPoint())
                .pointBalanceAfter(param.getPointBalanceAfter())
                .build();
        pointHistoryRepo.save(pointHistory);

    }

    @Transactional
    public void savePointHistory(HistoryDto.SetPointHistory.Request param) {
        TokenInfo tokenInfo = CommonUtil.getTokenInfo();

        MemberInfo memberInfo = memberInfoRepo.findById(tokenInfo.getId())
                .orElseThrow(() -> new AppException(ErrorCode.DB001));

        PaymentHistory paymentHistory = null;
        if (! ObjectUtils.isEmpty(param.getPaymentHistoryId())) {
            paymentHistory = paymentHistoryRepo.findById(param.getPaymentHistoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.DB001));
        }

        PointHistory pointHistory = PointHistory.builder()
                .memberInfo(memberInfo)
                .paymentHistory(paymentHistory)
                .transactionType(param.getTransactionType())
                .pointAmount(param.getTransactionPoint())
                .pointBalanceAfter(param.getPointBalanceAfter())
                .build();
        pointHistoryRepo.save(pointHistory);

    }

}
