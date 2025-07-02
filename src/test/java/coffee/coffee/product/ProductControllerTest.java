package coffee.coffee.product;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import common.RestDocsTestSupportBase;
import minu.coffee.common.util.CommonUtil;
import minu.coffee.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;

@WithUserDetails(value = "minuda", userDetailsServiceBeanName = "customUserDetailsService")
public class ProductControllerTest extends RestDocsTestSupportBase {

    @Test
    void getProducts() throws Exception {

        ProductDto.GetProducts.Request param = new ProductDto.GetProducts.Request();
        param.setSubcategoryId(11L);

        ResultActions result = this.mockMvc.perform(
                        get("/api/product/list")
                                .contentType(contentType)
                                .accept(contentType)
                                .header("Authorization", "Bearer {accessToken}")
                                .params(CommonUtil.convertDtoToMap(objectMapper, param)));
        result.andExpect(status().isOk())
                .andDo(print())
                // 기존 restDocs 문서화
                .andDo(restDocs.document(
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 토큰")
                                ),
                                queryParameters(
                                        parameterWithName("subcategoryId").description("서브 카테고리 고유 아이디")
                                ),
                                responseFields(
                                                getStatusResponseFields())
                                        .and(
                                                fieldWithPath("data.list").type(JsonFieldType.ARRAY).description("상품 목록"),
                                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("상품 ID"),
                                                fieldWithPath("data.list[].categoryCode").type(JsonFieldType.STRING).description("카테고리 코드"),
                                                fieldWithPath("data.list[].categoryName").type(JsonFieldType.STRING).description("카테고리명"),
                                                fieldWithPath("data.list[].subCategoryCode").type(JsonFieldType.STRING).description("서브 카테고리 코드"),
                                                fieldWithPath("data.list[].subCategoryName").type(JsonFieldType.STRING).description("서브 카테고리명"),
                                                fieldWithPath("data.list[].productCode").type(JsonFieldType.STRING).description("상품코드"),
                                                fieldWithPath("data.list[].productName").type(JsonFieldType.STRING).description("상품명"),
                                                fieldWithPath("data.list[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                                fieldWithPath("data.list[].paymentType").type(JsonFieldType.STRING).description("결제 타입"),
                                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("결과 코드"),
                                                fieldWithPath("msg").type(JsonFieldType.STRING).description("결과 메시지"),
                                                fieldWithPath("timeZone").type(JsonFieldType.STRING).description("서버 타임존"),
                                                fieldWithPath("timeStamp").type(JsonFieldType.NUMBER).description("서버 응답시간"),
                                                fieldWithPath("languageCode").type(JsonFieldType.STRING).description("응답의 언어셋")
                                        )
                                        .and(getMetaDataResponseFields())
                        )
                )
                // openAPI 문서화
                .andDo(document("get-products",
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Product")
                                        .description("상품 목록 조회")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("인증 토큰")
                                        )
                                        .queryParameters(
                                                parameterWithName("subcategoryId").description("서브 카테고리 고유 아이디")
                                        )
                                        .responseFields(
                                                fieldWithPath("data.list").type(JsonFieldType.ARRAY).description("상품 목록"),
                                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("상품 ID"),
                                                fieldWithPath("data.list[].categoryCode").type(JsonFieldType.STRING).description("카테고리 코드"),
                                                fieldWithPath("data.list[].categoryName").type(JsonFieldType.STRING).description("카테고리명"),
                                                fieldWithPath("data.list[].subCategoryCode").type(JsonFieldType.STRING).description("서브 카테고리 코드"),
                                                fieldWithPath("data.list[].subCategoryName").type(JsonFieldType.STRING).description("서브 카테고리명"),
                                                fieldWithPath("data.list[].productCode").type(JsonFieldType.STRING).description("상품코드"),
                                                fieldWithPath("data.list[].productName").type(JsonFieldType.STRING).description("상품명"),
                                                fieldWithPath("data.list[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                                fieldWithPath("data.list[].paymentType").type(JsonFieldType.STRING).description("결제 타입"),
                                                fieldWithPath("resultCode").type(JsonFieldType.STRING).description("결과 코드"),
                                                fieldWithPath("msg").type(JsonFieldType.STRING).description("결과 메시지"),
                                                fieldWithPath("timeZone").type(JsonFieldType.STRING).description("서버 타임존"),
                                                fieldWithPath("timeStamp").type(JsonFieldType.NUMBER).description("서버 응답시간"),
                                                fieldWithPath("languageCode").type(JsonFieldType.STRING).description("응답의 언어셋")
                                        )
                                        .build()
                        )
                ));
    }

}
