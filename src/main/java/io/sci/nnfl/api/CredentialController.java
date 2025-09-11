package io.sci.nnfl.api;

import io.sci.nnfl.api.dto.SignInRequest;
import io.sci.nnfl.model.User;
import io.sci.nnfl.model.dto.CreatePasswordRequest;
import io.sci.nnfl.model.dto.UserRequest;
import io.sci.nnfl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class CredentialController extends BaseApiController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/check-credential-id", method = RequestMethod.POST)
    public ResponseEntity<Response> checkUserName(@RequestBody String username) {
        int result = 0;
        User user = userService.getUser(username);
        if (user==null) {
            result = -1;
        }else {
            if (user.isEnabled()){
                result = 1;
            }
        }
        return getHttpStatus(new Response(result));
    }

    @RequestMapping(value = "/create-password", method = RequestMethod.POST)
    public ResponseEntity<Response> createPassword(@RequestBody CreatePasswordRequest createPasswordRequest) {
        try {
            User user = userService.createPassword(createPasswordRequest);
            String token = createToken(user);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Token", token);
            return getHttpStatus(new Response(user), responseHeaders);
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<Response> signIn(@RequestBody SignInRequest req) {
        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
            var userDetails = userDetailsService.loadUserByUsername(req.username());
            if (userDetails!=null) {
                User user = userService.getUser(userDetails.getUsername());
                String token = createToken(user);
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("Token", token);
                return getHttpStatus(new Response(user), responseHeaders);
            }else{
                return response(HttpStatus.UNAUTHORIZED, new Response());
            }
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<Response> signUp(@RequestBody UserRequest request) {
        try {
            int result = 0;
            User user = userService.signUp(request);
            if (user==null) {
                result = -1;
            }else {
                if (user.isEnabled()){
                    result = 1;
                }
            }
            return getHttpStatus(new Response(result));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }


}

