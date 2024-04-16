package com.insa.TeamOpsSystem.Task1;
import com.insa.TeamOpsSystem.until.Auditable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity(name = "task1") // Use @Entity to mark the class as a JPA entity
@Data   // Use Lombok's @Data annotation for getters/setters
public class Task1 extends Auditable {

    @Id  // Use @Id to identify the primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use @GeneratedValue for automatic ID generation
    private Long id; // Use Long for ID compatibility

    private String name; // Use String for text fields
    private String description;

}
