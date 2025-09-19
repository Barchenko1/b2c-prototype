package com.b2c.prototype.manager.post.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.post.ResponsePostDto;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.manager.post.IPostManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.b2c.prototype.util.PostUtil;
import com.nimbusds.jose.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.EMAIL;
import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.PostUtil.postMap;

@Slf4j
@Service
public class PostManager implements IPostManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public PostManager(IGeneralEntityDao generalEntityDao,
                       IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void savePost(String articularId, PostDto postDto) {
        Post newPost = itemTransformService.mapPostDtoToPost(postDto);
        Item item = generalEntityDao.findEntity(
                "Item.findItemByArticularId",
                Pair.of(ARTICULAR_ID, articularId));

        item.getPosts().add(newPost);
        generalEntityDao.mergeEntity(item);
    }

    @Override
    public void updatePost(String articularId, String postId, PostDto postDto) {
        Post newPost = itemTransformService.mapPostDtoToPost(postDto);
        Item item = generalEntityDao.findEntity(
                "Item.findItemByArticularId",
                Pair.of(ARTICULAR_ID, articularId));

        Optional<Post> optionalPost = findOptionalPost(item.getPosts(), postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            post.setTitle(newPost.getTitle());
            post.setMessage(newPost.getMessage());
            post.setAuthorEmail(newPost.getAuthorEmail());
            post.setAuthorName(newPost.getAuthorName());
            post.setDateOfCreate(newPost.getDateOfCreate());

            generalEntityDao.mergeEntity(item);
        }
    }


    @Override
    public void deletePostByPostId(String articularId, String postId) {
        Item item = generalEntityDao.findEntity(
                "Item.findItemByArticularId",
                Pair.of(ARTICULAR_ID, articularId));

        item.getPosts().stream()
                .filter(post -> postId.equals(post.getPostUniqId()))
                .findFirst()
                .ifPresent(post -> item.getPosts().remove(post));
        generalEntityDao.mergeEntity(item);
    }

    @Override
    public List<ResponsePostDto> getPostListByArticularId(String articularId) {
        AtomicReference<List<ResponsePostDto>> responsePostDtoList = new AtomicReference<>();
        Item item = generalEntityDao.findEntity(
                "Item.findItemWithTopLevelPostsByArticularId",
                Pair.of(ARTICULAR_ID, articularId));
        Set<Post> posts = item.getPosts();
        responsePostDtoList.set(posts.stream()
                .map(PostUtil::toDto)
                .toList());
        return responsePostDtoList.get();
    }

    @Override
    public List<ResponsePostDto> getPostListByEmail(String email) {
        AtomicReference<List<ResponsePostDto>> responsePostDtoList = new AtomicReference<>();
        List<Post> posts = generalEntityDao.findEntityList(
                "Post.findPostWithByEmail",
                Pair.of(EMAIL, email));
        responsePostDtoList.set(posts.stream()
                .map(PostUtil::toDto)
                .toList());
        return responsePostDtoList.get();
    }

    @Override
    public List<ResponsePostDto> getPostListByUserId(String userId) {
        AtomicReference<List<ResponsePostDto>> responsePostDtoList = new AtomicReference<>();
        List<Post> posts = generalEntityDao.findEntityList(
                "Post.findPostWithByUserId",
                Pair.of(USER_ID, userId));
        responsePostDtoList.set(posts.stream()
                .map(PostUtil::toDto)
                .toList());
        return responsePostDtoList.get();
    }

    @Override
    public ResponsePostDto getPostByArticularIdPostId(String articularId, String postId) {
        AtomicReference<ResponsePostDto> responsePostDto = new AtomicReference<>();
        Item item = generalEntityDao.findEntity(
                "Item.findItemByArticularId",
                Pair.of(ARTICULAR_ID, articularId));
        Set<Post> posts = item.getPosts();
        responsePostDto.set(posts.stream()
                .map(PostUtil::toDto)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Post not found")));
        return responsePostDto.get();
    }

    private Optional<Post> findOptionalPost(Set<Post> posts, String postId) {
        return Optional.ofNullable(postMap(posts).get(postId));
    }

}
