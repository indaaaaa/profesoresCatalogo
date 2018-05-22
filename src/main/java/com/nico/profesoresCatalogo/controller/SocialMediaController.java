package com.nico.profesoresCatalogo.controller;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.nico.profesoresCatalogo.model.SocialMedia;
import com.nico.profesoresCatalogo.service.SocialMediaService;
import com.nico.profesoresCatalogo.util.CustomizarError;

@Controller
@RequestMapping("/v1")
public class SocialMediaController {

	@Autowired
	private SocialMediaService _socialMediaService;

	// GET
		@RequestMapping(value = "/socialMedias", method = RequestMethod.GET, headers = "Accept=application/json")
		public ResponseEntity<List<SocialMedia>> socialMedias(@RequestParam(value="name",required=false) String name) {
			
			List<SocialMedia> listaDeSocialMedias = new ArrayList();
			
			if (name == null || name.isEmpty()) {
				
				

				listaDeSocialMedias = _socialMediaService.encontrarTodosLasSocialMedia();

				if (listaDeSocialMedias.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}

				return new ResponseEntity<List<SocialMedia>>(listaDeSocialMedias, HttpStatus.OK);
				
			}
			
			
				
				SocialMedia socialMediaNueva = _socialMediaService.buscarSocialMediaPorNombre(name);
				if (socialMediaNueva == null) {
					
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					
				}
				
				listaDeSocialMedias.add(socialMediaNueva);
				return new ResponseEntity<List<SocialMedia>>(listaDeSocialMedias, HttpStatus.OK);
				
			
			
			
			
			

		

	}

