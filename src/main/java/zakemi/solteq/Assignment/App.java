package zakemi.solteq.Assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import zakemi.solteq.Assignment.database.MongoDBDatabase;
import zakemi.solteq.Assignment.database.SalaryDatabase;
import zakemi.solteq.Assignment.database.SalaryDatabaseImpl;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class App
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
    
    @Bean
    public MongoDBDatabase getDatabase(){
    	return new MongoDBDatabase();
    }
    
    @Bean
    public SalaryDatabase getSalaryDatabase(){
    	return new SalaryDatabaseImpl();
    }
}
