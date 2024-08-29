package com.example.cronparser;

import com.example.cronparser.controller.CronParserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CronParserApplication {
//	public static void main(String[] args) {
//		SpringApplication.run(CronParserApplication.class, args);
//	}
	public static void main(String[] args) {
		// If no arguments are provided, start the web server normally.
		if (args.length == 0) {
			SpringApplication.run(CronParserApplication.class, args);
		} else {
			// If arguments are provided, run the specific function and then close the context.
			try (ConfigurableApplicationContext context = SpringApplication.run(CronParserApplication.class, new String[]{})) {
				String cronExpression = args[0];  // Assuming the first argument is the cron expression.
				CronParserController controller = context.getBean(CronParserController.class);
				String result = controller.parseCronExpression(cronExpression);
				System.out.println(result);
			} catch (Exception e) {
				System.out.println("Error processing request: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
