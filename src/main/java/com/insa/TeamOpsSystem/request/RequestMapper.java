package com.insa.TeamOpsSystem.request;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestMapper {
      Request toTraffics(RequestDtos requestDtos);
      RequestDtos toTrafficsDto(Request request);
}
