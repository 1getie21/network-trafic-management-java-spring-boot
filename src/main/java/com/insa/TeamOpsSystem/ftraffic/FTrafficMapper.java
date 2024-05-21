package com.insa.TeamOpsSystem.ftraffic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FTrafficMapper {
    Ftraffics toTraffics(FTrafficDtos fTrafficDtos);
    FTrafficDtos toTrafficsDto(Ftraffics fTraffics);
}
