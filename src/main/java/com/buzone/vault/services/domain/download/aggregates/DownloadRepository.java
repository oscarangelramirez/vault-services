package com.buzone.vault.services.domain.download.aggregates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRepository extends JpaRepository<Download, Long> {
}
