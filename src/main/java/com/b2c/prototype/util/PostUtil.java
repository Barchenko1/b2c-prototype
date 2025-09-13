package com.b2c.prototype.util;

import com.b2c.prototype.modal.dto.payload.post.ResponsePostDto;
import com.b2c.prototype.modal.entity.post.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostUtil {

    public static ResponsePostDto toDto(Post entity) {
        if (entity == null) {
            return null;
        }
        return ResponsePostDto.builder()
                .title(entity.getTitle())
                .message(entity.getMessage())
                .authorEmail(entity.getAuthorEmail())
                .authorName(entity.getAuthorName())
                .postId(entity.getPostUniqId())
//                .dateOfCreate(entity.getDateOfCreate())
                .childList(entity.getChildList() != null
                        ? entity.getChildList().stream()
                        .map(PostUtil::toDto)
                        .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public static List<String> getAllValues(List<Post> posts) {
        return posts.stream()
                .flatMap(PostUtil::flattenPost)
                .collect(Collectors.toList());
    }

    private static Stream<String> flattenPost(Post post) {
        return Stream.concat(
                Stream.of(post.getPostUniqId()),
                post.getChildList() == null ? Stream.empty() :
                        post.getChildList().stream().flatMap(PostUtil::flattenPost)
        );
    }

    public static Map<String, Post> postMap(Set<Post> posts) {
        return flattenPosts(posts).stream()
                .collect(Collectors.toMap(Post::getPostUniqId, Function.identity(), (existing, replacement) -> existing));
    }

    public static List<Post> flattenPosts(Set<Post> posts) {
        List<Post> flatList = new ArrayList<>();
        for (Post post : posts) {
            extractNestedPosts(post, flatList);
        }
        return flatList;
    }

    private static void extractNestedPosts(Post post, List<Post> flatList) {
        if (post == null) return;

        flatList.add(post);

        if (post.getChildList() != null) {
            for (Post child : post.getChildList()) {
                extractNestedPosts(child, flatList);
            }
        }
    }
}
