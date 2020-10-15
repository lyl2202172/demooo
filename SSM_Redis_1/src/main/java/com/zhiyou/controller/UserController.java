package com.zhiyou.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.zhiyou.model.User;
import com.zhiyou.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	@RequestMapping("show")
	public String show(HttpServletRequest req,HttpServletResponse rep){
//		PageHelper.startPage(1,5);
		req.setAttribute("list",userService.select());
		return "show";
	}
	@RequestMapping("add")
	public String add(User user,HttpServletRequest req,HttpServletResponse rep){
		userService.add(user);
		return "forward:show";
	}
	@RequestMapping("delete")
	public String delete(int id,HttpServletRequest req,HttpServletResponse rep){
		userService.delete(id);
		return "forward:show";
	}
	@RequestMapping("selectById")
	public String selectById(int id,HttpServletRequest req,HttpServletResponse rep){
		User user = userService.selectById(id);
		req.setAttribute("user", user);
		return "update";
	}
	@RequestMapping("update")
	public String update(User user,HttpServletRequest req,HttpServletResponse rep){
		userService.update(user);
		return "forward:show";
	}
}
