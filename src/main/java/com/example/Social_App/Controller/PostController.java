package com.example.Social_App.Controller;


import com.example.Social_App.DTO.PostDTO;
import com.example.Social_App.Mapper.PostMapper;
import com.example.Social_App.Model.Post;
import com.example.Social_App.Service.PostService;
import com.example.Social_App.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class PostController {


    private final UserService userService;


    private final PostService postService;

    public PostController(final UserService userService, final PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/Posts")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts().stream().map(post -> PostMapper.MAPPER.postToDto(post)).collect(Collectors.toList());
    }

    @GetMapping("/Posts/{id-post}")
    public ResponseEntity<PostDTO> findById(@PathVariable(value = "id-post") final Long id) {
        return new ResponseEntity<>(PostMapper.MAPPER.postToDto(postService.findById(id)), HttpStatus.OK);
    }

    @PostMapping("/Posts/{id-user}")
    public ResponseEntity<PostDTO> AddPost(@RequestBody final PostDTO newpost, @PathVariable(value = "id-user") final Long id) {
        final Post post = PostMapper.MAPPER.dtoToPost(newpost);
        return new ResponseEntity<>(PostMapper.MAPPER.postToDto(postService.createPost(post, id)), HttpStatus.CREATED);
    }

    @PutMapping("/Posts/{id-post}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody final PostDTO newpost, @PathVariable(value = "id-post") final Long id) {
        final Post post = PostMapper.MAPPER.dtoToPost(newpost);

        return new ResponseEntity<>(PostMapper.MAPPER.postToDto(postService.updatePost(id, post)), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/Posts/{id-post}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "id-post") final Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/Posts/User/{id-user}")
    public List<PostDTO> findAllPostsPerUser(@PathVariable(value = "id-user") final Long id) {
        return postService.allPostofUser(id).stream().map(post -> PostMapper.MAPPER.postToDto(post)).collect(Collectors.toList());
    }

}
