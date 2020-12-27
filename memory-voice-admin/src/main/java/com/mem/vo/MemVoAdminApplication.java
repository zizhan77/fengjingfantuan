package com.mem.vo;

import com.mem.vo.controller.ticket.TicketStoreController;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ImportResource(locations = {"classpath:/spring-config.xml"})
@Slf4j
public class MemVoAdminApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MemVoAdminApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MemVoAdminApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {

		for (int i = 0; i < 6; i++) {
			//log.info("MemVoAdminApplication start successfully ");
		}

	}


	/**
	 * 继承CommandLineRunner接口后项目启动时会按照执行顺序执行run方法
	 * 通过设置Order的value来指定执行的顺序
	 */
	@Component
	public class MyCommandLineRunner implements CommandLineRunner{
		@Override
		public void run(String... var1) throws Exception{
			TicketStoreController.main(var1);
		}
	}


}

