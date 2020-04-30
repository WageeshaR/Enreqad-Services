package com.enreqad.user.entity.profile;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "roles", schema = "enreqad_user")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 10)
    private UserRoleEnum roleEnum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserRoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(UserRoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }
}
