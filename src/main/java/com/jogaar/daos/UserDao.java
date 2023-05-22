package com.jogaar.daos;

import com.jogaar.entities.Campaign;
import com.jogaar.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(DISTINCT c.campaigner) FROM Campaign c where c.currentState = 'GREENLIT'")
    Long countSuccessfulCampaigners();

    @Query("SELECT COUNT(DISTINCT p.pledger) FROM Pledge p INNER JOIN p.campaign c where c.currentState = 'GREENLIT'")
    Long countSuccessfulPledgers();

    @Query("SELECT SUM(p.amount) FROM Pledge p INNER JOIN p.campaign c where c.currentState = 'GREENLIT'")
    Long totalRaised();

    @Query("SELECT u FROM User u WHERE u.accessLevel = 'MOD' OR u.accessLevel = 'ADMIN'")
    Page<User> findAllSuperUsers(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.accessLevel = 'BANNED'")
    Page<User> findAllBannedUsers(Pageable pageable);

    Page<User> findAllByAccessLevel(User.Access access, Pageable pageable);
}
