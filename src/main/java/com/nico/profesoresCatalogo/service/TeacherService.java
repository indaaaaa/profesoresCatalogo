package com.nico.profesoresCatalogo.service;

import java.util.List;

import com.nico.profesoresCatalogo.model.Teacher;

public interface TeacherService {

	void saveTeacher(Teacher teacher);

	List<Teacher> buscarTodosLosTeachers();

	void eliminarTeacherPorId(long idTeacher);

	void actualizarTeacher(long idTeacher, String nombre, String avatar);
	
	void actualizarTeacher(Teacher teacher);

	Teacher buscarTeacherPorId(Long idTeacher);

	Teacher buscarTeacherPorNombre(String nombreTeacher);

}
