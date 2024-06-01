package com.insa.TeamOpsSystem.CheckList;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface CheckListRepository extends JpaRepository<CheckList, Long>, JpaSpecificationExecutor<CheckList> {

    Page<CheckList> findAllBySitesDeletedIsFalse(Pageable pageable);

    Page<CheckList> findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(String username, Pageable pageable);
}
