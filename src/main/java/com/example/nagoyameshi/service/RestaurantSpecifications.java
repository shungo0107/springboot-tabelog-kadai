package com.example.nagoyameshi.service;


import org.springframework.data.jpa.domain.Specification;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;

import jakarta.persistence.criteria.Join;

public class RestaurantSpecifications {

	 // 店舗検索用のSQLを動的に作成する
    /*
     * Specification<～～～>
     * 　⇒ 返り値の型はSpecification<～～～>です。
     *          これはSpring Data JPAのSpecificationインターフェースを実装することで、クエリの条件を定義できます。
     * (root, query, criteriaBuilder)
     * 　⇒ ラムダ式のパラメータリストです。ここでは、メソッドが3つの引数を取ることを示しています。これらはそれぞれ、
     *          Root<T>、CriteriaQuery<?>、CriteriaBuilderの型で、JPAクエリを構築するのに使用されます。
     * category != null ? criteriaBuilder.equal(root.get("category"), category) : null;: 
     * 　⇒ これはラムダ式の本体です。
     * category != null: 
     * 　⇒ categoryがnullでないかをチェックします。
     * criteriaBuilder.equal(root.get("category"), category): 
     * 　⇒CriteriaBuilderを使って、「categoryが特定の値に等しい」という条件を構築します。
     */
    public static Specification<Restaurant> hasName(String name) {
        return (root, query, criteriaBuilder) ->
        name != null ? criteriaBuilder.like(root.get("name"), "%" + name + "%") : null;
    }
    
    public static Specification<Restaurant> hasAddress(String address) {
        return (root, query, criteriaBuilder) ->
        address != null ? criteriaBuilder.like(root.get("address"), "%" + address + "%") : null;
    }

    public static Specification<Restaurant> hasCategory(Integer categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
            Join<Category, Restaurant> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
        };
    }

    public static Specification<Restaurant> priceLessThanOrEqualTo(Integer price) {
        return (root, query, criteriaBuilder) ->
                price != null ? criteriaBuilder.lessThanOrEqualTo(root.get("price"), price) : null;
    }

/*
     //並び替え条件追加メソッド
    public static Sort sortBy(String fieldName, boolean ascending) {
        return ascending ? Sort.by(fieldName).ascending() : Sort.by(fieldName).descending();
    }
*/
    
}
