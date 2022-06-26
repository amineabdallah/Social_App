package com.example.Social_App.Controller;

import com.example.Social_App.DTO.FollowDTO;
import com.example.Social_App.Mapper.FollowMapper;
import com.example.Social_App.Model.Follow;
import com.example.Social_App.Service.FollowService;
import com.example.Social_App.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FollowController {


    private final UserService userService;

    private final FollowService followService;

    public FollowController(final UserService userService, final FollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }


    @GetMapping("/Follows")
    public List<FollowDTO> getAllFollows() {
        return followService.getAllFollows().stream().map(follow -> FollowMapper.MAPPER.followToDto(follow)).collect(Collectors.toList());
    }

    @GetMapping("/Follow/{id-follow}")
    public ResponseEntity<FollowDTO> findById(@PathVariable(value = "id-follow") final Long id) {
        return new ResponseEntity<>(FollowMapper.MAPPER.followToDto(followService.findById(id)), HttpStatus.OK);
    }

    @PostMapping("/Follow/{id-user}/{id-user2}")
    public ResponseEntity AddFollow(final FollowDTO newFollow, @PathVariable(value = "id-user") final Long id_user,
                                    @PathVariable(value = "id-user2") final Long id_user2) {
        final Follow follow = FollowMapper.MAPPER.dtoTofollow(newFollow);
        if (followService.createFollow(follow, id_user, id_user2))
            return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/Follow/{id-follow}")

    public ResponseEntity<?> deleteFollow(@PathVariable(value = "id-follow") final Long id) {
        followService.deleteFollow(id);
        return ResponseEntity.noContent().build();
    }

}
