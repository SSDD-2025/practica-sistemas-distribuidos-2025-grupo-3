package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.PostService;
import com.example.demo.model.Post;

@Controller
@RequestMapping("/posts")
public class PostController {

	@Autowired
	PostService posts;


    @GetMapping("/")
	public String showPosts(Model model) {
		model.addAttribute("posts", posts.findAll());
		return "postIndex";
	}

	@PostMapping("/newPost")
	public String newPost(@RequestParam String title, @RequestParam String postContent) {      
		Post p = new Post(title, postContent);
		posts.save(p);
		return "savedPost";
	}
	
}
