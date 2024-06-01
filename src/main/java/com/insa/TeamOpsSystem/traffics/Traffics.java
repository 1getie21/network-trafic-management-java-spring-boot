package com.insa.TeamOpsSystem.traffics;


import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.jwt.until.Auditable;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "traffics")
@Data
@Where(clause = "deleted=0")
@SQLDelete(sql = "UPDATE traffics SET deleted = 1,deleted_at= LocalDateTime.now() WHERE id=? and version=?")
public class Traffics extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    private  Times eightTime;
    private  String eightTimeTraffic;
    @Column(nullable = false, updatable = false)
    private  Times fortiethTime;
    private  String fortiethTimeTraffic;
    @Column(nullable = false, updatable = false)
    private  Times eighteenTime;
    private  String eighteenTimeTraffic;
    private  String remark;
    @ManyToOne
    private Sites sites;
}
