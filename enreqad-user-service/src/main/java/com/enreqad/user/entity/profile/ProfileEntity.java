package com.enreqad.user.entity.profile;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "enreqad_user", name = "profile_entity")
@EntityListeners(AuditingEntityListener.class)
public class ProfileEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "birthday", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "res_country", nullable = true)
    private String resCountry;

    @Column(name = "opr_country", nullable = false)
    private String oprCountry;

    @Column(name = "display_picture", nullable = true)
    @Lob
    private Byte[] display_picture;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles = new HashSet<>();

    public ProfileEntity(){

    };

    public ProfileEntity(
            long id,
            String firstName,
            String lastName,
            String username,
            Date birthday,
            String gender,
            String resCountry,
            String oprCountry,
            Byte[] display_picture
    )
    {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.birthday = birthday;
        this.gender = gender;
        this.resCountry = resCountry != null ? resCountry : "";
        this.oprCountry = oprCountry;
        if (display_picture != null) this.display_picture = display_picture;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getResCountry() {
        return resCountry;
    }

    public void setResCountry(String resCountry) {
        this.resCountry = resCountry;
    }

    public String getOprCountry() {
        return oprCountry;
    }

    public void setOprCountry(String oprCountry) {
        this.oprCountry = oprCountry;
    }

    public Byte[] getDisplay_picture() {
        return display_picture;
    }

    public void setDisplay_picture(Byte[] display_picture) {
        this.display_picture = display_picture;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
