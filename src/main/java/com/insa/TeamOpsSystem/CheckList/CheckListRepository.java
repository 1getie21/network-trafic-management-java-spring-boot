package com.insa.TeamOpsSystem.CheckList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface CheckListRepository extends PagingAndSortingRepository<CheckList, Long>, JpaSpecificationExecutor<CheckList> {
}
