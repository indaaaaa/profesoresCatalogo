package com.nico.profesoresCatalogo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="social_media")
public class SocialMedia implements Serializable {

	@Id
	@Column(name="id_social_media")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idSocialMedia;
	
	@Column(name="name")
	private String name;
	
	@Column(name="icon")
	private String icon;
	
	@JsonManagedReference(value="c")
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="id_social_media")
	private List<TeacherSocialMedia> teacherSocialMedias = new ArrayList<>();
	
	

	public SocialMedia() {
		super();
		
	}

	public SocialMedia(String name, String icon) {
		super();
		this.name = name;
		this.icon = icon;
		
	}

	public long getIdSocialMedia() {
		return idSocialMedia;
	}

	public void setIdSocialMedia(long idSocialMedia) {
		this.idSocialMedia = idSocialMedia;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
