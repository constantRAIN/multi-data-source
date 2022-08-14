package com.multi.datasource.mapper;

import com.multi.datasource.dto.UserDTO;
import com.multi.datasource.mapper.po.UserPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

	@Select({
			"select",
			"* ",
			"from user"
	})
	List<UserDTO> listAllUsersWithReadDatabase();

	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert("insert into user(username) " +
			"values(#{username})")
	Integer insertWithReadDatabase(UserPO user);

	@Select({
			"select",
			"* ",
			"from user"
	})
	List<UserDTO> listAllUsersWithWriteDatabase();

	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	@Insert("insert into user(username) " +
			"values(#{username})")
	Integer insertWithWriteDatabase(UserPO user);
}
