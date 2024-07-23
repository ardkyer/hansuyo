package com.ardkyer.rion.controller;

import com.ardkyer.rion.entity.Video;
import com.ardkyer.rion.entity.User;
import com.ardkyer.rion.service.VideoService;
import com.ardkyer.rion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    private static final String UPLOADED_FOLDER = "C:\\Users\\k0207\\datas\\";

    @GetMapping
    public String listVideos(Model model) {
        List<Video> videos = videoService.getAllVideos();
        model.addAttribute("videos", videos);
        return "videos";  // This should match your Thymeleaf template name
    }

    @GetMapping("/{id}")
    public String watchVideo(@PathVariable Long id, Model model) {
        Optional<Video> videoOptional = videoService.getVideoById(id);
        if (videoOptional.isPresent()) {
            model.addAttribute("video", videoOptional.get());
            return "watchVideo";
        } else {
            return "redirect:/videos";
        }
    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    private Resource loadAsResource(String filename) {
        try {
            Path file = Paths.get(UPLOADED_FOLDER).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "uploadForm";  // This should match your upload form template name
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("video") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload");
            return "redirect:/videos/upload";
        }

        try {
            // Generate a unique filename
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Save the file
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);

            // Get the current authenticated user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.findByUsername(auth.getName());

            // Create and save video metadata
            Video video = new Video();
            video.setTitle(title);
            video.setDescription(description);
            video.setVideoUrl(filename);
            video.setUser(currentUser);
            videoService.uploadVideo(video);

            redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
        }

        return "redirect:/videos";
    }
}