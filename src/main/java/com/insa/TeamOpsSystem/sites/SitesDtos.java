package com.insa.TeamOpsSystem.sites;

import com.insa.TeamOpsSystem.until.Auditable;
import lombok.Data;

@Data
public class SitesDtos extends Auditable {
    private Long id;
    private String name;
    private  String url;
}
