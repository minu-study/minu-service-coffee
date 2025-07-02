package minu.coffee;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@ComponentScan("minu")
public class CoffeeApplication {

    public static void main(String[] args) {
        log.info("CoffeeApplication Service Start args : {}", Arrays.toString(args));
        ApplicationContext ctx = SpringApplication.run(CoffeeApplication.class, args);

    }


    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("Service Start TimeZone : {} / Time : {} / Locale : {}", TimeZone.getDefault().getID(), new Date(), Locale.getDefault());
    }


}
