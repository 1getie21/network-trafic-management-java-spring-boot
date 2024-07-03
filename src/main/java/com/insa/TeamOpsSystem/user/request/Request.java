package com.insa.TeamOpsSystem.user.request;
import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.jwt.until.Auditable;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "request")
@Data
@Where(clause = "deleted=0")
@SQLDelete(sql = "UPDATE request SET deleted = 1 WHERE id=? and version=?")
public class Request extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
//    private  String fname;
//    @Column(nullable = false)
    private  String phone;
    @Column(nullable = false)
    private  String email;
    @Column(nullable = false)
    private String requester;
    @Column(nullable = false)
    private String organization;
    private String categories;
    private String contact;
    private String description;
    private String status;
    private String priority;
    private String descriptionFile;
}
