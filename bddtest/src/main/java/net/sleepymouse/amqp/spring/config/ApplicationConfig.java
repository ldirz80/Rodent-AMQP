package net.sleepymouse.amqp.spring.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@PropertySource("classpath:amqp.properties")
@ComponentScan({ "net.sleepymouse.amqp" })
public class ApplicationConfig
{

}
