package com.planner.repository;

import com.planner.entities.RoleEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity>{

  Optional<RoleEntity> findByName(String name);
}
