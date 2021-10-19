package nguyen.security.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import nguyen.security.repository.RoleRepository;
import nguyen.security.models.Role;

//CRUD API
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/roles")
	public ResponseEntity<Set<Role>> getAllRoles() {
		try {
			Set<Role> roles = new HashSet<>(); 
            roleRepository.findAll().forEach(roles::add);

			if (roles.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(roles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/roles/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable("id") long id) {
		Optional<Role> role = roleRepository.findById(id);

		if (role.isPresent()) {
			return new ResponseEntity<>(role.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

    @PostMapping("/roles")
	public ResponseEntity<?> createRole(@RequestBody Role role) {
		if (roleRepository.existsByName(role.getName()))
			return new ResponseEntity<>(String.format("Name '%s' is already used",role.getName()),HttpStatus.BAD_REQUEST);
		try {
			Role _role = roleRepository
					.save(new Role(role.getName()));
			return new ResponseEntity<>(_role, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/roles/{id}")
	public ResponseEntity<?> updateRole(@PathVariable("id") long id, @RequestBody Role role) {
		if (roleRepository.existsByName(role.getName()))
			return new ResponseEntity<>(String.format("Name '%s' is already used",role.getName()),HttpStatus.BAD_REQUEST);
			
		Optional<Role> roleData = roleRepository.findById(id);

		if (roleData.isPresent()) {
			Role _role = roleData.get();
			_role.setName(role.getName());
			return new ResponseEntity<>(roleRepository.save(_role), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


    @DeleteMapping("/roles/{id}")
	public ResponseEntity<HttpStatus> deleteRole(@PathVariable("id") long id) {

		Optional<Role> role = roleRepository.findById(id);
		if (!role.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	
		try {
			roleRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
