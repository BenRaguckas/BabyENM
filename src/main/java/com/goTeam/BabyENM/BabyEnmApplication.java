package com.goTeam.BabyENM;

import com.goTeam.BabyENM.dao.NodeCrudRepository;
import com.goTeam.BabyENM.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BabyEnmApplication {


	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BabyEnmApplication.class, args);

		NodeCrudRepository crud = context.getBean(NodeCrudRepository.class);


		crud.save(new Node("TestName", "TestIP", "TestLocation", 1, 2));

		crud.findAll().forEach(System.out::println);
	}


}
