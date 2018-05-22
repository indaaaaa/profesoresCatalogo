package com.nico.profesoresCatalogo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.profesoresCatalogo.dao.SocialMediaDao;
import com.nico.profesoresCatalogo.model.SocialMedia;
import com.nico.profesoresCatalogo.model.TeacherSocialMedia;

@Service("socialMediaService")
@Transactional
public class SocialMediaServiceImp implements SocialMediaService {
	
	@Autowired
	private SocialMediaDao _socialMediaDao;

	@Override
	public void guardarSocialMedia(SocialMedia socialMedia) {
		_socialMediaDao.guardarSocialMedia(socialMedia);
		
	}

	@Override
	public void eliminarSocialMediaPorId(long idSocialMedia) {
		_socialMediaDao.eliminarSocialMediaPorId(idSocialMedia);
		
	}

	@Override
	public void actualizarSocialMedia(SocialMedia socialMedia) {
		_socialMediaDao.actualizarSocialMedia(socialMedia);
		
	}

	@Override
	public List<SocialMedia> encontrarTodosLasSocialMedia() {
		
		return _socialMediaDao.encontrarTodosLasSocialMedia();
	}

	@Override
	public SocialMedia buscarSocialMediaPorId(Long idSocialMedia) {
		
		return _socialMediaDao.buscarSocialMediaPorId(idSocialMedia);
	}

	@Override
	public SocialMedia buscarSocialMediaPorNombre(String name) {
		
		return _socialMediaDao.buscarSocialMediaPorNombre(name);
	}

	@Override
	public TeacherSocialMedia buscarSocialMediaPorIdyPorNombre(Long idSocialMedia, String nickname) {
		// TODO Auto-generated method stub
		return _socialMediaDao.buscarSocialMediaPorIdyPorNombre(idSocialMedia, nickname);
	}

}
