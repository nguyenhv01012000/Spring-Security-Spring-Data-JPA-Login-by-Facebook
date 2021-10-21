package nguyen.security.repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nguyen.security.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	@Query("SELECT u FROM User u join u.roles r where r.id =:id")
	List<User> getUserList(@Param("id") Long id);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