	// POST
	@RequestMapping(value = "/socialMedias", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> crearSocialMedia(@RequestBody SocialMedia socialMedia,
			UriComponentsBuilder uriComponentsBuilder) {

		if (socialMedia.getName().equals(null) || socialMedia.getName().isEmpty()) {
			return new ResponseEntity(new CustomizarError("El nombre no puede ser nulo"), HttpStatus.NOT_ACCEPTABLE);
		}

		if (_socialMediaService.buscarSocialMediaPorNombre(socialMedia.getName()) != null) {

			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

		}

		// se da por entendido que no existe en la base de datos.

		_socialMediaService.guardarSocialMedia(socialMedia);
		

		SocialMedia socialMedia2 = _socialMediaService.buscarSocialMediaPorNombre(socialMedia.getName());

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setLocation(uriComponentsBuilder.path("/v1/socialMedias/{id}")
				.buildAndExpand(socialMedia2.getIdSocialMedia()).toUri());

		return new ResponseEntity<String>(httpHeaders, HttpStatus.CREATED);

	}
	
	
	//GETparaTraerSocialMediasPorID
	
		@RequestMapping(value="socialMedias/{id}" , method = RequestMethod.GET, headers = "Accept=application/json")
		public ResponseEntity<SocialMedia> socialMediasPorId(@PathVariable("id") Long idSocialMedia){
			
			if(idSocialMedia == null || idSocialMedia <= 0){
				
				return new ResponseEntity<SocialMedia>(HttpStatus.NOT_ACCEPTABLE);
				
			}
			
		
			
			SocialMedia socialMediaConId = _socialMediaService.buscarSocialMediaPorId(idSocialMedia);
			
			
			
			if(socialMediaConId == null){
				
				return new ResponseEntity<SocialMedia>(HttpStatus.NOT_ACCEPTABLE);
				
			}
			
			return new ResponseEntity<SocialMedia>(socialMediaConId, HttpStatus.OK);
		
			
		}
		
		//PATCH
		
		@RequestMapping(value="socialMedias/{id}" , method = RequestMethod.PATCH, headers = "Accept=application/json")
		public ResponseEntity<?> actualizarSocialMedia(@PathVariable("id") Long idSocialMedia, @RequestBody SocialMedia socialMedia){
			
			if(idSocialMedia <= 0 || socialMedia == null){
				
				return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
				
			}
			
			SocialMedia socialMediaEncontradaAActualizar = _socialMediaService.buscarSocialMediaPorId(idSocialMedia);
			
			if(socialMediaEncontradaAActualizar == null){
				
				return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
				
			}
			
			socialMediaEncontradaAActualizar.setName(socialMedia.getName());
			socialMediaEncontradaAActualizar.setIcon(socialMedia.getIcon());
			
			
			
			_socialMediaService.actualizarSocialMedia(socialMediaEncontradaAActualizar);
			
			
			
			
			return new ResponseEntity<SocialMedia>(socialMediaEncontradaAActualizar, HttpStatus.OK);
			
			
		}
		
	//DELETE
		
		@RequestMapping(value="socialMedias/{id}" , method = RequestMethod.DELETE, headers = "Accept=application/json")
		public ResponseEntity<?> eliminarSocialMedia(@PathVariable("id") Long idSocialMedia){
			
			if(idSocialMedia <= 0){
				
				return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
				
			}
			
			SocialMedia socialMediaAEliminar = _socialMediaService.buscarSocialMediaPorId(idSocialMedia);
			
			if(socialMediaAEliminar == null){
				
				return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
				
			}
			
			_socialMediaService.eliminarSocialMediaPorId(idSocialMedia);
			
			return new ResponseEntity<SocialMedia>(HttpStatus.OK);
			
			
		}
		
		
		//CargarIconoSocialMedia
		
		public static final String RUTA_IMAGENES = "imagenes/socialMediaImagenes/";
		
		@RequestMapping(value="/socialMedias/imagenes", method = RequestMethod.POST, headers=("content-type=multipart/form-data"))
		public ResponseEntity<byte[]> crearIconoSocialMedia(@RequestParam("id_social_media") Long idSocialMedia,
		@RequestParam("file") MultipartFile multipartFile){
			
			
			
			if (idSocialMedia == null) {
				
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
				
			}
			
			if (multipartFile.isEmpty() || multipartFile == null) {
				
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			SocialMedia socialMedia = _socialMediaService.buscarSocialMediaPorId(idSocialMedia);
			
			if (socialMedia == null) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			if (socialMedia.getIcon() != null) {
				
				String nombreIcono = socialMedia.getIcon();
				
				Path path = Paths.get(nombreIcono);
				
				File filaObtenida = path.toFile();
				
				if (filaObtenida.exists()) {
					
					filaObtenida.delete();
				}
				
			}
			
		   try {
			
			   Date date = new Date();
			   
			   SimpleDateFormat simpleDateFormato = new SimpleDateFormat("yyyy-MM-HH-mm-ss");
			   
			   String formato = simpleDateFormato.format(date);
			   
			   
				
				
			   
			   String rutaConformada = String.valueOf(idSocialMedia) + " - " + " imagenesSocialMedia" + " - " + formato + "-"  + "." + multipartFile.getContentType().split("/")[1];
			   
			   socialMedia.setIcon(RUTA_IMAGENES + rutaConformada ); 
			   
			   byte[] imagen = multipartFile.getBytes();
			   
			   Path rutaDondeSeGuardaraLaImagenCompletamenteEspecificada = Paths.get(RUTA_IMAGENES + rutaConformada);
			   			   
			   /*anotacion importante, se debe especificar al maximo donde se guardara el archivo, 
			    no alcanca con pasarle la ruta, sino que hay que pasarle la ruta, y el nombre donde se guardara*/
			   
			   Files.write(rutaDondeSeGuardaraLaImagenCompletamenteEspecificada, imagen);
			   
			   _socialMediaService.actualizarSocialMedia(socialMedia);
			   
			   
			   return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
			   
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			
		}
		
		@RequestMapping(value="/socialMedias/{id}/imagenes", method = RequestMethod.DELETE, headers="Accept=application/json")
		public ResponseEntity<?> eliminarIconoSocialMedia(@PathVariable("id") Long idSocialMedia){
			
			if (idSocialMedia == null) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			SocialMedia socialMedia = _socialMediaService.buscarSocialMediaPorId(idSocialMedia);
			
			if (socialMedia == null) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			String nombreIcono = socialMedia.getIcon();
			
			Path path = Paths.get(nombreIcono);
			
			File filaObtenida = path.toFile();
			
			if (filaObtenida.exists()) {
				filaObtenida.delete();
			}
			
			socialMedia.setIcon("");
			
			_socialMediaService.actualizarSocialMedia(socialMedia);
			
			return new ResponseEntity<SocialMedia>(socialMedia, HttpStatus.OK);
			
			
			
		}
		
		

}
