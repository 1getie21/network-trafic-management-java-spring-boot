package com.insa.TeamOpsSystem.CheckList;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CheckListMapper {
    CheckList toTraffics(CheckListDtos CheckListDtos);
    CheckListDtos toTrafficsDto(CheckList CheckList);
}
