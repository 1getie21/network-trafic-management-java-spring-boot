package com.insa.TeamOpsSystem.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RepositoryRestResource
public interface RequestRepository extends PagingAndSortingRepository<Request, Long>, JpaSpecificationExecutor<Request> {

    Page<Request> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
    Page<Request> findAll(Pageable Pageable);
    Page<Request> findAllByCreatedAtBetweenAndCreatedBy(LocalDateTime from, LocalDateTime to, String username, Pageable pageable);

    List<Request> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    List<Request> findAllByCreatedBy(String createdBy);

    List<Request> findAllByCreatedAtBetweenAndCreatedBy(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay1, String username);

    List<Request> findAll();

}
