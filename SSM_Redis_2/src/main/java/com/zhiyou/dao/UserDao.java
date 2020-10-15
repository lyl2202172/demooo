package com.zhiyou.dao;

import java.util.List;

import com.zhiyou.model.User;

public interface UserDao {

	User selectName(String name,String password);
	void add(User user);
	void update(User user);
	void delete(int id);
	User selectById(int id);
	List<User> select();
}
