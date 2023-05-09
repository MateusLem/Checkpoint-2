package br.com.fiap.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.dao.ProfileDAO;
import br.com.fiap.model.Profile;
import br.com.fiap.repository.ProfileRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/profiles")
@Api(value = "Progamer API")
public class ProfileController {

	@Inject
	private ProfileDAO profileDao;
	
	@Inject
	private ProfileRepository repository;

	@GetMapping()
	@ApiOperation("Get all profiles")
	public ResponseEntity<List<Profile>> index() {
		return ResponseEntity.ok(profileDao.findAllProfiles());
	}

	@GetMapping("{id}")
	@ApiOperation("Get profile by id")
	public ResponseEntity<Profile> show(@PathVariable("id") long id) {

		Profile profile = profileDao.searchById(id);

		if (profile == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(profile);
	}

	@PostMapping()
	@ApiOperation("Create new profile")
	public ResponseEntity<String> create(@RequestBody Profile profileRequest) {
		Profile profile = new Profile();
		try {
			if (profileRequest.getName() == null || profileRequest.getProfile() == null || profileRequest.getEmail() == null
					|| profileRequest.getPassword() == null) {

				System.out.println("===== ERROR =====");
				System.out.println("Insufficient parameters");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

			}

			profile.setName(profileRequest.getName());
			profile.setProfile(profileRequest.getProfile());
			profile.setEmail(profileRequest.getEmail());
			profile.setPassword(profileRequest.getPassword());

			profileDao.saveProfile(profile);

			return ResponseEntity.status(HttpStatus.CREATED).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("{id}")
	@ApiOperation("Profile total update")
	public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Profile profileRequest) {

		try {
			Profile profile = profileDao.searchById(id);

			if (profile == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			profile.setName(profileRequest.getName());
			profile.setProfile(profileRequest.getProfile());
			profile.setEmail(profileRequest.getEmail());
			profile.setPassword(profileRequest.getPassword());

			profileDao.saveProfile(profile);

			return ResponseEntity.ok("Profile successfully updated!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping("{id}")
	@ApiOperation("Profile parcial update")
	public ResponseEntity<String> patch(@PathVariable("id") long id, @RequestBody Profile profileRequest) {

		try {
			Profile profile = profileDao.searchById(id);

			if (profile == null)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

			if (profileRequest.getName() != null)
				profile.setName(profileRequest.getName());
			if (profileRequest.getProfile() != null)
				profile.setProfile(profileRequest.getProfile());
			if (profileRequest.getEmail() != null)
				profile.setEmail(profileRequest.getEmail());
			if (profileRequest.getPassword() != null)
				profile.setPassword(profileRequest.getPassword());

			profileDao.saveProfile(profile);

			return ResponseEntity.ok("Profile successfully updated!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("{id}")
	@ApiOperation("Delete profile")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		try {
			Profile profile = profileDao.searchById(id);
			if (profile == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			profileDao.deleteProfile(profile);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("delete/{id}")
	@ApiOperation("Deleting profile with JPA")
	public ResponseEntity<String> delete2(@PathVariable("id") long id){
		repository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
