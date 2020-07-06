package com.rubik;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 启动程序
 * 
 * @author jeefast
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RubikApplication {
    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext application = SpringApplication.run(RubikApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        System.out.println("\n----------------------------------------------------------\n\t" +
            "Application 工程启动成功 is running! Access URLs:\n\t" +
            "Local: \t\thttp://localhost:" + port + path + "\n\t" +
            "External: \thttp://" + ip + ":" + port + path + "\n" +
            "----------------------------------------------------------\n");
    }
}