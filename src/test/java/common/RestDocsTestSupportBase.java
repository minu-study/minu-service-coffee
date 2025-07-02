package common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@Disabled
@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public class RestDocsTestSupportBase extends BaseControllerTest {

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @BeforeEach
    protected void setUp(WebApplicationContext context,
                         RestDocumentationContextProvider provider) {
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(context)
                        .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                        .alwaysDo(MockMvcResultHandlers.print())
                        .alwaysDo(restDocs)
                        .addFilters(new CharacterEncodingFilter("UTF-8", true))
                        .build();
    }


    protected List<FieldDescriptor> getPageableResponseFields() {
        List<FieldDescriptor> pageableFields = new ArrayList<>();
        pageableFields.add(fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("페이지정보"));
        pageableFields.add(fieldWithPath("data.pageable.requestPageNumber").type(JsonFieldType.NUMBER).description("요청된 페이지 번호"));
        pageableFields.add(fieldWithPath("data.pageable.requestSize").type(JsonFieldType.NUMBER).description("요청된 페이지 당 Data 수"));
        pageableFields.add(fieldWithPath("data.pageable.nowPageNumber").type(JsonFieldType.NUMBER).description("현재 페이지 번호"));
        pageableFields.add(fieldWithPath("data.pageable.totalPageSize").type(JsonFieldType.NUMBER).description("전체 페이지 수"));
        pageableFields.add(fieldWithPath("data.pageable.totalDataSize").type(JsonFieldType.NUMBER).description("전체 Data 수"));
        return pageableFields;
    }

    protected List<FieldDescriptor> getStatusResponseFields() {
        List<FieldDescriptor> statusFields = new ArrayList<>();
        statusFields.add(fieldWithPath("resultCode").type(JsonFieldType.STRING).description("결과 코드"));
        statusFields.add(fieldWithPath("msg").type(JsonFieldType.STRING).description("결과 메시지"));
        statusFields.add(fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 객체").optional());
        return statusFields;
    }

    protected List<FieldDescriptor> getMetaDataResponseFields() {
        List<FieldDescriptor> metaDataFields = new ArrayList<>();
        metaDataFields.add(fieldWithPath("timeZone").type(JsonFieldType.STRING).description("서버 타임존"));
        metaDataFields.add(fieldWithPath("timeStamp").type(JsonFieldType.NUMBER).description("서버 응답시간"));
        metaDataFields.add(fieldWithPath("languageCode").type(JsonFieldType.STRING).description("응답의 언어셋"));
        return metaDataFields;
    }

    protected List<FieldDescriptor> getCommonResponseFields(String name) {
        List<FieldDescriptor> commonFields = new ArrayList<>();
        commonFields.add(fieldWithPath("data." + name + ".[].insertDate").type(JsonFieldType.STRING).description("생성일시").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".[].insertDateText").type(JsonFieldType.STRING).description("생성일자").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".[].insertUser").type(JsonFieldType.NUMBER).description("생성자 ID").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".[].insertUserName").type(JsonFieldType.STRING).description("생성자 명").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".[].insertIp").type(JsonFieldType.STRING).description("생성 IP").optional().ignored());

        commonFields.add(fieldWithPath("data." + name + ".[].updateDate").type(JsonFieldType.STRING).description("수정일시").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".[].updateDateText").type(JsonFieldType.STRING).description("수정일자").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".[].updateUser").type(JsonFieldType.NUMBER).description("수정자 ID").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".[].updateUserName").type(JsonFieldType.STRING).description("수정자 명").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".[].updateIp").type(JsonFieldType.STRING).description("수정 IP").optional().ignored());
        return commonFields;
    }

    protected List<FieldDescriptor> getCommonResponseField(String name) {
        List<FieldDescriptor> commonFields = new ArrayList<>();
        commonFields.add(fieldWithPath("data." + name + ".insertDate").type(JsonFieldType.STRING).description("생성일시").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".insertDateText").type(JsonFieldType.STRING).description("생성일자").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".insertUser").type(JsonFieldType.NUMBER).description("생성자 ID").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".insertUserName").type(JsonFieldType.STRING).description("생성자 명").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".insertIp").type(JsonFieldType.STRING).description("생성 IP").optional().ignored());

        commonFields.add(fieldWithPath("data." + name + ".updateDate").type(JsonFieldType.STRING).description("수정일시").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".updateDateText").type(JsonFieldType.STRING).description("수정일자").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".updateUser").type(JsonFieldType.NUMBER).description("수정자 ID").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".updateUserName").type(JsonFieldType.STRING).description("수정자 명").optional().ignored());
        commonFields.add(fieldWithPath("data." + name + ".updateIp").type(JsonFieldType.STRING).description("수정 IP").optional().ignored());
        return commonFields;
    }

    protected List<FieldDescriptor> getCommonResponseField() {
        List<FieldDescriptor> commonFields = new ArrayList<>();
        commonFields.add(fieldWithPath("data.insertDate").type(JsonFieldType.STRING).description("생성일시").optional().ignored());
        commonFields.add(fieldWithPath("data.insertDateText").type(JsonFieldType.STRING).description("생성일자").optional().ignored());
        commonFields.add(fieldWithPath("data.insertUser").type(JsonFieldType.NUMBER).description("생성자 ID").optional().ignored());
        commonFields.add(fieldWithPath("data.insertUserName").type(JsonFieldType.STRING).description("생성자 명").optional().ignored());
        commonFields.add(fieldWithPath("data.insertIp").type(JsonFieldType.STRING).description("생성 IP").optional().ignored());

        commonFields.add(fieldWithPath("data.updateDate").type(JsonFieldType.STRING).description("수정일시").optional().ignored());
        commonFields.add(fieldWithPath("data.updateDateText").type(JsonFieldType.STRING).description("수정일자").optional().ignored());
        commonFields.add(fieldWithPath("data.updateUser").type(JsonFieldType.NUMBER).description("수정자 ID").optional().ignored());
        commonFields.add(fieldWithPath("data.updateUserName").type(JsonFieldType.STRING).description("수정자 명").optional().ignored());
        commonFields.add(fieldWithPath("data.updateIp").type(JsonFieldType.STRING).description("수정 IP").optional().ignored());
        return commonFields;
    }

}

