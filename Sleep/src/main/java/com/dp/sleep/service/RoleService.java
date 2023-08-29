package com.dp.sleep.service;


import com.dp.sleep.model.Role;
import com.dp.sleep.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private final RoleRepository repository;

  public RoleService(RoleRepository repository) {
    this.repository = repository;
  }

  public Role getByTitle(String title) {
    return repository.getRoleByTitle(title);
  }
}
