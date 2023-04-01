package com.planner.service.impl;

import com.planner.entities.RoleEntity;
import com.planner.model.Roles;
import com.planner.repository.RoleRepository;
import com.planner.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public void saveAll(List<Roles> roles) {
    roles.stream().forEach(
        role -> {
          roleRepository.save(convertToEntity(role));
        }
    );
  }

  private RoleEntity convertToEntity(Roles role){
    RoleEntity eRoleEntity = new RoleEntity();
    eRoleEntity.setName(role.getName());
    return eRoleEntity;
  }
}
