package com.nico.profesoresCatalogo.service;

import java.util.List;

import com.nico.profesoresCatalogo.model.Course;

public interface CourseService {
	
void guardarCurso(Course curso);
	
	void eliminarCursoPorId(long idCurso);
	
	void actualizarCurso(Course curso);
	
	List<Course> encontrarTodosLosCursos();
	
	Course buscarCoursePorId(Long idCourse);
	
	Course buscarCoursePorNombre(String nombreCourse);
	
	List<Course> encontrarCursoPorIdTeacher(long idTeacher);

}
