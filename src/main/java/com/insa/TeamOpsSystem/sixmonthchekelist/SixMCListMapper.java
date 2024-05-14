package com.insa.TeamOpsSystem.sixmonthchekelist;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SixMCListMapper {
      SixMCList toTraffics(SixMCListDtos sixmclistDtos);
      SixMCListDtos toTrafficsDto(SixMCList sixmclist);
}
