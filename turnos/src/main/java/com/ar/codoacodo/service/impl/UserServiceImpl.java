package com.ar.codoacodo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ar.codoacodo.domain.User;
import com.ar.codoacodo.repository.UserRepository;
import com.ar.codoacodo.service.UserService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

	//D.I
		private final UserRepository repository;
		
		/*
		public UserServiceImpl(UserRepository repository) {
			this.repository = repository;
		}*/
		
		@Override
		public void crearUser(User user) {
			//aca va la logica de insert!
			this.repository.save(user);
			//insert into tabla (c1,c2..cn) values(v1,v2....vn)
		}

		@Override
		public User buscarUser(Long id) {
			//select * from tabla where id = id
			
			//mapper! mapstruct!
			return this.repository.findById(id).get();
		}

		@Override
		public List<User> buscarTodos() {
			return this.repository.findAll();
		}

		@Override
		public User buscarUserPorUsername(String username) {
			//select * from tabla where username = 'username'
			return this.repository.findByUsername(username);
		}
		
		@Override
		public void eliminarUser(Long id) {
			this.repository.deleteById(id);
		}

		@Override
		public void actualizar(User user) {
			this.repository.save(user);		
		}
	}
