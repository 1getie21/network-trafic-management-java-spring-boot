package com.insa.TeamOpsSystem.failedTraffics;


import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.jwt.until.Auditable;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "failed_traffics")
@Data
@Where(clause = "deleted=0")
@SQLDelete(sql = "UPDATE failed_traffics SET deleted = 1 WHERE id=? and version=?")
public class FailedTraffics extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String failedLinkType;
    @Column(nullable = false)
    private  String failedReason;
    @Column(nullable = false)
    private  String reportedTo;
    @Column(nullable = false)
    private LocalDateTime fixedAt;
    @Column(nullable = false)
    private LocalDateTime disConnectedAt;
    private String failureLength;
    @ManyToOne
    private Sites sites;
}
