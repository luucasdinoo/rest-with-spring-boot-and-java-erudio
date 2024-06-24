package br.com.erudio.restwithspringbootandjavaerudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);

		String result = bCryptPasswordEncoder.encode("admin123");
		System.out.println("My hash " + result);

        String result2 = bCryptPasswordEncoder.encode("admin321");
        System.out.println("My hash2 " + result2);
    }
}
