package com.project.blog.controllers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.blog.entity.Category;
import com.project.blog.entity.Daily;
import com.project.blog.entity.ImagePost;
import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.repo.CategoryRepo;
import com.project.blog.repo.DailyRepo;
import com.project.blog.repo.ImagePostRepo;
import com.project.blog.repo.UserRepo;
import com.project.blog.repo.confirmationTokenRepository;
import com.project.blog.repo.postRepo;



@Controller
public class AdminController {



	@Autowired
	UserRepo userRepo;
	
	@Autowired
	postRepo postRepo;
	
	@Autowired
	ImagePostRepo imagePostRepo;
	
	@Autowired
	CategoryRepo categortRepo;
	
	@Autowired
	DailyRepo dailyRepo;
	
	@Autowired
	confirmationTokenRepository confirmationTokenRepo;
	
	
	
	
	
	
	@GetMapping("/profile")
	public String profile(Model model,Principal principal) {
		String username = principal.getName();
		
		model.addAttribute("users",userRepo.findByUsername(username));
		return "admin/profile";
	}
	
	@PostMapping("/profile")
	public String profileupdate(Model model,Principal principal,User user) {
		String username = principal.getName();
		User usernew = userRepo.findByUsername(username);
		String fname = user.getFirstName();
		String lname = user.getLastName();
		String email = user.getEmail();
		String uname = user.getUsername();
		usernew.setFirstName(fname);
		usernew.setLastName(lname);
		usernew.setEmail(email);
		usernew.setUsername(uname);
		userRepo.save(usernew);
		model.addAttribute("users",userRepo.findByUsername(username));
		return "admin/profile";
	}
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
	
	@GetMapping("/posts")
	public String posts(Model model,Category category) {
		model.addAttribute("category",categortRepo.findAll());
		return "admin/post";
	}
	
	@PostMapping("/text")
	public String postText(Post post,Model model,Principal principal) {
		String username = principal.getName();
		post.setUsername(username);
		 Date date= new Date();
         //getTime() returns current time in milliseconds
		 long time = date.getTime();
	         //Passed the milliseconds to constructor of Timestamp class 
		 Timestamp ts = new Timestamp(time);
		 
		post.setTime(ts);
		postRepo.save(post);
		model.addAttribute("user", userRepo.findAll());
		return "/index";
	}
	
	@GetMapping("/own")
	public String ownPost(Model model,Principal principal) {
		String username = principal.getName();
		
		
		model.addAttribute("posts",postRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("iposts",imagePostRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pimageid")));
		return "admin/ownPost";
	}
	
	@GetMapping("/allPost")
	public String allPost(Model model) {
		
		model.addAttribute("posts",postRepo.findAll(Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("iposts",imagePostRepo.findAll(Sort.by(Sort.Direction.DESC, "pimageid")));
		return "admin/allPost";
	}
	
	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "E:\\software\\sts-4.5.0.RELEASE\\Project\\Blog\\src\\main\\resources\\static\\Upload_Img\\";
    private static String UPLOADED_PATH = "Upload_Img\\";
	
	
	
	@PostMapping("/image")
	public String fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,Principal principal,ImagePost imagePost,Model model) {

		if (file.isEmpty()) {
			return "error";
		}

		try {
			// read and write the file to the selected location-
			byte[] bytes = file.getBytes();
			String filename = file.getOriginalFilename();
			String ext = filename.substring(filename.lastIndexOf(".") + 1);
			System.out.println(ext);
				Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
				String pathnew = UPLOADED_PATH + file.getOriginalFilename().toString();
				
				Files.write(path, bytes);
				
				String username = principal.getName();
				imagePost.setUsername(username);
				 Date date= new Date();
		         //getTime() returns current time in milliseconds
				 long time = date.getTime();
			         //Passed the milliseconds to constructor of Timestamp class 
				 Timestamp ts = new Timestamp(time);
				 
				imagePost.setItime(ts);
				imagePost.setIpath(pathnew);
				imagePostRepo.save(imagePost);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		model.addAttribute("user", userRepo.findAll());
		return "/index";
	}
	
	@GetMapping("/textPosts")
	public String textPosts(Model model,Principal principal) {
		String username = principal.getName();
		model.addAttribute("texts", postRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pid")));
		
		return "admin/textPosts";
	}
	
	@GetMapping("/ImagePosts")
	public String ImagePosts(Model model,Principal principal) {
		String username = principal.getName();
		model.addAttribute("images", imagePostRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pimageid")));
		return "admin/imagePosts";
	}
	
	@GetMapping("/textAllPosts")
	public String textAllPosts(Model model,Principal principal) {
		String username = principal.getName();
		model.addAttribute("texts", postRepo.findAll(Sort.by(Sort.Direction.DESC, "pid")));
		
		return "admin/textAllPosts";
	}
	
	@GetMapping("/ImageAllPosts")
	public String ImageAllPosts(Model model,Principal principal) {
		String username = principal.getName();
		model.addAttribute("images", imagePostRepo.findAll(Sort.by(Sort.Direction.DESC, "pimageid")));
		return "admin/imageAllPosts";
	}
	
	
	  @PostMapping("/dailyPost") 
	  public String dailyPost(Model model,Daily daily) { 
		  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		    Date date = new Date();  
		  daily.setDate(date);
		  dailyRepo.save(daily);
		  model.addAttribute("success","Daily Post Posted!!!");
		  return "admin/post";
	  }
	 
}
