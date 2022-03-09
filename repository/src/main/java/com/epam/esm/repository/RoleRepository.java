package com.epam.esm.repository;

import com.epam.esm.entity.impl.Role;

public interface RoleRepository {
    Role get(String name);
}
