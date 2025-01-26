package com.b2c.prototype.controller.basic;

import com.b2c.prototype.modal.dto.payload.PostDto;
import com.b2c.prototype.service.processor.post.IPostService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewPost(@RequestBody final PostDto requestPostDto) {
        postService.savePost(requestPostDto);

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
