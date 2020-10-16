package springframework.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:    MyConfiguration
 * Package:    springframework.configuration
 * Datetime:    2020/10/15   16:21
 * Author:   zsh
 * Description: bean配置类上加不加@Configuration的区别
 */
@Configuration
public class MyConfiguration {
    @Bean
    public Student student(){
        System.out.println("this is in student()");
        return new Student();
    }

    @Bean
    public Teacher mathTeacher(){
        System.out.println("this is in mathTeacher()");
        Student student = this.student();
        return new Teacher(student);
    }

    @Bean
    public Teacher englisthTeacher(){
        System.out.println("this is in englisthTeacher()");
        Student student = this.student();
        return new Teacher(student);
    }
}
