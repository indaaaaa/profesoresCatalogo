package com.nico.profesoresCatalogo.dao;

import java.util.List;
import com.nico.profesoresCatalogo.model.Teacher;

import com.nico.profesoresCatalogo.model.Teacher ;

public interface TeacherDao {
	
	void saveTeacher(Teacher teacher);
	
	List<Teacher> buscarTodosLosTeachers();
	
	void eliminarTeacherPorId(long idTeacher);
	
	void actualizarTeacher(long idTeacher, String nombre, String avatar);
	
	void actualizarTeacher(Teacher teacher);
	
	Teacher buscarTeacherPorId(Long idTeacher);
	
	Teacher buscarTeacherPorNombre(String nombreTeacher);

}
