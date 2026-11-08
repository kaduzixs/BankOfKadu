package banck.client.kadu.Repository;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository
        extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""SELECT a FROM Accounts a WHERE a.user.id = :userId """)
    Optional<Accounts> findByUserIdForUpdate(@Param("userId") Long userId);
}