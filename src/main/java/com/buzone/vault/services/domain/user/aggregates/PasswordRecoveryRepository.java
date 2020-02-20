package com.buzone.vault.services.domain.user.aggregates;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long> {
	Optional<PasswordRecovery> findByKey(String key);
}
