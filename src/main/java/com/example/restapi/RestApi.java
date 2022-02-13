package com.example.restapi;

import com.example.restapi.model.Address;
import com.example.restapi.model.Gender;
import com.example.restapi.model.Student;
import com.example.restapi.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class RestApi {

	public static void main(String[] args) {
		SpringApplication.run(RestApi.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate template){
		return args -> {
			String email = "example2@email.com";
			Address address = new Address(
					"Brazil",
					"POA",
					"999"
			);
			Student student = new Student(
					"Test First",
					"Test Last",
					email,
					Gender.FEMALE,
					address,
					List.of("Arts", "Physics"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);
			//usingMongoTemplateAndQuery(repository, template, email, student);

			repository.findStudentByEmail(email).ifPresentOrElse(student1 -> {
				// exists
				System.out.println("Already exist " + student);
			}, () -> {
				// empty
				System.out.println("Inserting student " + student);
				repository.insert(student);
			});
		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate template, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = template.find(query, Student.class);
		if(students.size() > 1){
			throw new IllegalStateException("found many students with email " + email);
		}

		if(students.isEmpty()){
			System.out.println("Inserting student " + student);
			repository.insert(student);
		}
		else{
			System.out.println("Already exist " + student);
		}
	}

}
