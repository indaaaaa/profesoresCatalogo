package com.nico.profesoresCatalogo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.profesoresCatalogo.dao.TeacherDao;
import com.nico.profesoresCatalogo.model.Teacher;

@Service("teacherService")
@Transactional
public class TeacherServiceImp implements TeacherService {

	@Autowired
	private TeacherDao _teacherDao;
	
	@Override
	public void saveTeacher(Teacher teacher) {
		_teacherDao.saveTeacher(teacher);		
	}

	@Override
	public List<Teacher> buscarTodosLosTeachers() {
		// TODO Auto-generated method stub
		return _teacherDao.buscarTodosLosTeachers();
	}

	@Override
	public void eliminarTeacherPorId(long idTeacher) {
		_teacherDao.eliminarTeacherPorId(idTeacher);		
	}

	@Override
	public void actualizarTeacher(long idTeacher, String nombre, String avatar) {
		_teacherDao.actualizarTeacher(idTeacher, nombre, avatar);		
	}

	@Override
	public Teacher buscarTeacherPorId(Long idTeacher) {
		// TODO Auto-generated method stub
		return _teacherDao.buscarTeacherPorId(idTeacher);
	}

	@Override
	public Teacher buscarTeacherPorNombre(String nombreTeacher) {
		// TODO Auto-generated method stub
		return _teacherDao.buscarTeacherPorNombre(nombreTeacher);
	}

	@Override
	public void actualizarTeacher(Teacher teacher) {
		_teacherDao.actualizarTeacher(teacher);
		
	}

}
