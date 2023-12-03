package com.sandrajavaschool.OnlineStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OnlineStoreApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


		String password = "12345";

		//asi generamos contraseñas encriptadas a partir de "12345" +++
		//de esta forma no es siempre la misma encriptacion
		//genera dos contraseñas encriptadas una para adm y otra para user

		for (int i = 0; i < 2; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
		}


	}


}
