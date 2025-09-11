package io.sci.nnfl.api;

import io.sci.nnfl.model.User;
import io.sci.nnfl.model.dto.ChangePasswordRequest;
import io.sci.nnfl.model.dto.UserRequest;
import io.sci.nnfl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserApiController extends BaseApiController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/change-pwd", method = RequestMethod.POST)
    public ResponseEntity<Response> changePassword(@RequestHeader("Authorization") String token,
                                                   @RequestBody ChangePasswordRequest request) {
        try {
            if (!authorize(token)) {
                return FORBIDDEN;
            }

            String userId = getUserId(token);
            User user = userService.getById(Long.parseLong(userId));
            userService.changePassword(user, request.getCurrentPassword(), request.getNewPassword());
            return getHttpStatus(new Response(user));

        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/update-profile", method = RequestMethod.POST)
    public ResponseEntity<Response> changeProfile(@RequestHeader("Authorization") String token,
                                                  @RequestBody UserRequest request) {
        try {
            if (!authorize(token)) {
                return FORBIDDEN;
            }

            String userId = getUserId(token);
            User user = userService.getById(Long.parseLong(userId));
            user = userService.updateProfile(user, request);
            return getHttpStatus(new Response(user));

        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

}

