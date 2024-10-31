package com.tunemerge.tunemerge.Repository;

import com.tunemerge.tunemerge.Model.accessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<accessToken, Long> {
    accessToken findTopByOrderByIdDesc();
    @Modifying
    @Query("DELETE FROM accessToken")
    void deleteOldTokens();

}