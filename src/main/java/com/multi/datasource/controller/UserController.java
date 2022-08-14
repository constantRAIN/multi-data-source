package com.multi.datasource.controller;

import com.multi.datasource.dto.UserDTO;
import com.multi.datasource.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : zxliuyu
 * @date : 2022-08-14 22:53
 */
@RestController
@RequestMapping("user")
public class UserController {
	@Resource
	private UserService userService;

	@PostMapping("/read/insert")
	public boolean createUserWithReadDatabase(@RequestBody UserDTO user) {
		return userService.createUserWithReadDatabase(user);
	}

	@GetMapping("/read/listAllUser")
	public List<UserDTO> listAllUsersWithReadDatabase() {
		return userService.listAllUsersWithReadDatabase();
	}

	@PostMapping("/write/insert")
	public boolean createUserWithWriteDatabase(@RequestBody UserDTO user) {
		return userService.createUserWithWriteDatabase(user);
	}

	@GetMapping("/write/listAllUser")
	public List<UserDTO> listAllUsersWithWriteDatabase() {
		return userService.listAllUsersWithWriteDatabase();
	}
}
