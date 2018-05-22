package com.nico.profesoresCatalogo.controller;

//import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.nico.profesoresCatalogo.model.SocialMedia;
import com.nico.profesoresCatalogo.model.Teacher;
import com.nico.profesoresCatalogo.model.TeacherSocialMedia;
import com.nico.profesoresCatalogo.service.SocialMediaService;
import com.nico.profesoresCatalogo.service.TeacherService;
import com.nico.profesoresCatalogo.util.CustomizarError;


@Controller
@RequestMapping(value="/v1")
public class TeacherController {
	
	@Autowired
	private TeacherService _teacherService;
	
	@Autowired
	private SocialMediaService _socialMediaService;

	
	//teacherPorNombreOPorTodos
	@RequestMapping(value="/teachers", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<Teacher>> traerTeacherPorNombreOTodosLosTeachers(@RequestParam(value="name", required = false) String name){
		
		List<Teacher> listaDeTeachers = new ArrayList<>();
		
		if (name == null) {
			
					
			
			listaDeTeachers = _teacherService.buscarTodosLosTeachers();
			
			return new ResponseEntity<List<Teacher>>(listaDeTeachers, HttpStatus.OK);
			
		}
		
		
		Teacher teacherEncontradoPorNombre = _teacherService.buscarTeacherPorNombre(name);
		
		if (teacherEncontradoPorNombre == null) {
			
			return new ResponseEntity(new CustomizarError("el teacher no existe"), HttpStatus.NOT_FOUND);
			
		}
		
		
		listaDeTeachers.add(teacherEncontradoPorNombre);
		
		return new ResponseEntity<List<Teacher>>(listaDeTeachers, HttpStatus.OK);
		
		
	
		
		
	}
	
	
	//TeacherPorId
	@RequestMapping(value="teachers/{id}", method = RequestMethod.GET, headers="Accept=application/json")
	public ResponseEntity<Teacher> teacherPorId(@PathVariable("id") Long idTeacher){
		
		
		
		if (idTeacher <= 0) {
			
			
			
			return new ResponseEntity(new CustomizarError("el id de teacher es menor que cero"), HttpStatus.OK);
			
		}
		
		Teacher teacher = _teacherService.buscarTeacherPorId(idTeacher);
		
		if (teacher == null) {
			
			return new ResponseEntity(new CustomizarError("el teacher es nulo") ,HttpStatus.NOT_FOUND);
			
		}
		
		
		
		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
		
	}
	
	
	//POST
	@RequestMapping(value="/teachers", method = RequestMethod.POST, headers="Accept=application/json")
	public ResponseEntity<?> crearTeacher(@RequestBody Teacher teacher, UriComponentsBuilder ucb){
	
	
		if (teacher == null) {
			
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
			
		}
		
		List<Teacher> listaDeTeachers = new ArrayList<>();
		
		Teacher teacherEncontradoPorNombre = _teacherService.buscarTeacherPorNombre(teacher.getName());
		
		if(teacherEncontradoPorNombre != null){
		
		
				
				return new ResponseEntity(new CustomizarError("El teacher ya existe y no se puede crear"), HttpStatus.NOT_ACCEPTABLE);
				
		
		
		}
			
		_teacherService.saveTeacher(teacher);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		
		
		httpHeaders.setLocation(ucb.path("v1/teachers/{id}").buildAndExpand(teacher.getIdTeacher()).toUri());
		
		return new ResponseEntity<String>(httpHeaders, HttpStatus.OK);
		
		
		}
	
	
	//UPDATE
	@RequestMapping(value="/teachers/{id}", method = RequestMethod.PATCH, headers="Accept=application/json")
	public ResponseEntity<Teacher> actualizarTeacher(@RequestBody Teacher teacher, @PathVariable("id") Long idTeacher){
		
		
		if (teacher == null || idTeacher <=0) {
			
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
			
		}
		
		List<Teacher> listaDeTodosLosTeachers = new ArrayList<>();
		
		listaDeTodosLosTeachers = _teacherService.buscarTodosLosTeachers();
		
		
		for (Teacher teacheri : listaDeTodosLosTeachers) {
			
			if (idTeacher != teacheri.getIdTeacher()) {
				return new ResponseEntity(new CustomizarError("El id no existe"), HttpStatus.NOT_FOUND);
			}
			
		}
		
		
		_teacherService.actualizarTeacher(idTeacher, teacher.getName(), teacher.getAvatar());
		
		return new ResponseEntity<Teacher>(HttpStatus.OK);
		
		
	}
	
	
	//eliminarTeacher
	
	@RequestMapping(value="/teachers/{id}", method = RequestMethod.DELETE, headers="Accept=application/json")
	public ResponseEntity<Teacher> deleteTeacher(@PathVariable("id") Long idTeacher){
		
		if (idTeacher <= 0) {
			
			return new ResponseEntity(new CustomizarError("El id de teacher no puede ser menor a 0"), HttpStatus.NOT_ACCEPTABLE);
			
		}
		
		Teacher teacherEncontradoPorIdAEliminar = _teacherService.buscarTeacherPorId(idTeacher);
		
		
		if (teacherEncontradoPorIdAEliminar == null) {
			
			return new ResponseEntity(new CustomizarError("El teacher no existe"), HttpStatus.NOT_FOUND);
		}
		
		_teacherService.eliminarTeacherPorId(idTeacher);
		
		return new ResponseEntity<Teacher>(teacherEncontradoPorIdAEliminar, HttpStatus.OK);
		
		
	}
	
	public static final String TEACHER_UPLOADED_FOLDER = "imagenes/teachersImagenes/";
	
	
	
	
	
	
	
	
	
	//CrearOCargarImagen
	@RequestMapping(value="/teachers/imagenes", method = RequestMethod.POST, headers=("content-type=multipart/form-data"))
	public ResponseEntity<byte[]> crearImagen(@RequestParam("id_teacher") Long idTeacher, @RequestParam ("imagen") MultipartFile multipartFile, UriComponentsBuilder uriComponetsBuilder){
		
		
		
		
		
		if (idTeacher == null) {
			
			return new ResponseEntity(new CustomizarError("el id de teacher es menor que cero o es nulo"), HttpStatus.NO_CONTENT);
						
		}
		
		if (multipartFile.isEmpty()) {
			
			return new ResponseEntity(new CustomizarError("el archivo esta vacio o es nulo"), HttpStatus.NO_CONTENT);
		}
		
		
		
	     Teacher teacher = _teacherService.buscarTeacherPorId(idTeacher);
		
	     if (teacher == null) {
			
	    	 return new ResponseEntity(new CustomizarError("el id de teacher es menor que cero o es nulo, no existe"), HttpStatus.NOT_FOUND);
	    	 
		}
	     
	     
	     if (!teacher.getAvatar().isEmpty() ||  teacher.getAvatar() != null ) {
			
	    	 String fileName = teacher.getAvatar();
	    	 
	    	 Path path = Paths.get(fileName);
	    	 
	    	 File filaObtenida = path.toFile();
	    	 
	    	   	 
	    	 if (filaObtenida.exists()) {
				
	    		 filaObtenida.delete();
	    		 
			}
	     }
	    	 
	    	 
	    	 
	    	 try {
	    		 
	    		 Date date = new Date();
		    	 
		    	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		    	 
		    	 String dateName = dateFormat.format(date);
		    	 
		    	 String fileName = String.valueOf(idTeacher) + "-pictureTeacher-" +  dateName + "." + multipartFile.getContentType().split("/")[1];
		    	 
		    	 teacher.setAvatar(TEACHER_UPLOADED_FOLDER + fileName);
	    		 
				byte[] imagen = multipartFile.getBytes();
				
								
				Path path = Paths.get(TEACHER_UPLOADED_FOLDER + fileName);
				
				Files.write(path, imagen);
				
				
				
								
				_teacherService.actualizarTeacher(teacher);
				
				
				
			    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
				
			    
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
				return new ResponseEntity(new CustomizarError("error durante la carga") + multipartFile.getOriginalFilename(), HttpStatus.BAD_REQUEST);
			}
	    	 
	    	 
	    	 
		
	     
		
	    
		
	}
	
	
	//EliminarImagen
	
	@RequestMapping(value="/teachers/{id}/imagenes", method = RequestMethod.DELETE, headers="Accept=application/json")
	public ResponseEntity<?> eliminarImagen(@PathVariable("id") Long idTeacher){
		
		
		if (idTeacher == null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			
		}
		
		Teacher teacher = _teacherService.buscarTeacherPorId(idTeacher);
		
		if (teacher == null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			
		}
		
		
		String nombreDelArchivo = teacher.getAvatar();
		
		Path path = Paths.get(nombreDelArchivo);
		
		File filaObtenida = path.toFile();
		
		if (filaObtenida.exists()) {
			
			filaObtenida.delete();
			
		}
		
		teacher.setAvatar("");
		
		_teacherService.actualizarTeacher(teacher);
		
		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
				
		
	}
	
	//agregar red social a teacher
	@RequestMapping(value="/teachers/socialMedias", method = RequestMethod.PATCH, headers="Accept=application/json")
	public ResponseEntity<?> agregarRedSocialATeacher(@RequestBody Teacher teacher){
		
		
		/* chequeamos que el id del teacher que viene por postman no sea nulo - ID TEACHER CHEQUEADO */
		if ((Long)teacher.getIdTeacher() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		/*si ese id no es nulo, entonces buscamos que exista un teacher con ese id en la base de datos -  CHEQUEADO QUE EXISTA EL TEACHER EN LA BD */
		Teacher teacherGuardado = _teacherService.buscarTeacherPorId(teacher.getIdTeacher());
		if (teacherGuardado == null) {
		  return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
		/*aca chequea que desde postman venga alguna social media a asignar, entendamos que dentro de teacherSocialMedia viene la socialMedia
		 * CHEQUEAMOS QUE VENGAN REDES SOCIALES PARA ASIGNAR AL TEACHER */
		if (teacher.getTeacherSocialMedias().size() == 0) {
			return new ResponseEntity<>(new CustomizarError("se necesita al menos, id de teacher, id social media y nickname"), HttpStatus.NOT_ACCEPTABLE);
		}else{
			
			/*creamos una lista de redes sociales para almacenar las potenciales redes*/
			List<TeacherSocialMedia> listaDeRedesSocialesDelTeacher = new ArrayList<>();
			
			listaDeRedesSocialesDelTeacher = teacher.getTeacherSocialMedias();
			//recordemos que aca estamos iterando la lista de redes sociales, o la red social que nos enviaron via json en postman
			for (TeacherSocialMedia teacherSocialMediai : listaDeRedesSocialesDelTeacher) {
				
				TeacherSocialMedia teacherSocialMedia = teacherSocialMediai;
				
				/*verificamos que el id o nickname que nos pasan por postman no sea nulo - CHEQUEADO ID SOCIAL MEDIA Y NICKNAME */
				if ((Long)teacherSocialMedia.getSocialMedia().getIdSocialMedia() == null || teacherSocialMedia.getNickname() == null) {
					return new ResponseEntity<>(new CustomizarError("la social media no existe, o el nickname esta vacio"), HttpStatus.NOT_ACCEPTABLE);
				}
				
				
				/* Busca un red social por nombre y por nickname, esto se almacena en un objeto teacherSocialMedia
				 * ya que es el unico objeto que permite almacenar ambas cosas, tanto un id de social media como 
				 * un nickname */
				TeacherSocialMedia tsmAux = _socialMediaService.buscarSocialMediaPorIdyPorNombre(
						teacherSocialMedia.getSocialMedia().getIdSocialMedia(), teacherSocialMedia.getNickname());
				
				if (tsmAux != null) {
					
					return new ResponseEntity<>(new CustomizarError("la red social ya esta asignada a este teacher"), HttpStatus.NOT_ACCEPTABLE);
					
				}
				
				 /* aca chequeamos que los campos de la tsm que almacenamos buscando por nombre y por nickname no sean nulos.- */
				/*if ((Long)tsmAux.getSocialMedia().getIdSocialMedia() == null || tsmAux.getNickname() == null) {
					return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
				}*/
				
				/* ahora tratamos de chequear que la socialMedia que viene por postman exista en la BASE DE DATOS */
				SocialMedia socialMedia = _socialMediaService.buscarSocialMediaPorId(
						teacherSocialMedia.getSocialMedia().getIdSocialMedia());
				
				if (socialMedia == null) {
					
					return new ResponseEntity<>(new CustomizarError("la social media no existe"), HttpStatus.NOT_ACCEPTABLE);
					
				}
				
				
				teacherSocialMedia.setSocialMedia(socialMedia);
				teacherSocialMedia.setTeacher(teacherGuardado);
				
				if(tsmAux == null){
					
					teacherGuardado.getTeacherSocialMedias().add(teacherSocialMedia);
					
				}else{
					
					LinkedList<TeacherSocialMedia> teacherSocialMedias = new LinkedList<>();
					teacherSocialMedias.addAll(teacherGuardado.getTeacherSocialMedias());
					
					
					for (int j = 0 ; j < teacherSocialMedias.size(); j++) {
						
						TeacherSocialMedia teacherSocialMediaX = teacherSocialMedias.get(j);
						
						if (teacherSocialMedia.getTeacher().getIdTeacher() == teacherSocialMediaX.getTeacher().getIdTeacher()
							&& teacherSocialMedia.getSocialMedia().getIdSocialMedia() == teacherSocialMediaX.getSocialMedia().getIdSocialMedia()) {
							
							teacherSocialMediaX.setNickname(teacherSocialMedia.getNickname());
							
							teacherSocialMedias.set(j, teacherSocialMediaX);
							
							
						}
						else{
							
							teacherSocialMedias.set(j, teacherSocialMediaX);
							
						}
						
					}
					

					teacherGuardado.getTeacherSocialMedias().clear();
					teacherGuardado.getTeacherSocialMedias().addAll(teacherSocialMedias);
					
				}
				
				
				
			}
			
			
			
			
			
		}
		
		_teacherService.actualizarTeacher(teacherGuardado);
		return new ResponseEntity<Teacher>(teacherGuardado, HttpStatus.OK);
		
	}
	
	
	
	
}
	
	

