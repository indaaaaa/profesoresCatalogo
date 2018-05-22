package com.nico.profesoresCatalogo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import com.nico.profesoresCatalogo.model.*;

import com.nico.profesoresCatalogo.dao.*;

@Repository
@Transactional
public class TeacherDaoImp extends AbstractSession implements TeacherDao {

	public TeacherDaoImp() {

	}

	public void saveTeacher(Teacher teacher) {

		getSession().persist(teacher);

	}

	public List<Teacher> buscarTodosLosTeachers() {

		Session session = getSession();

		return session.createQuery("from Teacher").list();

	}

	public void eliminarTeacherPorId(long idTeacher) {

		Session session = getSession();

		List<Teacher> listaTeachers = session.createQuery("from Teacher").list();

		for (Teacher t : listaTeachers) {

			if (idTeacher == t.getIdTeacher()) {

				session.delete(t);
				
			}

		}

	}

	public void actualizarTeacher(long idTeacher, String nombre, String avatar) {

		Session session = getSession();

		List<Teacher> listaTeachers = session.createQuery("from Teacher").list();

		for (Teacher t : listaTeachers) {

			if (idTeacher == t.getIdTeacher()) {

				t.setName(nombre);
				t.setAvatar(avatar);

				session.update(t);
				
			}

		}

	}

	public Teacher buscarTeacherPorId(Long idTeacher) {

		return (Teacher)getSession().get(Teacher.class, idTeacher);

	}

	public Teacher buscarTeacherPorNombre(String name) {

		return (Teacher)getSession().createQuery("From Teacher where name = :name").setMaxResults(1).setParameter("name", name).uniqueResult();
	}

	@Override
	public void actualizarTeacher(Teacher teacher) {
		getSession().update(teacher);
		
	}

}
