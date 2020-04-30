package com.enreqad.user.controller.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class UserProfileRequest {

    @NotBlank
    private long id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 20)
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 20)
    private String username;

    @NotBlank
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date birthday;

    @NotBlank
    private String gender;

    private String resCountry;

    @NotBlank
    private String oprCountry;

    @Size(max = 2000000)
    private Byte[] display_picture;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setUsername(String username) {
        this.username = username;
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
}
