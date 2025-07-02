package common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minu.coffee.CoffeeApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = CoffeeApplication.class)
public class BaseControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    protected final MediaType contentTypeText = new MediaType(MediaType.TEXT_HTML.getType(),
            MediaType.TEXT_HTML.getSubtype(), StandardCharsets.UTF_8);

    protected final MediaType contentTypeFileForm = new MediaType(MediaType.MULTIPART_FORM_DATA.getType(),
            MediaType.MULTIPART_FORM_DATA.getSubtype(), StandardCharsets.UTF_8);

    protected String createJson(Object dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

}
