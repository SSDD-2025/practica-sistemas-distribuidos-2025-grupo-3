package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
public class PostController {

    @GetMapping("/posts")
	public String showPosts() {
		return "postIndex";
	}

}
