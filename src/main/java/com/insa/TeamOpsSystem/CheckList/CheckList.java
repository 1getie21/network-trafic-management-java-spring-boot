package com.insa.TeamOpsSystem.CheckList;
import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.until.Auditable;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "check_list")
@Data
@Where(clause = "deleted=0")
@SQLDelete(sql = "UPDATE check_list SET deleted = 1 WHERE id=? and version=?")
public class CheckList extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private  String linkType;
    @Column(nullable = false)
    private  String download;
    @Column(nullable = false)
    private  String upload;
    @Column(nullable = false)
    private String createdBy;
    @Column(nullable = false)

    private String avgNBP;
    private String npbone;
    private String npbtwo;
    private String npbthree;
    private  float nbpTotal;
    @ManyToOne
    private Sites sites;
}
