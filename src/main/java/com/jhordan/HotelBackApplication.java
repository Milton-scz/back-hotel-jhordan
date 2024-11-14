package com.jhordan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.jhordan.Entity.User;
import com.jhordan.Entity.User.Role;
import com.jhordan.Exception.GraphQLErrorAdapter;
import com.jhordan.Repository.UserRepository;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;




@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.jhordan") @ComponentScan(basePackageClasses = HotelBackApplication.class) 
public class HotelBackApplication {
	 @Autowired
	    PasswordEncoder encoder;
	    
	    @Autowired
	    UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(HotelBackApplication.class, args);
	}
	@Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLErrorHandler() {
            @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {
                // Separate client and server errors
                List<GraphQLError> clientErrors = errors.stream()
                        .filter(this::isClientError)
                        .collect(Collectors.toList());

                List<GraphQLError> serverErrors = errors.stream()
                        .filter(e -> !isClientError(e))
                        .map(GraphQLErrorAdapter::new)
                        .collect(Collectors.toList());

                List<GraphQLError> processedErrors = new ArrayList<>();
                processedErrors.addAll(clientErrors);
                processedErrors.addAll(serverErrors);
                return processedErrors;
            }

            protected boolean isClientError(GraphQLError error) {
                return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
            }
        };
    }

   
    
     @Bean
    public CommandLineRunner initData() {
        return args -> {
       
            if (!userRepository.existsByEmail("jhordan@gmail.com")) {
            	User user = new User();
            	user.setName("Jhordan");
            	user.setCedula("87766432");
            	user.setTelefono("76644887");
            	user.setEmail("jhordan@gmail.com");
            	user.setDireccion("Barrio los olivos zona norte");
            	 user.setPassword(encoder.encode("admin"));
            	 user.setRole(Role.ADMINISTRADOR);
                userRepository.save(user);
        }
    }; 
    }
  
     @Bean
     public WebMvcConfigurer corsConfigurer() {
         return new WebMvcConfigurer() {
             @Override
             public void addCorsMappings(CorsRegistry registry) {
                 registry.addMapping("/**")
                     // Especificar los orígenes explícitamente
                 	 .allowedOriginPatterns("http://*", "https://*")
                     .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                     .allowedHeaders("Authorization", "Content-Type")
                     .allowCredentials(true); // Permitir credenciales
             }
         };
     }

    
    
}


