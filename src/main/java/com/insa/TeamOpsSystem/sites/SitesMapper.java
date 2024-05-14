package com.insa.TeamOpsSystem.sites;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SitesMapper {
    Sites toTraffics(SitesDtos sitesDtos);
    SitesDtos toTrafficsDto(Sites sites);
}
