package com.nico.profesoresCatalogo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.nico.profesoresCatalogo.model.Course;
import com.nico.profesoresCatalogo.model.SocialMedia;
import com.nico.profesoresCatalogo.service.CourseService;
import com.nico.profesoresCatalogo.util.CustomizarError;

@Controller
@RequestMapping(value="/v1")
public class CourseController {
	
	@Autowired
	private CourseService _courseService;
	
	
	//GET_POR_NOMBRE_O_TODOS_LOS_CURSOS
	
	@RequestMapping(value="/courses", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<Course>> obtenerListaDeCursos(@RequestParam(value="name", required=false) String name){
		
		
		List<Course> listaDeCursos = new ArrayList();
		
		if (name == null || name.isEmpty()) {
			
			listaDeCursos = _courseService.encontrarTodosLosCursos();
			
			return new ResponseEntity<List<Course>>(listaDeCursos, HttpStatus.OK);
			
			
			
				
						
		}
		
		 Course curso = _courseService.buscarCoursePorNombre(name);
		 
		 listaDeCursos.add(curso);
		 
		 return new ResponseEntity<List<Course>>(listaDeCursos, HttpStatus.OK);
		
		
	}
	
	//GET_POR_ID_O_TODOS_LOS_CURSOS
	
	@RequestMapping(value="courses/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Course> obtenerListaDeCursosPorId(@PathVariable("id") Long idCourse){
		
		if (idCourse <= 0) {
			
			return new ResponseEntity(new CustomizarError("el id course no puede ser menor que 0"), HttpStatus.NOT_ACCEPTABLE);
			
		}
		
		
		
		Course cursoEncontradoPorId = _courseService.buscarCoursePorId(idCourse);
		
		
		 
		
		if (cursoEncontradoPorId == null ) {
			
			return new ResponseEntity(new CustomizarError("El curso es nulo"), HttpStatus.NOT_FOUND);
			
		}
		
		
		
		return new ResponseEntity<Course>(cursoEncontradoPorId, HttpStatus.OK);
		
		
	}
	
	//POST
	@RequestMapping(value="/courses", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> crearCurso(@RequestBody Course curso, UriComponentsBuilder ucb){
		
		List<Course> listaDeCursos = _courseService.encontrarTodosLosCursos();
		
		Course cursoPorNombre = _courseService.buscarCoursePorNombre(curso.getName());
		
		if(cursoPorNombre != null){
		
		for (Course coursei : listaDeCursos) {
			
			if (cursoPorNombre.getName().equals(coursei.getName())) {
				
				return new ResponseEntity(new CustomizarError("El curso ya existe"), HttpStatus.IM_USED);
				
			}
			
			
		}
		
		}
		
		_courseService.guardarCurso(curso);
		
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		httpHeaders.setLocation(ucb.path("/v1/courses/{id}").buildAndExpand(curso.getIdCourse()).toUri());
		
		return new ResponseEntity<String>(httpHeaders, HttpStatus.CREATED);
		
		
		
	}
	
	//UPDATE
	
	
	@RequestMapping(value="/courses/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
	public ResponseEntity<Course> updateCurso(@PathVariable("id") Long idCourse, @RequestBody Course curso){
		
		if(curso == null){
			
			return new ResponseEntity(new CustomizarError("el curso es nulo"), HttpStatus.NOT_ACCEPTABLE);
			
			
		}
		
		
		
		Course cursoEncontradoPorId = _courseService.buscarCoursePorId(idCourse);
		
		if (cursoEncontradoPorId == null) {
			
			return new ResponseEntity(new CustomizarError("el curso no existe"), HttpStatus.NOT_ACCEPTABLE);
			
		}
		
		
		cursoEncontradoPorId.setName(curso.getName());
		cursoEncontradoPorId.setProject(curso.getProject());
		cursoEncontradoPorId.setThemes(curso.getThemes());
			
			_courseService.actualizarCurso(cursoEncontradoPorId);
			
			return new ResponseEntity<Course>(cursoEncontradoPorId, HttpStatus.OK);
			
		
		
		
	}
	
	
	//ElIMINAR CURSO
	
	@RequestMapping(value="/courses/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	ResponseEntity<Course> deleteCurso(@PathVariable("id") long idCourse){
		
		if (idCourse <= 0) {
			
		return	new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
			
		}
		
		 Course cursoABorrarEncontrado = _courseService.buscarCoursePorId(idCourse);
		 
		 if(cursoABorrarEncontrado == null){
			 
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		 }
		 
		 
		 _courseService.eliminarCursoPorId(idCourse);
		 
		return new ResponseEntity<Course>(HttpStatus.OK);
		
	}
	
	
	
	

}
