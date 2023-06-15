package org.example.Lab2_BLPS.service.news.service.repository;

import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.model.CommentEntity;
import org.example.Lab2_BLPS.service.news.model.LikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    Page<CommentEntity> findAllByNewsIdAndStateOfModel(Long newsId, StateOfModel stateOfModel, Pageable pageable);

    boolean existsByIdAndUsername(Long id, String username);

    @Query("UPDATE FROM CommentEntity c set c.stateOfModel = :stateOfmodel WHERE c.username = :username")
    void updatesCommentsModelByUsername(String username, StateOfModel stateOfmodel);
}
