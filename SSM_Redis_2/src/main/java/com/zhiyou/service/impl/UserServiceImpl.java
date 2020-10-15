package com.zhiyou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zhiyou.dao.UserDao;
import com.zhiyou.model.User;
import com.zhiyou.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao dao;
	
	public void add(User user) {
		dao.add(user);
	}

	
	public void update(User user) {
		dao.update(user);
		
	}

//	public void qian(User user1,User user2,int qian){
//		Integer qi=Integer.valueOf(user1.getQian())-qian;
//		if(qi<0){
//			System.out.println("余额不足");
//		}else{
//			user1.setQian(qi.toString());
//			dao.update(user1);
//		}
//		Integer qi2=Integer.valueOf(user2.getQian())+qian;
//		user2.setQian(qi2.toString());
//		dao.update(user2);
//	}
	//用来标记需要清除缓存的方法，以及指定需要清除的缓存   allEntries代表是否清除缓存中的所有元素
	@CacheEvict(value="userSelect",allEntries=true)
	public void delete(int id) {
		dao.delete(id);
	}

	//指定下面这个方法需要使用缓存，使用缓存的名字叫userSelect
	@Cacheable(value="userSelect")
	public List<User> select() {
		return dao.select();
	}


	public User selectById(int id) {
		return dao.selectById(id);
	}


	public User selectName(String name, String password) {
		return dao.selectName(name, password);
	}
}
