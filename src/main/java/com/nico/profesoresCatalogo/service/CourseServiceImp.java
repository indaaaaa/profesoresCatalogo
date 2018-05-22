package com.nico.profesoresCatalogo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.profesoresCatalogo.dao.CourseDao;
import com.nico.profesoresCatalogo.model.Course;

@Service("courseService")
@Transactional
public class CourseServiceImp implements CourseService{

	@Autowired
	private CourseDao _courseDao;
	
	@Override
	public void guardarCurso(Course curso) {
		_courseDao.guardarCurso(curso);
		
	}

	@Override
	public void eliminarCursoPorId(long idCurso) {
		_courseDao.eliminarCursoPorId(idCurso);
		
	}

	@Override
	public void actualizarCurso(Course curso) {
		_courseDao.actualizarCurso(curso);
	}

	@Override
	public List<Course> encontrarTodosLosCursos() {
		return _courseDao.encontrarTodosLosCursos();
	}

	@Override
	public Course buscarCoursePorId(Long idCourse) {
		return _courseDao.buscarCoursePorId(idCourse);
	}

	@Override
	public Course buscarCoursePorNombre(String nombreCourse) {
		
		return _courseDao.buscarCoursePorNombre(nombreCourse);
	}

	@Override
	public List<Course> encontrarCursoPorIdTeacher(long idTeacher) {
		
		return _courseDao.encontrarCursoPorIdTeacher(idTeacher);
	}

}
