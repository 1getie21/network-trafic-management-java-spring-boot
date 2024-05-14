package com.insa.TeamOpsSystem.traffics;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrafficMapper {
    Traffics toTraffics(TrafficDtos trafficDtos);
    TrafficDtos toTrafficsDto(Traffics traffics);
}
