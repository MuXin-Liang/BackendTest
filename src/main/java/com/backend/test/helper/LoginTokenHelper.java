package com.backend.test.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT的token，区分大小写
 */
@ConfigurationProperties(prefix = "config.token")
@Component
public class LoginTokenHelper extends MD5PublicHelper {

}
