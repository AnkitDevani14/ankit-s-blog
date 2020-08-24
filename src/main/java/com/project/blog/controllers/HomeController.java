package com.project.blog.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.blog.entity.ImagePost;
import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.repo.CategoryRepo;
import com.project.blog.repo.DailyRepo;
import com.project.blog.repo.ImagePostRepo;
import com.project.blog.repo.UserRepo;
import com.project.blog.repo.postRepo;

@Controller
public class HomeController {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	DailyRepo dailyRepo;
	
	@Autowired
	postRepo postRepo;
	
	@Autowired
	ImagePostRepo imagePostRepo;
	
	@GetMapping("/")
	public String Home(Model model,Principal principal) {
		
		model.addAttribute("category",categoryRepo.findAll());
		model.addAttribute("daily",dailyRepo.findAll(Sort.by(Sort.Direction.DESC, "did")));
		model.addAttribute("post",postRepo.findByCategory("Technology", Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("post1",postRepo.findByCategory("Education", Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("admin",postRepo.findByUsername("admin", Sort.by(Sort.Direction.ASC, "pid")));
		
		return "User/index"; 
	}
	
	@GetMapping("/admin")
	public String HomeAdmin(Model model) {
		
		model.addAttribute("user", userRepo.findAll());
		return "index"; 
	}
	@GetMapping("/HomeAdmin{id}")
	public String HomeAdmin(Model model,@PathVariable Long id) {
		model.addAttribute("textPost",dailyRepo.getOne(id));
		model.addAttribute("category",categoryRepo.findAll());
		return "User/AdminPost";
	}
	
	@GetMapping("/Edu{id}")
	public String Edu(Model model,@PathVariable Long id) {
		model.addAttribute("textPost", postRepo.getOne(id));
		model.addAttribute("category",categoryRepo.findAll());
		return "User/EduPost";
	}
	
	
	@GetMapping("/Tech{id}")
	public String Tech(Model model,@PathVariable Long id) {
		model.addAttribute("textPost", postRepo.getOne(id));
		model.addAttribute("category",categoryRepo.findAll());
		return "User/TechPost";
	}
	
	@GetMapping("/Category{id}")
	public String Category(Model model,@PathVariable String id) {
		model.addAttribute("categoryText", postRepo.findByCategory(id,Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("categoryimage", imagePostRepo.findByicategory(id,Sort.by(Sort.Direction.DESC, "pimageid")));
		model.addAttribute("category",categoryRepo.findAll());
		model.addAttribute("categoryName",id);
		return "User/CategoryPost";
	}
	
	@GetMapping("/CategoryImage{id}")
	public String CategoryImage(Model model,@PathVariable String id) {
		
		model.addAttribute("categoryText", imagePostRepo.findByicategory(id,Sort.by(Sort.Direction.DESC, "pimageid")));
		model.addAttribute("category",categoryRepo.findAll());
		model.addAttribute("categoryName",id);
		return "User/CategoryImage";
	}

	@GetMapping("/newText{id}")
	public String New1(Model model,@PathVariable Long id) {
		model.addAttribute("textPost", postRepo.getOne(id));
		model.addAttribute("category",categoryRepo.findAll());
		return "User/textPost";
	}
	
	@GetMapping("/newImage{id}")
	public String New2(Model model,@PathVariable Long id) {
		model.addAttribute("imagePost", imagePostRepo.getOne(id));
		model.addAttribute("category",categoryRepo.findAll());
		return "User/imagePost";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/register")
	public String register1(Model model) {
		model.addAttribute("user", new User());
		return "register"; 
	}
	
	private static String UPLOADED_FOLDER = "E:\\software\\sts-4.5.0.RELEASE\\Project\\Blog\\src\\main\\resources\\static\\Upload_Img\\";
    private static String UPLOADED_PATH = "Upload_Img\\";
	
	@PostMapping("/register")
	public String registerUser(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,@Valid User user,Model model,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "register";
		}
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
				 user.setActive(0);
				 user.setRoles("ROLE_USER");
				 user.setProfilePic(pathnew);
				 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				 user.setPassword(encoder.encode(user.getPassword()));
				 userRepo.save(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
				 return "login";
	}
	
	@GetMapping("/UserAddPost")
	public String USerAddPost(Model model) {
		model.addAttribute("category",categoryRepo.findAll());
		return "User/addPost";
	}
	
	@PostMapping("/Usertext")
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
		model.addAttribute("success","Blog Posted successfully!!");
		return "User/addPost";
	}
	
	@PostMapping("/Userimage")
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

		model.addAttribute("success","Blog Posted successfully!!");
		return "User/addPost";
	}
	@GetMapping("/UserViewPost")
	public String USerViewPost(Model model,Principal principal) {
		model.addAttribute("category",categoryRepo.findAll());
		String username = principal.getName();
		model.addAttribute("textPost",postRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("imagePost",imagePostRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pimageid")));
		return "User/ViewPost";
	}
	
	@GetMapping("/UserTextDelete{id}")
	public String UserTextDelete(Model model,@PathVariable Long id,Principal principal) {
		postRepo.deleteById(id);
		model.addAttribute("textPost", postRepo.getOne(id));
		model.addAttribute("errorDelte","Post Deleted Successfully!!");
		
		
		
		model.addAttribute("category",categoryRepo.findAll());
		String username = principal.getName();
		model.addAttribute("textPost",postRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("imagePost",imagePostRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pimageid")));
		return "User/ViewPost";
	}
	
	@GetMapping("/UserimageDelete{id}")
	public String imageDelete(Model model,@PathVariable Long id,Principal principal) {
		imagePostRepo.deleteById(id);
		
		model.addAttribute("errorDelte","Post Deleted Successfully!!");
		model.addAttribute("category",categoryRepo.findAll());
		String username = principal.getName();
		model.addAttribute("textPost",postRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("imagePost",imagePostRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pimageid")));
		return "User/ViewPost";
	}
	
	@GetMapping("/UserTextEdit{id}")
	public String TextEdit(Model model,@PathVariable Long id) {
		
		model.addAttribute("textPost", postRepo.getOne(id));
		
		return "User/textEdit";
	}
	
	@PostMapping("/UsertextEdit{id}")
	public String TextEditPost(Post post,Model model,@PathVariable Long id,Principal principal) {
		Post post1 = postRepo.getOne(id);
		String cate = post.getCategory();
		String title = post.getTitle();
		String content = post.getContent();
		
		 Date date= new Date();
         //getTime() returns current time in milliseconds
		 long time = date.getTime();
	         //Passed the milliseconds to constructor of Timestamp class 
		 Timestamp ts = new Timestamp(time);
		 
		 post1.setCategory(cate);
		 post1.setContent(content);
		 post1.setTime(ts);
		 post1.setTitle(title);
		 if(postRepo.save(post1) != null)
		 {
			 model.addAttribute("success", "Post Edited successfully!!!");
			 String username = principal.getName();
				
				
			 model.addAttribute("textPost",postRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pid")));
				model.addAttribute("imagePost",imagePostRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pimageid")));
				return "User/ViewPost";
		 }
		 else
		 {
			 model.addAttribute("ErrorPost", "Error in updating Post!!!");
			 String username = principal.getName();
				
				
			 model.addAttribute("textPost",postRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pid")));
				model.addAttribute("imagePost",imagePostRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pimageid")));
				return "User/ViewPost";
		 }
		 
		 
		 
	}
	
	@GetMapping("/UserimageEdit{id}")
	public String imageEdit(Model model,@PathVariable Long id) {
		
		model.addAttribute("imagePost", imagePostRepo.getOne(id));
		
		return "User/imageEdit";
	}
	

    
	@PostMapping("/UserimageEdit{id}")
	public String UserimageEditPost(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,ImagePost imagepost,Model model,@PathVariable Long id,Principal principal) {
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
		
		ImagePost ipost = imagePostRepo.getOne(id);
		String cate = imagepost.getIcategory();
		String title = imagepost.getItitle();
		String content = imagepost.getIcontent();
		
		 Date date= new Date();
         //getTime() returns current time in milliseconds
		 long time = date.getTime();
	         //Passed the milliseconds to constructor of Timestamp class 
		 Timestamp ts = new Timestamp(time);
		 
		 ipost.setIcategory(cate);
		ipost.setIcontent(content);
		ipost.setItime(ts);
		 ipost.setItitle(title);
		 ipost.setIpath(pathnew);
		 if(imagePostRepo.save(ipost) != null)
		 {
			 model.addAttribute("success", "Post Edited successfully!!!");
			 String username = principal.getName();
				
				
			 model.addAttribute("textPost",postRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pid")));
				model.addAttribute("imagePost",imagePostRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pimageid")));
				return "User/ViewPost";
		 }
		 else
		 {
			 model.addAttribute("ErrorPost", "Error in updating Post!!!");
			 String username = principal.getName();
				
				
			 model.addAttribute("textPost",postRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pid")));
				model.addAttribute("imagePost",imagePostRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pimageid")));
				return "User/ViewPost";
		 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		 String username = principal.getName();
			
			
		 model.addAttribute("textPost",postRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pid")));
			model.addAttribute("imagePost",imagePostRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pimageid")));
			return "User/ViewPost";
	}
	
	@GetMapping("/UsertextPosts")
	public String UserTexts(Model model,Principal principal) {
		String username = principal.getName();
		model.addAttribute("category",categoryRepo.findAll());
		 model.addAttribute("textPost",postRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pid")));
		return "User/UserPost";
	}
	
	@GetMapping("/UserImagePosts")
	public String UserImages(Model model,Principal principal) {
		String username = principal.getName();
		model.addAttribute("category",categoryRepo.findAll());
		model.addAttribute("imagePost",imagePostRepo.findByUsername(username, Sort.by(Sort.Direction.DESC, "pimageid")));
		return "User/UserImage";
	}
	
	@GetMapping("/UserChangePass")
	public String UserChangePass() {
		return "User/UserChangePass";
	}
	
	@PostMapping("/UserChangePass")
	public String UserChangePass1(Principal principal,Model model,User user) {
		String username = principal.getName();
		User usernew = userRepo.findByUsername(username);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usernew.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(usernew);
		model.addAttribute("success","Password changed!!!");
		return "User/UserChangePass";
	}
}
