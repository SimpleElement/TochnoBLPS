package org.example.Lab2_BLPS.service.report.service.repostory;

import org.example.Lab2_BLPS.service.report.model.NewsServiceBanEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NewsServiceBanRepository extends CrudRepository<NewsServiceBanEntity, Long> {
    Boolean existsByUsername(String username);

    NewsServiceBanEntity findByUsername(String username);

    @Query("DELETE FROM NewsServiceBanEntity nsb WHERE nsb.username = :username")
    void unbanByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN count(nsb) > 0 THEN true ELSE false END FROM NewsServiceBanEntity nsb WHERE nsb.unbanDate < :date")
    boolean existsUsersByUnban(@Param("date") Date date);

    @Query("SELECT nsb.username FROM NewsServiceBanEntity nsb WHERE nsb.unbanDate < :date")
    List<String> getUnbanUsername(@Param("date") Date date);
}
