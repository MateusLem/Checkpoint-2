package br.com.fiap.dao;

import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import br.com.fiap.model.Profile;

@Named
@ViewScoped
public class ProfileDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public ProfileDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional
	public void saveProfile(Profile profile) {
		entityManager.merge(profile);
	}
	
	public List<Profile> findAllProfiles(){
		@SuppressWarnings("unchecked")
		TypedQuery<Profile> query = (TypedQuery<Profile>) entityManager.createQuery(
				"SELECT e FROM Profile e");
		return query.getResultList();
	}

	@Transactional
	public void deleteProfile(Profile profile) {
		entityManager.remove(profile);
	}

	public Profile searchById(Long id) {
		return entityManager.find(Profile.class, id);
	}
}
