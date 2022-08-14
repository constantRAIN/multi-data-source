package com.multi.datasource.dto;

import com.multi.datasource.mapper.po.UserPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zxliuyu
 * @date : 2022-08-14 23:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	private Long id;
	private String username;

	public UserPO convert() {
		return UserPO.builder()
				.id(this.getId())
				.username(this.getUsername())
				.build();
	}

}
