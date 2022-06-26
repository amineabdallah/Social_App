package com.example.Social_App.Service;

import com.example.Social_App.Model.Follow;
import com.example.Social_App.Model.User;
import com.example.Social_App.Repositiry.FollowRepository;
import com.example.Social_App.Repositiry.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    public FollowService(final FollowRepository followRepository, final UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public List<Follow> getAllFollows() {
        return followRepository.findAll();
    }

    public boolean createFollow(final Follow follow, final Long id_User_This, final Long id_User_ToThis) {
        final User This = userRepository.findById(id_User_This).orElseThrow(() -> new RuntimeException("No data found!"));
        final User ToThis = userRepository.findById(id_User_ToThis).orElseThrow(() -> new RuntimeException("No data found!"));
        if (!(this.Follow_Exsist(This, ToThis)) && (id_User_This != id_User_ToThis)) {
            follow.setThis_One(This);
            follow.setTo_This(ToThis);
            followRepository.save(follow);
            return true;
        }
        return false;
    }

    public Follow findById(final Long id_Comment) {
        return followRepository.findById(id_Comment).orElseThrow(() -> new RuntimeException("No data found!"));
    }


    public void deleteFollow(final Long id_Follow) {
        final Follow oldFollow = followRepository.findById(id_Follow).orElseThrow(() -> new RuntimeException("No data found!"));
        followRepository.delete(oldFollow);
    }

    public boolean Follow_Exsist(final User user1, final User user2) {
        if (followRepository.numberOfFollow(user1, user2) == 1) {
            return true;
        }
        return false;
    }

}
