package com.filip.financesrest.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User
{
    @JsonIgnore
    private long id;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String passwordConfirm;
    @JsonIgnore
    private Set<Role> roles;
    private Set<FinanceEntry> entries;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public Set<FinanceEntry> getEntries()
    {
        return entries;
    }

    public void setEntries(Set<FinanceEntry> entries)
    {
        this.entries = entries;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm()
    {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm)
    {
        this.passwordConfirm = passwordConfirm;
    }


    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }
}
