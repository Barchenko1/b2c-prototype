package com.b2c.prototype;

import com.b2c.prototype.dao.basic.BasicPostDao;
import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.modal.client.dto.request.RequestPostDto;
import com.b2c.prototype.modal.client.dto.update.RequestPostDtoUpdate;
import com.b2c.prototype.modal.client.entity.post.Post;
import com.b2c.prototype.service.client.post.IPostService;
import com.b2c.prototype.service.client.post.PostService;
import com.tm.core.configuration.ConfigDbType;
import com.tm.core.configuration.factory.ConfigurationSessionFactory;
import com.tm.core.configuration.factory.IConfigurationSessionFactory;
import com.tm.core.util.TransitiveSelfEnum;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

public class PostTestMain {
    public static void main(String[] args) {
        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                ConfigDbType.XML
        );
        SessionFactory sessionFactory = configurationSessionFactory.getSessionFactory();
        IPostDao postDao = new BasicPostDao(sessionFactory);

        IPostService postService = new PostService(postDao);

        RequestPostDto firstPostDto = getFirstPost();
        RequestPostDto secondPostDto = getSecondPost();

        RequestPostDtoUpdate requestUpdatePostDto = new RequestPostDtoUpdate();

        requestUpdatePostDto.setOldEntityDto(firstPostDto);
        requestUpdatePostDto.setNewEntityDto(secondPostDto);

        postService.createNewPost(firstPostDto);
//        secondPostDto.getParent().setUniquePostId(firstPostDto.getUniquePostId());
//        postService.createNewPost(secondPostDto);
//        Post post = postService.getPostByUniqueId("a7d5cb02-3abf-4251-8009-1ae94581e471");
        postService.updatePost(requestUpdatePostDto);
        Map<TransitiveSelfEnum, List<Post>> postsTree = postService.getAllPostsTree();
        postService.deletePostByUniqueId("abc");
    }

    private static RequestPostDto getFirstPost() {
        RequestPostDto postDto = new RequestPostDto();
        postDto.setTitle("carTitle");
        postDto.setMessage("carMessage");
        postDto.setUniquePostId("abc");
        postDto.setAuthorEmail("email");
        postDto.setAuthorUserName("username");
        postDto.setParent(null);

        return postDto;
    }

    private static RequestPostDto getSecondPost() {
        RequestPostDto parentPostDto = new RequestPostDto();
        parentPostDto.setUniquePostId("abc");


        RequestPostDto postDto = new RequestPostDto();
        postDto.setTitle("carTitle1");
        postDto.setMessage("carMessage1");
        postDto.setUniquePostId("abc1");
        postDto.setAuthorEmail("email1");
        postDto.setAuthorUserName("username1");
        postDto.setParent(parentPostDto);

        return postDto;
    }
}
