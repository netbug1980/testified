package com.netbug_nb.uc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan
@EnableMongoRepositories // 不能提取到DemoApplication中
public class UCAutoConfiguration {
}
