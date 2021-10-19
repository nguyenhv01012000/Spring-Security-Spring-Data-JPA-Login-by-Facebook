package nguyen.security.config.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nguyen.security.models.ERole;
import nguyen.security.models.Provider;
import nguyen.security.models.Role;
import nguyen.security.models.User;
import nguyen.security.repository.RoleRepository;
import nguyen.security.repository.UserRepository;
 
@Service
public class UserService {

    @Autowired
	RoleRepository roleRepository;
 
    @Autowired
	UserRepository userRepository;
     
    public void processOAuthPostLogin(String username,String email) {
         
        if (!userRepository.existsByUsername(username)) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setProvider(Provider.FACEBOOK);

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
            newUser.setRoles(roles);

            //newUser.setEnabled(true);          
             
            userRepository.save(newUser);        
        }
         
    }  
}
