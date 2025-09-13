package com.b2c.prototype.util;

import com.b2c.prototype.modal.entity.review.ReviewComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReviewCommentUtil {
    public static Map<String, ReviewComment> reviewCommentMap(List<ReviewComment> reviewComments) {
        return flattenReviewComments(reviewComments).stream()
                .collect(Collectors.toMap(ReviewComment::getReviewCommentUniqId, Function.identity(), (existing, replacement) -> existing));
    }

    public static List<ReviewComment> flattenReviewComments(List<ReviewComment> reviewComments) {
        List<ReviewComment> flatList = new ArrayList<>();
        for (ReviewComment reviewComment : reviewComments) {
            extractNestedCategories(reviewComment, flatList);
        }
        return flatList;
    }

    private static void extractNestedCategories(ReviewComment reviewComment, List<ReviewComment> flatList) {
        if (reviewComment == null) return;

        flatList.add(reviewComment);

        if (reviewComment.getChildList() != null) {
            for (ReviewComment child : reviewComment.getChildList()) {
                extractNestedCategories(child, flatList);
            }
        }
    }
}
