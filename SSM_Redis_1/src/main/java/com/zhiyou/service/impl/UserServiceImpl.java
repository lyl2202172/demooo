package com.zhiyou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
//			System.out.println("Óà¶î²»×ã");
//		}else{
//			user1.setQian(qi.toString());
//			dao.update(user1);
//		}
//		Integer qi2=Integer.valueOf(user2.getQian())+qian;
//		user2.setQian(qi2.toString());
//		dao.update(user2);
//	}
	public void delete(int id) {
		dao.delete(id);
	}

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
