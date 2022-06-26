package com.example.Social_App.Controller;

import com.example.Social_App.DTO.PostDTO;
import com.example.Social_App.DTO.ReactDTO;
import com.example.Social_App.Mapper.PostMapper;
import com.example.Social_App.Mapper.ReactMapper;
import com.example.Social_App.Model.React;
import com.example.Social_App.Service.CommentService;
import com.example.Social_App.Service.PostService;
import com.example.Social_App.Service.ReactService;
import com.example.Social_App.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReactController {


    private final UserService userService;

    private final CommentService commentService;

    private final PostService postService;

    private final ReactService reactService;

    public ReactController(final UserService userService, final CommentService commentService,
                           final PostService postService, final ReactService reactService) {
        this.userService = userService;
        this.commentService = commentService;
        this.postService = postService;
        this.reactService = reactService;
    }


    @GetMapping("/Reacts")
    public List<ReactDTO> getAllReacts() {
        return reactService.getAllReacts().stream().map(react -> ReactMapper.MAPPER.reactToDto(react)).collect(Collectors.toList());
    }

    @GetMapping("/React/{id-react}")
    public ResponseEntity<ReactDTO> findById(@PathVariable(value = "id-react") final Long id) {
        return new ResponseEntity<>(ReactMapper.MAPPER.reactToDto(reactService.findById(id)), HttpStatus.OK);
    }

    @PostMapping("/React/Post/{id-post}/{id-user}")
    public ResponseEntity<ReactDTO> AddReactOnPost(@RequestBody final ReactDTO newReact, @PathVariable(value = "id-user") final Long id_user,
                                                   @PathVariable(value = "id-post") final Long id_post) {
        final React react = ReactMapper.MAPPER.dtoToReact(newReact);
        return new ResponseEntity<>(ReactMapper.MAPPER.reactToDto(reactService.createReactToPost(react, id_user, id_post)), HttpStatus.CREATED);
    }

    @PostMapping("/React/Comment/{id-comment}/{id-user}")
    public ResponseEntity<ReactDTO> AddReactOnComment(@RequestBody final ReactDTO newReact, @PathVariable(value = "id-user") final Long id_user,
                                                      @PathVariable(value = "id-comment") final Long id_comment) {
        final React react = ReactMapper.MAPPER.dtoToReact(newReact);
        return new ResponseEntity<>(ReactMapper.MAPPER.reactToDto(reactService.createReactToComment(react, id_user, id_comment)), HttpStatus.CREATED);
    }

    @PutMapping("/React/{id-react}")
    public ResponseEntity<ReactDTO> updateReact(@RequestBody final ReactDTO newReact, @PathVariable(value = "id-react") final Long id) {
        final React react = ReactMapper.MAPPER.dtoToReact(newReact);

        return new ResponseEntity<>(ReactMapper.MAPPER.reactToDto(reactService.updateReact(id, react)), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/React/{id-react}")
    public ResponseEntity<?> deleteReact(@PathVariable(value = "id-react") final Long id) {
        reactService.deleteReact(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/React/Post/{id-post}")
    public List<ReactDTO> allReactOfAPost(@PathVariable(value = "id-post") final Long id) {
        return reactService.allReactOfAPost(id).stream().map(react -> ReactMapper.MAPPER.reactToDto(react)).collect(Collectors.toList());
    }

    @GetMapping("/React/Comment/{id-comment}")
    public List<ReactDTO> allReactsOfAComment(@PathVariable(value = "id-comment") final Long id) {
        return reactService.allReactOfAComment(id).stream().map(react -> ReactMapper.MAPPER.reactToDto(react)).collect(Collectors.toList());
    }

    @GetMapping("/React/User/{id-user}")
    public List<PostDTO> allReactsOfAUser(@PathVariable(value = "id-user") final Long id) {
        return reactService.findAllReactOfUserPerPost(id).stream().map(post -> PostMapper.MAPPER.postToDto(post)).collect(Collectors.toList());
    }

    @GetMapping("/React/Posts/{id-Post}")
    public int nbReactPerPost(@PathVariable(value = "id-Post") final Long id) {
        return reactService.numberOfReactPerPost(id);
    }


}
