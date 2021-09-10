package com.olive.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="User_Jwt_tab")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="usr_name")
	private String userName;
	@Column(name="usr_pwd")
	private String password;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="Role_tab",
	joinColumns = @JoinColumn(name="uid")
	)
	@Column(name="usr_role")
	private List<String> roles;
	
}
