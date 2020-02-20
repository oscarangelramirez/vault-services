package com.buzone.vault.services.domain.issue.cfdi.aggregates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueCfdiRepository extends JpaRepository<IssueCfdi, Long>, JpaSpecificationExecutor<IssueCfdi> {
}
