package com.example.Social_App.Controller;

import com.example.Social_App.DTO.UserCreationDTO;
import com.example.Social_App.DTO.UserDTO;
import com.example.Social_App.Mapper.UserCreationMapper;
import com.example.Social_App.Mapper.UserMapper;
import com.example.Social_App.Model.ConfirmationToken;
import com.example.Social_App.Model.User;
import com.example.Social_App.Service.ConfirmationTokenService;
import com.example.Social_App.Service.Email.EmailSender;
import com.example.Social_App.Service.EmailValidator;
import com.example.Social_App.Service.UserService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {


    private final UserService userService;
    private final EmailValidator emailValidator;
    private final EmailSender emailSender;

    private final ConfirmationTokenService confirmationTokenService;

    public UserController(final UserService userService, final EmailValidator emailValidator, final EmailSender emailSender, final ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.emailValidator = emailValidator;
        this.emailSender = emailSender;
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping("/Users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(user -> UserMapper.MAPPER.userToDto(user)).collect(Collectors.toList());

    }

    @GetMapping("/Users/{id-user}")
    public ResponseEntity<UserDTO> findById(@PathVariable(value = "id-user") final Long id) {
        return new ResponseEntity<>(UserMapper.MAPPER.userToDto(userService.findById(id)), HttpStatus.OK);
    }

    @PostMapping("/User")
    public ResponseEntity<UserDTO> AddUser(@RequestBody final UserCreationDTO userc) {
        final User user = UserCreationMapper.MAPPER.dtoToUser(userc);

        return new ResponseEntity<>(UserMapper.MAPPER.userToDto(userService.createUser(user)), HttpStatus.CREATED);

    }

    @PostMapping("/Sign")
    public String Sign(@RequestBody final UserCreationDTO userc) {
        final User user = UserCreationMapper.MAPPER.dtoToUser(userc);
        final boolean isValidEmail = emailValidator.test(userc.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        final String token = userService.sign(user);
        final String link = "http://localhost:8082/Sign/confirm?token=" + token;
        emailSender.send(
                user.getEmail(),
                buildEmail(user.getFirstname(), link));

        return token;
    }

    @GetMapping("Sign/confirm")
    public String confirm(@RequestParam("token") final String token) {
        return this.confirmToken(token);
    }

    @Transactional
    public String confirmToken(final String token) {
        final ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        final LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    @PutMapping("/Users/{id-user}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody final UserCreationDTO userc, @PathVariable(value = "id-user") final Long id) {
        final User user = UserCreationMapper.MAPPER.dtoToUser(userc);

        return new ResponseEntity<>(UserMapper.MAPPER.userToDto(userService.updateUser(id, user)), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/Users/{id-user}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id-user") final Long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/Users/Role")
    public ResponseEntity<UserDTO> addRtoUser(@RequestBody final RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());

        return ResponseEntity.ok().build();
    }

    private String buildEmail(final String name, final String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}


@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}