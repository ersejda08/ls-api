package learnsmartly.ls_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import learnsmartly.ls_api.entity.LsUser;

public interface LsUserRepository extends JpaRepository<LsUser, Long> {

    Optional<LsUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
