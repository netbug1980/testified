package com.netbug_nb.test;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@ConditionalOnProperty(prefix = "netbug_nb.test", name = "enable", havingValue = "true", matchIfMissing = false)
public class TestAutoConfiguration {

}
