package com.nico.profesoresCatalogo.dao;

import java.util.List;


import com.nico.profesoresCatalogo.model.SocialMedia;
import com.nico.profesoresCatalogo.model.TeacherSocialMedia;

public interface SocialMediaDao {

	void guardarSocialMedia(SocialMedia socialMedia);

	void eliminarSocialMediaPorId(long idSocialMedia);

	void actualizarSocialMedia(SocialMedia socialMedia);

	List<SocialMedia> encontrarTodosLasSocialMedia();

	SocialMedia buscarSocialMediaPorId(Long idSocialMedia);
	
	TeacherSocialMedia buscarSocialMediaPorIdyPorNombre(Long idSocialMedia, String nickname);

	SocialMedia buscarSocialMediaPorNombre(String nombreSocialMedia);
	
	

}
