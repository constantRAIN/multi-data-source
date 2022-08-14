package com.multi.datasource.mapper.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zxliuyu
 * @date : 2022-08-14 23:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPO {
	private Long id;
	private String username;
}
