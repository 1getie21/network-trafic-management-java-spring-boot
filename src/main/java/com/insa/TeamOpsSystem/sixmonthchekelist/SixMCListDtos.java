package com.insa.TeamOpsSystem.sixmonthchekelist;

import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.jwt.until.Auditable;
import lombok.Data;

import java.io.Serializable;

@Data
public class SixMCListDtos extends Auditable  implements Serializable {
    private Long id;
    private String datacenter;
    private String fiber;
    private String rack;
    private String opd;
    private String Switch;
    private String t9140;
    private String server;
    private String routine;
    private Sites sites;
}
