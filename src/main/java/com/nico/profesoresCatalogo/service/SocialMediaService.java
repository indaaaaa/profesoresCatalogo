package com.nico.profesoresCatalogo.service;

import java.util.List;

import com.nico.profesoresCatalogo.model.SocialMedia;
import com.nico.profesoresCatalogo.model.TeacherSocialMedia;

public interface SocialMediaService {

	
	void guardarSocialMedia(SocialMedia socialMedia);

	void eliminarSocialMediaPorId(long idSocialMedia);

	void actualizarSocialMedia(SocialMedia socialMedia);

	List<SocialMedia> encontrarTodosLasSocialMedia();

	SocialMedia buscarSocialMediaPorId(Long idSocialMedia);

	SocialMedia buscarSocialMediaPorNombre(String nombreSocialMedia);
	
	TeacherSocialMedia buscarSocialMediaPorIdyPorNombre(Long idSocialMedia, String nickname);
	
}
