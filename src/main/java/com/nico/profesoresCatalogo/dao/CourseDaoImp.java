package com.nico.profesoresCatalogo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.nico.profesoresCatalogo.model.Course;
import com.nico.profesoresCatalogo.model.Teacher;

@Repository
@Transactional
public class CourseDaoImp extends AbstractSession implements CourseDao {

	@Override
	public void guardarCurso(Course curso) {
		getSession().persist(curso);

	}

	@Override
	public void eliminarCursoPorId(long idCurso) {
		List<Course> listaDeCursos = getSession().createQuery("from Course").list();

		for (Course coursei : listaDeCursos) {
			if (coursei.getIdCourse() == idCurso) {
				getSession().delete(coursei);
			}
		}

	}

	@Override
	public void actualizarCurso(Course curso) {
		getSession().update(curso);

	}

	@Override
	public List<Course> encontrarTodosLosCursos() {

		List<Course> listaDeCursos = getSession().createQuery("from Course").list();

		return listaDeCursos;
	}

	@Override
	public Course buscarCoursePorId(Long idCourse) {
		return getSession().get(Course.class, idCourse);
	}

	@Override
	public Course buscarCoursePorNombre(String nombreCourse) {

		return (Course) getSession().createQuery("from Course where name = :nombreCourse")
				.setParameter("nombreCourse", nombreCourse).uniqueResult();

	}

	@Override
	public List<Course> encontrarCursoPorIdTeacher(long idTeacher) {
		
		 List<Course> listaCursosBD = getSession().createQuery("from Course").list();
		 
		 List<Course> listaDeCursosCoincidentesConProfesor = new ArrayList();
		 
		 for (Course coursei : listaCursosBD) {
			if(coursei.getTeacher().getIdTeacher() == idTeacher){
				listaDeCursosCoincidentesConProfesor.add(coursei);
			}
		}
		
		return listaDeCursosCoincidentesConProfesor;
	}

}
