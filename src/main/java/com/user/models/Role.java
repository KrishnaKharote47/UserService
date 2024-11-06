package com.user.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Role extends BaseModel {

    private String value;

//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;
}
