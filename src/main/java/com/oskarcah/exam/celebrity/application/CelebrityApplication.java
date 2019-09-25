package com.oskarcah.exam.celebrity.application;

import com.oskarcah.exam.celebrity.components.ProblemSolver;
import com.oskarcah.exam.celebrity.model.KnownPerson;
import com.oskarcah.exam.celebrity.model.Person;
import com.oskarcah.exam.celebrity.model.Problem;
import com.oskarcah.exam.celebrity.repositories.KnownPersonRepository;
import com.oskarcah.exam.celebrity.repositories.PersonRepository;
import com.oskarcah.exam.celebrity.repositories.ProblemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Base Springboot Application
 */
@SpringBootApplication()
@ComponentScan({"com.oskarcah.exam.celebrity",})
@EnableJpaRepositories(basePackages = {"com.oskarcah.exam.celebrity.repositories",})
@EntityScan({"com.oskarcah.exam.celebrity.model",})
public class CelebrityApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CelebrityApplication.class);

    /**
     * Entry point for executable program
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        SpringApplication.run(CelebrityApplication.class, args);
    }


    /**
     * Servlet configuration method for war file
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CelebrityApplication.class);
    }


}
