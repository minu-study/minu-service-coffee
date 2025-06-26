package minu.coffee.config.security;

import jakarta.annotation.PostConstruct;
import minu.coffee.common.util.EncUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptorConfig {

    @Value("${common.encrypt.key}")
    private String encryptKey;

    @Value("${common.encrypt.salt}")
    private String encryptSalt;

    @PostConstruct
    public void init() {
        EncUtil.setEncryptKey(encryptKey);
        EncUtil.setEncryptSalt(encryptSalt);
        EncUtil.resetEncryptor();
    }
}