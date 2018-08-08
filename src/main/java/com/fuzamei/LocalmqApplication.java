package com.fuzamei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@MapperScan(basePackages = {"com.fuzamei.mapper"})
public class LocalmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalmqApplication.class, args);
	}
}
