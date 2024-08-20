<<<<<<< HEAD
package com.francodavyd.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable (name = "roles_permissions", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns=@JoinColumn(name = "permission_id"))
    private Set<Permission> permissionsList = new HashSet<>();
}
=======
package com.francodavyd.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable (name = "roles_permissions", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns=@JoinColumn(name = "permission_id"))
    private Set<Permission> permissionsList = new HashSet<>();
}
>>>>>>> 1da008a75635c65c3167e712ecb49d3a2672cf0c
