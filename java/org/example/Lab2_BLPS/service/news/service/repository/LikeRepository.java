package org.example.Lab2_BLPS.service.news.service.repository;

import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.model.LikeEntity;
import org.example.Lab2_BLPS.service.news.model.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<LikeEntity, Long> {
    boolean existsLikeByNewsAndUsername(NewsEntity news, String username);

    void removeByNewsAndUsername(NewsEntity news, String username);

    Page<LikeEntity> findAllByNewsIdAndStateOfModel(Long newsId, StateOfModel stateOfModel, Pageable pageable);

    @Query("UPDATE FROM LikeEntity l set l.stateOfModel = :stateOfmodel WHERE l.username = :username")
    void updatesLikesModelByUsername(String username, StateOfModel stateOfmodel);
}
