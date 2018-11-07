package com.filip.financesrest.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "entry_category")
public class EntryCategory
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(min = 3, message = "Category name must be at least 3 characters long.")
	private String name;


	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<FinanceEntry> entries;

	public EntryCategory()
	{
	}

	public EntryCategory(String name, User user, List<FinanceEntry> entries)
	{
		this.name = name;
		this.user = user;
		this.entries = entries;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public List<FinanceEntry> getEntries()
	{
		return entries;
	}

	public void setEntries(List<FinanceEntry> entries)
	{
		this.entries = entries;
	}
}
