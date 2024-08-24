package com.b2c.prototype.test.service;

import com.b2c.prototype.dao.post.IPostDao;
import com.b2c.prototype.modal.dto.request.RequestPostDto;
import com.b2c.prototype.modal.dto.update.RequestPostDtoUpdate;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.service.post.PostService;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.util.TransitiveSelfEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostServiceTest {

    @Mock
    private IPostDao postDao;

    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postDao);
    }

    @Test
    void createNewPost() {
        // Arrange
        RequestPostDto requestPostDto = new RequestPostDto();
        requestPostDto.setTitle("TestTitle");
        requestPostDto.setMessage("TestMessage");
        requestPostDto.setAuthorUserName("TestUser");
        requestPostDto.setAuthorEmail("test@example.com");

        doNothing().when(postDao).saveEntityTree(any(Post.class));

        // Act
        postService.createNewPost(requestPostDto);

        // Assert
        verify(postDao, times(1)).saveEntityTree(any(Post.class));
    }

    @Test
    void updatePost() {
        // Arrange
        RequestPostDtoUpdate requestUpdatePostDto = new RequestPostDtoUpdate();
        RequestPostDto newRequestPostDto = new RequestPostDto();
        newRequestPostDto.setTitle("UpdatedTitle");
        newRequestPostDto.setMessage("UpdatedMessage");
        newRequestPostDto.setAuthorUserName("UpdatedUser");
        newRequestPostDto.setAuthorEmail("updated@example.com");
        requestUpdatePostDto.setNewEntityDto(newRequestPostDto);

        RequestPostDto oldRequestPostDto = new RequestPostDto();
        oldRequestPostDto.setUniquePostId("unique-id-123");
        requestUpdatePostDto.setOldEntityDto(oldRequestPostDto);

        Post existingPost = new Post();
        existingPost.setUniquePostId("unique-id-123");

        when(postDao.getOptionalEntityBySQLQueryWithParams(anyString(), eq("unique-id-123"))).thenReturn(Optional.of(existingPost));
        doNothing().when(postDao).updateEntityTree(any(), any(Post.class));

        // Act
        postService.updatePost(requestUpdatePostDto);

        // Assert
        verify(postDao, times(1)).updateEntityTree(any(), any(Post.class));
    }

    @Test
    void deletePostByUniqueId() {
        // Arrange
        String uniqueId = "unique-id-123";
        Post post = new Post();
        post.setUniquePostId(uniqueId);

        when(postDao.getOptionalEntityBySQLQueryWithParams(anyString(), eq(uniqueId))).thenReturn(Optional.of(post));
        doNothing().when(postDao).deleteEntityTree(any());

        // Act
        postService.deletePostByUniqueId(uniqueId);

        // Assert
        verify(postDao, times(1)).deleteEntityTree(any());
    }

    @Test
    void getPostListByPostTitle() {
        // Arrange
        String title = "TestTitle";
        List<TransitiveSelfEntity> postList = new ArrayList<>();
        when(postDao.getEntityListBySQLQueryWithParams(anyString(), eq(title))).thenReturn(postList);

        // Act
        List<Post> result = postService.getPostListByPostTitle(title);

        // Assert
        assertEquals(postList, result);
        verify(postDao, times(1)).getEntityListBySQLQueryWithParams(anyString(), eq(title));
    }

    @Test
    void getPostListByEmail() {
        // Arrange
        String email = "test@example.com";
        List<TransitiveSelfEntity> postList = new ArrayList<>();
        when(postDao.getEntityListBySQLQueryWithParams(anyString(), eq(email))).thenReturn(postList);

        // Act
        List<Post> result = postService.getPostListByEmail(email);

        // Assert
        assertEquals(postList, result);
        verify(postDao, times(1)).getEntityListBySQLQueryWithParams(anyString(), eq(email));
    }

    @Test
    void getPostListByUserName() {
        // Arrange
        String username = "TestUser";
        List<TransitiveSelfEntity> postList = new ArrayList<>();
        when(postDao.getEntityListBySQLQueryWithParams(anyString(), eq(username))).thenReturn(postList);

        // Act
        List<Post> result = postService.getPostListByUserName(username);

        // Assert
        assertEquals(postList, result);
        verify(postDao, times(1)).getEntityListBySQLQueryWithParams(anyString(), eq(username));
    }

    @Test
    void getPostByUniqueId() {
        // Arrange
        String uniqueId = "unique-id-123";
        Post post = new Post();
        post.setUniquePostId(uniqueId);

        when(postDao.getOptionalEntityBySQLQueryWithParams(anyString(), eq(uniqueId))).thenReturn(Optional.of(post));

        // Act
        Post result = postService.getPostByUniqueId(uniqueId);

        // Assert
        assertEquals(post, result);
        verify(postDao, times(1)).getOptionalEntityBySQLQueryWithParams(anyString(), eq(uniqueId));
    }

    @Test
    void getAllPostsTree() {
        // Arrange
        Map<TransitiveSelfEnum, List<TransitiveSelfEntity>> postTree = new HashMap<>();
        when(postDao.getEntitiesTreeBySQLQuery(anyString())).thenReturn(postTree);

        // Act
        Map<TransitiveSelfEnum, List<Post>> result = postService.getAllPostsTree();

        // Assert
        assertEquals(postTree, result);
        verify(postDao, times(1)).getEntitiesTreeBySQLQuery(anyString());
    }
}