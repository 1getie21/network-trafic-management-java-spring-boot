package com.insa.TeamOpsSystem.sites;


import com.insa.TeamOpsSystem.until.Auditable;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "sites")
@Data
@Where(clause = "deleted=0")
@SQLDelete(sql = "UPDATE sites SET deleted = 1 WHERE id=? and version=?")
public class Sites extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private  String url;
}
