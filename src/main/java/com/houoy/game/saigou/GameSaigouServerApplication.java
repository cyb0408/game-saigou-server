package com.houoy.game.saigou;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@EnableTransactionManagement
public class GameSaigouServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(GameSaigouServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GameSaigouServerApplication.class, args);
		logger.info("赛狗server启动成功");
	}
}
