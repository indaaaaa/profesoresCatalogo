package com.nico.profesoresCatalogo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="teacher")
public class Teacher implements Serializable {
	
	@Id
	@Column(name="id_teacher")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idTeacher;
	
	@Column(name="name")
	private String name;
	
	@Column(name="avatar")
	private String avatar;
	
	
	@JsonManagedReference(value="a")
	@OneToMany(mappedBy="teacher")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Course> courses;
	
	@JsonManagedReference(value="b")
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="id_teacher")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TeacherSocialMedia> teacherSocialMedias = new ArrayList<>();
	
	
	
	
	
	public Teacher(String name, String avatar) {
		
		this.name = name;
		this.avatar = avatar;
		
		
	}
	public Teacher() {
		
	}
	public long getIdTeacher() {
		return idTeacher;
	}
	public void setIdTeacher(long idTeacher) {
		this.idTeacher = idTeacher;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<TeacherSocialMedia> getTeacherSocialMedias() {
		return teacherSocialMedias;
	}
	public void setTeacherSocialMedias(List<TeacherSocialMedia> teacherSocialMedias) {
		this.teacherSocialMedias = teacherSocialMedias;
	}
	
	
	
	
}
