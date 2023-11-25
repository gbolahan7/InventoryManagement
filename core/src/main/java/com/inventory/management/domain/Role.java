package com.inventory.management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "name", unique = true, nullable = false, updatable = false)
	String name;

	@Column(name = "description")
	String description;

	@ManyToMany(mappedBy = "roles")
	private Collection<User> users;

	@ElementCollection
	@CollectionTable(name = "privilege_set", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "privileges")
	private Set<String> privileges;

}
