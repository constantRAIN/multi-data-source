package com.multi.datasource.service;

import com.multi.datasource.config.ReadOnly;
import com.multi.datasource.dto.UserDTO;
import com.multi.datasource.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : zxliuyu
 * @date : 2022-08-14 22:52
 */
@Service
public class UserService {
	@Resource
	private UserMapper userMapper;

	@ReadOnly
	public boolean createUserWithReadDatabase(UserDTO user) {
		return userMapper.insertWithReadDatabase(user.convert()) > 0;
	}

	@ReadOnly
	public List<UserDTO> listAllUsersWithReadDatabase() {
		return userMapper.listAllUsersWithReadDatabase();
	}

	public boolean createUserWithWriteDatabase(UserDTO user) {
		return userMapper.insertWithWriteDatabase(user.convert()) > 0;
	}

	public List<UserDTO> listAllUsersWithWriteDatabase() {
		return userMapper.listAllUsersWithWriteDatabase();
	}
}
