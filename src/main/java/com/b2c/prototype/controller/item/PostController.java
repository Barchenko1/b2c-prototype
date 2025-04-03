package com.b2c.prototype.controller.item;

import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.post.ResponsePostDto;
import com.b2c.prototype.processor.item.IPostProcess;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final IPostProcess postProcess;

    public PostController(IPostProcess postProcess) {
        this.postProcess = postProcess;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUpdatePost(@RequestParam final Map<String, String> requestParams,
                                                     @RequestBody final PostDto postDto) {
        postProcess.saveUpdatePost(requestParams, postDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletePostByUniqueId(@RequestParam final Map<String, String> requestParams) {
        postProcess.deletePostByUniqueId(requestParams);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponsePostDto> getPostList(@RequestParam final Map<String, String> requestParams) {
        return postProcess.getPostList(requestParams);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponsePostDto getPostByArticularIdPostId(@RequestParam final Map<String, String> requestParams) {
        return postProcess.getPostByArticularIdPostId(requestParams);
    }

}
