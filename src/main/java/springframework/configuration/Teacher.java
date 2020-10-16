package springframework.configuration;

import lombok.NoArgsConstructor;

/**
 * ClassName:    Teacher
 * Package:    springframework.configuration
 * Datetime:    2020/10/15   16:25
 * Author:   zsh
 * Description:
 */
public class Teacher {
    private Student student;

    public Teacher(Student student){
        this.student = student;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "student=" + student +
                '}';
    }
}
