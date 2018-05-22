package com.nico.profesoresCatalogo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.nico.profesoresCatalogo.model.Course;
import com.nico.profesoresCatalogo.model.SocialMedia;
import com.nico.profesoresCatalogo.model.TeacherSocialMedia;

@Repository
@Transactional
public class SocialMediaDaoImp extends AbstractSession implements SocialMediaDao {

	@Override
	public void guardarSocialMedia(SocialMedia socialMedia) {
		getSession().persist(socialMedia);

	}

	@Override
	public void eliminarSocialMediaPorId(long idSocialMedia) {
		List<SocialMedia> listaSocialMedia = getSession().createQuery("from SocialMedia").list();

		for (SocialMedia socialMediai : listaSocialMedia) {
			if (socialMediai.getIdSocialMedia() == idSocialMedia) {
				getSession().delete(socialMediai);
			}
		}

	}

	@Override
	public void actualizarSocialMedia(SocialMedia socialMedia) {
		getSession().update(socialMedia);

	}

	@Override
	public List<SocialMedia> encontrarTodosLasSocialMedia() {
		List<SocialMedia> listaDeSocialMedia = getSession().createQuery("from SocialMedia").list();

		return listaDeSocialMedia;
	}

	@Override
	public SocialMedia buscarSocialMediaPorId(Long idSocialMedia) {
		return getSession().get(SocialMedia.class, idSocialMedia);
	}

	@Override
	public SocialMedia buscarSocialMediaPorNombre(String nombreSocialMedia) {
		return (SocialMedia) getSession().createQuery("from SocialMedia where name = :otroParametro")
				.setParameter("otroParametro", nombreSocialMedia).uniqueResult();
	}

	@Override
	public TeacherSocialMedia buscarSocialMediaPorIdyPorNombre(Long idSocialMedia, String nickname) {
		
		List<Object[]> listaDeArreglos = getSession().createQuery(
				
				"from TeacherSocialMedia tsm join tsm.socialMedia sm " 
				+ "where sm.idSocialMedia = :idSocialMedia and tsm.nickname = :nickname")
				.setParameter("idSocialMedia", idSocialMedia)
				.setParameter("nickname", nickname).list();
		
		if (listaDeArreglos.size() > 0) {
			
			for (Object[] objectsi : listaDeArreglos) {
				
				for (Object objects2i : objectsi) {
					
					if (objects2i instanceof TeacherSocialMedia) {
						return (TeacherSocialMedia) objects2i;
					}
					
				}
				
				
			}
			
		}
		return null;
		
		
				
		
	}

}
