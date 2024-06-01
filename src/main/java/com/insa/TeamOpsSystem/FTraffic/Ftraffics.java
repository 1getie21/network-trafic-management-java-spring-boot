package com.insa.TeamOpsSystem.FTraffic;


import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.until.Auditable;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Ftraffics extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String trafficTimeName;
    private  String timeValues;
    @ManyToOne
    private Sites sites;
    private String remark;
}
