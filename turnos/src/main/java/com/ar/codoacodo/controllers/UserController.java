package com.ar.codoacodo.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ar.codoacodo.domain.Role;
import com.ar.codoacodo.domain.User;
import com.ar.codoacodo.dto.UserDTO;
import com.ar.codoacodo.dto.UserRequestDTO;
import com.ar.codoacodo.dto.UserRequestPutDTO;
import com.ar.codoacodo.dto.UserResponseDTO;
import com.ar.codoacodo.service.UserService;
import lombok.RequiredArgsConstructor;

//http://localhost:8081/user

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	//necesito el service
	private final UserService userService;
	
	//GET
	@GetMapping("/{id}")	
	public ResponseEntity<UserDTO> m1(
			@PathVariable("id") Long id
			) {
		
		User user = this.userService.buscarUser(id);
		
		UserDTO dto = UserDTO.builder()
			.id(user.getId())
			.username(user.getUsername())
			.roles(user.getRoles()
					.stream()
					.map(x -> x.getRol())
					.collect(Collectors.toSet())
			).build();
			
		
		//http status code=200
		return ResponseEntity.ok(dto);
	}
	
	//GET
	@GetMapping()	
	public ResponseEntity<List<User>> findAll() {
		
		List<User> user = this.userService.buscarTodos();
		
		//http status code=200
		return ResponseEntity.ok(user);
	}
	
	@PostMapping()
	@PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<UserResponseDTO> createUser(
			@RequestBody UserRequestDTO request
		)  {
		
		//verifico si existe
		User user = this.userService.buscarUserPorUsername(request.getUsername());
		if(user != null) {
			UserResponseDTO response = UserResponseDTO.builder()
				.username(user.getUsername())
				.build();
			
			return ResponseEntity.ok(response);
		}
		
		//sino lo crea
		//validacion!!!
		//["1","2","3"]
		Set<Role> rolesDelUsuario = request.getRoles()
			.stream()
			.map(r -> Role.builder().id(Long.parseLong(r)).build())
			.collect(Collectors.toSet());
		
		User newUser = User.builder()
				.username(request.getUsername())
				.password(new BCryptPasswordEncoder().encode(request.getPassword()))
				.roles(rolesDelUsuario)
				.build();
		
		this.userService.crearUser(newUser);
		
		UserResponseDTO response = UserResponseDTO.builder()
			.username(newUser.getUsername())
			.build();
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> actualizar(
			@PathVariable("id") Long id			
		) {
		
		this.userService.eliminarUser(id);
		
		return ResponseEntity.ok().build();
	}
	
	/*Idempotentes
	 * user/1
	{
		alias: 'nuevoalias'
		id: 2
	}
	 */
	@PutMapping("/{id}")
	public ResponseEntity<UserRequestPutDTO> actualizarPorPut(
			@PathVariable(name="id", required = true) Long id,
			@Validated @RequestBody UserRequestPutDTO request 
		) {
		
		User user = this.userService.buscarUser(id);
		if(!user.getId().equals(request.getId())) {
			return ResponseEntity.badRequest().build();
		}
		
		user.setPassword(request.getPassword());
		//otros atributos en base al request
		
		this.userService.actualizar(user);
		
		return ResponseEntity.ok().build();
	}
	
	//path!
}


	

