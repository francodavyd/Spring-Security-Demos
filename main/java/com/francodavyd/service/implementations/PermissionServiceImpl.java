<<<<<<< HEAD
package com.francodavyd.service.implementations;

import com.francodavyd.model.Permission;
import com.francodavyd.repository.IPermissionRepository;
import com.francodavyd.service.services.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private IPermissionRepository repository;

    @Override
    public List<Permission> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Permission save(Permission permission) {
        return repository.save(permission);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Permission update(Long id, Permission updatedPermission) {
        Optional<Permission> searchedPermission = this.findById(id);
        if (searchedPermission.isPresent()) {
            Permission per = searchedPermission.get();
            per.setPermissionName(updatedPermission.getPermissionName());
            return repository.save(per);
        }
        return null;
    }
}
=======
package com.francodavyd.service.implementations;

import com.francodavyd.model.Permission;
import com.francodavyd.model.Post;
import com.francodavyd.repository.IPermissionRepository;
import com.francodavyd.service.services.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private IPermissionRepository repository;

    @Override
    public List<Permission> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Permission save(Permission permission) {
        return repository.save(permission);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Permission update(Long id, Permission updatedPermission) {
        Optional<Permission> searchedPermission = this.findById(id);
        if (searchedPermission.isPresent()) {
            Permission per = searchedPermission.get();
            per.setPermissionName(updatedPermission.getPermissionName());
            return repository.save(per);
        }
        return null;
    }
}
>>>>>>> 1da008a75635c65c3167e712ecb49d3a2672cf0c
