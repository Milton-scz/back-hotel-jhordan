package com.jhordan.Resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.jhordan.Entity.User;
import com.jhordan.Exception.DuplicateEntryException;
import com.jhordan.Exception.EntityNotFoundException;
import com.jhordan.Repository.UserRepository;
import com.jhordan.Security.TokenGenerator;

import graphql.GraphQLException;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Component @RestController
public class UserMutationResolver implements GraphQLMutationResolver {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MutationMapping 
    public User createUser(@Argument User user) {
    	if(!userRepository.existsByEmail(user.getEmail())) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(User.Role.CLIENTE);
        }
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException("Error, Email ya existe!");
        }
    	}else {
    		 throw new DuplicateEntryException("Error2, Email ya existe!");
    	}
    	
    }
    @MutationMapping 
    public User updateUser(@Argument User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("The update request must include an ID");
        }
        if (user.getName() == null && user.getPassword() == null && user.getRole() == null) {
            throw new IllegalArgumentException("The update request must include values for either" +
                    "the username, password or role");
        }

        LOGGER.info("Update request received: {}", user);

        User currentUser = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
       if(user.getPassword()!=null) {
    	   user.setPassword(passwordEncoder.encode(user.getPassword()));
       }
       
        currentUser.merge(user);

        return userRepository.save(currentUser);
    }
    @MutationMapping 
    public boolean deleteUser(@Argument Long id) {
        LOGGER.info("Received request to delete user with id: {}", id);
        userRepository.deleteById(id);

        return true;
    }
    
    @MutationMapping 
    public String login(@Argument String email,@Argument String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new GraphQLException("Invalid credentials");
        }

        return tokenGenerator.build(user.getName(), user.getRole());
    }
}
