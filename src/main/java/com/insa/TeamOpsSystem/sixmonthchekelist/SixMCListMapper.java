package com.insa.TeamOpsSystem.sixmonthchekelist;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SixMCListMapper {
    SixMCList toSixMCList(SixMCListDtos sixmclistDtos);

    SixMCListDtos toSixMCListDtos(SixMCList sixmclist);
}
