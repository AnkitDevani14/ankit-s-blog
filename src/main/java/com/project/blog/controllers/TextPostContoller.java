package com.project.blog.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.blog.entity.ImagePost;
import com.project.blog.entity.Post;
import com.project.blog.repo.ImagePostRepo;
import com.project.blog.repo.postRepo;

@Controller
public class TextPostContoller {
	
	@Autowired
	postRepo postRepo;
	
	@Autowired
	ImagePostRepo imagePostRepo;
	
	@GetMapping("/text{id}")
	public String TextRead(Model model,@PathVariable Long id) {
		model.addAttribute("textPost", postRepo.getOne(id));
		return "text/textPost";
	}
	
	@GetMapping("/textDelete{id}")
	public String TextDelete(Model model,@PathVariable Long id,Principal principal) {
		postRepo.deleteById(id);
		model.addAttribute("textPost", postRepo.getOne(id));
		model.addAttribute("errorDelte","Post Deleted Successfully!!");
		String username = principal.getName();
		
		
		model.addAttribute("posts",postRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("iposts",imagePostRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pimageid")));
		return "admin/ownPost";
	}
	
	@GetMapping("/image{id}")
	public String ImageRead(Model model,@PathVariable Long id) {
		model.addAttribute("imagePost", imagePostRepo.getOne(id));
		return "text/imagePost";
	}
	
	@GetMapping("/textEdit{id}")
	public String TextEdit(Model model,@PathVariable Long id) {
		
		model.addAttribute("textPost", postRepo.getOne(id));
		
		return "text/textEdit";
	}
	
	@PostMapping("/textEdit{id}")
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
				
				
				model.addAttribute("posts",postRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pid")));
				model.addAttribute("iposts",imagePostRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pimageid")));
				return "admin/ownPost";
		 }
		 else
		 {
			 model.addAttribute("ErrorPost", "Error in updating Post!!!");
			 String username = principal.getName();
				
				
				model.addAttribute("posts",postRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pid")));
				model.addAttribute("iposts",imagePostRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pimageid")));
				return "admin/ownPost";
		 }
		 
		 
		 
	}
	
	@GetMapping("/imageDelete{id}")
	public String imageDelete(Model model,@PathVariable Long id,Principal principal) {
		imagePostRepo.deleteById(id);
		
		model.addAttribute("errorDelte","Post Deleted Successfully!!");
		String username = principal.getName();
		
		
		model.addAttribute("posts",postRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pid")));
		model.addAttribute("iposts",imagePostRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pimageid")));
		return "admin/ownPost";
	}
	
	@GetMapping("/imageEdit{id}")
	public String imageEdit(Model model,@PathVariable Long id) {
		
		model.addAttribute("imagePost", imagePostRepo.getOne(id));
		
		return "text/imgaeEdit";
	}

	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "E:\\software\\sts-4.5.0.RELEASE\\Project\\Blog\\src\\main\\resources\\static\\Upload_Img\\";
    private static String UPLOADED_PATH = "Upload_Img\\";
    
    
	@PostMapping("/imageEdit{id}")
	public String imageEditPost(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,ImagePost imagepost,Model model,@PathVariable Long id,Principal principal) {
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
				
				
				model.addAttribute("posts",postRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pid")));
				model.addAttribute("iposts",imagePostRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pimageid")));
				return "admin/ownPost";
		 }
		 else
		 {
			 model.addAttribute("ErrorPost", "Error in updating Post!!!");
			 String username = principal.getName();
				
				
				model.addAttribute("posts",postRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pid")));
				model.addAttribute("iposts",imagePostRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pimageid")));
				return "admin/ownPost";
		 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		 String username = principal.getName();
			
			
			model.addAttribute("posts",postRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pid")));
			model.addAttribute("iposts",imagePostRepo.findByUsername(username,Sort.by(Sort.Direction.DESC, "pimageid")));
			return "admin/ownPost";
		 
	}
}
