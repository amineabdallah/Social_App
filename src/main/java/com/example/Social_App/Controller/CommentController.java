package com.example.Social_App.Controller;

import com.example.Social_App.DTO.CommentDTO;
import com.example.Social_App.Mapper.CommentMapper;
import com.example.Social_App.Model.Comment;
import com.example.Social_App.Service.CommentService;
import com.example.Social_App.Service.PostService;
import com.example.Social_App.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {


    private final UserService userService;

    private final CommentService commentService;

    private final PostService postService;

    public CommentController(UserService userService, CommentService commentService, PostService postService) {
        this.userService = userService;
        this.commentService = commentService;
        this.postService = postService;
    }


    @GetMapping("/Comments")
    public List<CommentDTO> getAllComments(){
        return commentService.getAllCommets().stream().map(comment -> CommentMapper.MAPPER.commentToDto(comment)).collect(Collectors.toList());
    }

    @GetMapping("/Comment/{id-comment}")
    public ResponseEntity<CommentDTO> findById(@PathVariable(value = "id-comment") Long id){
        return new ResponseEntity<> (CommentMapper.MAPPER.commentToDto(commentService.findById(id)) , HttpStatus.OK);
    }

    @PostMapping("/Comment/{id-post}/{id-user}")
    public ResponseEntity<CommentDTO> AddComment(@RequestBody CommentDTO newComment, @PathVariable(value = "id-user") Long id_user,
                                           @PathVariable(value = "id-post") Long id_post){
        Comment comment = CommentMapper.MAPPER.dtoTocomment(newComment);
        return new ResponseEntity<> (CommentMapper.MAPPER.commentToDto(commentService.createComment(comment,id_user,id_post)) ,HttpStatus.CREATED);
    }

    @PutMapping("/Comment/{id-comment}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO newComment,@PathVariable(value = "id-comment") Long id){
        Comment comment = CommentMapper.MAPPER.dtoTocomment(newComment);

        return new ResponseEntity<> (CommentMapper.MAPPER.commentToDto(commentService.updateComment(id, comment)) ,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/Comment/{id-comment}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "id-comment") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/Comment/Post/{id-post}")
    public List<CommentDTO> findAllCommentsPerPost(@PathVariable(value = "id-post") Long id){
        return  commentService.allCommentOfAPost(id).stream().map(comment -> CommentMapper.MAPPER.commentToDto(comment)).collect(Collectors.toList());
    }

}
