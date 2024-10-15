
    create table app_user (
       id bigserial not null,
        email varchar(255),
        password varchar(255),
        username varchar(255),
        bucket_id bigint,
        role_id bigint,
        userAddress_id bigint,
        userDetails_id bigint,
        userPayment_id bigint,
        primary key (id)
    )
 
    
    create table brand (
       id bigserial not null,
        brand smallint,
        primary key (id)
    )
 
    
    create table bucket (
       id bigserial not null,
        user_id bigint,
        primary key (id)
    )
 
    
    create table bucket_has_product (
       bucket_id bigint not null,
        product_id bigint not null
    )
 
    
    create table category (
       id bigserial not null,
        category smallint,
        primary key (id)
    )
 
    
    create table comment (
       id bigserial not null,
        authorEmail varchar(255),
        authorName varchar(255),
        message varchar(255),
        post_id bigint,
        primary key (id)
    )
 
    
    create table currencyDiscount (
       id bigserial not null,
        primary key (id)
    )
 
    
    create table wishlist (
       id bigserial not null,
        product_id bigint,
        user_id bigint,
        primary key (id)
    )
 
    
    create table option_group (
       id bigserial not null,
        optionsGroup smallint,
        primary key (id)
    )
 
    
    create table option_item (
       id bigserial not null,
        optionGroup_id bigint,
        primary key (id)
    )
 
    
    create table option_item_product (
       Option_id bigint not null,
        products_id bigint not null
    )
 
    
    create table order_address (
       id bigserial not null,
        apartmentNumber integer not null,
        building integer not null,
        flor integer not null,
        street varchar(255),
        primary key (id)
    )
 
    
    create table order_details (
       id bigserial not null,
        primary key (id)
    )
 
    
    create table order_history (
       id bigserial not null,
        dataOfOrder timestamp(6),
        order_id bigint,
        user_id bigint,
        primary key (id)
    )
 
    
    create table order_item (
       id bigserial not null,
        orderAddress_id bigint,
        orderDetails_id bigint,
        orderStatus_id bigint,
        product_id bigint,
        user_id bigint,
        primary key (id)
    )
 
    
    create table order_status (
       id bigserial not null,
        orderStatus smallint,
        primary key (id)
    )
 
    
    create table post (
       id bigserial not null,
        authorEmail varchar(255),
        authorName varchar(255),
        message varchar(255),
        primary key (id)
    )
 
    
    create table post_comment (
       Post_id bigint not null,
        comments_id bigint not null
    )
 
    
    create table item (
       id bigserial not null,
        productName varchar(255),
        brand_id bigint,
        category_id bigint,
        discount_id bigint,
        productType_id bigint,
        primary key (id)
    )
 
    
    create table product_option (
       product_id bigint not null,
        option_item_id bigint not null
    )
 
    
    create table product_post (
       Product_id bigint not null,
        posts_id bigint not null
    )
 
    
    create table product_review (
       product_id bigint not null,
        review_id bigint not null
    )
 
    
    create table product_type (
       id bigserial not null,
        itemType smallint,
        primary key (id)
    )
 
    
    create table rating (
       id bigserial not null,
        rating smallint,
        primary key (id)
    )
 
    
    create table review (
       id bigserial not null,
        advantage varchar(255),
        flaw varchar(255),
        postComment_id bigint,
        primary key (id)
    )
 
    
    create table review_rating (
       review_id bigint not null,
        rating_id bigint not null
    )
 
    
    create table shop (
       id bigserial not null,
        shopAddress_id bigint,
        primary key (id)
    )
 
    
    create table shop_address (
       id bigserial not null,
        apartmentNumber integer not null,
        building integer not null,
        flor integer not null,
        street varchar(255),
        primary key (id)
    )
 
    
    create table shop_product (
       shop_id bigint not null,
        product_id bigint not null
    )
 
    
    create table store (
       id bigserial not null,
        storeAddress_id bigint,
        primary key (id)
    )
 
    
    create table store_address (
       id bigserial not null,
        apartmentNumber integer not null,
        building integer not null,
        flor integer not null,
        street varchar(255),
        primary key (id)
    )
 
    
    create table store_product (
       store_id bigint not null,
        product_id bigint not null
    )
 
    
    create table store_shop (
       store_id bigint not null,
        shop_id bigint not null
    )
 
    
    create table user_address (
       id bigserial not null,
        apartmentNumber integer not null,
        building integer not null,
        flor integer not null,
        street varchar(255),
        primary key (id)
    )
 
    
    create table user_details (
       id bigserial not null,
        primary key (id)
    )
 
    
    create table user_payment (
       id bigserial not null,
        primary key (id)
    )
 
    
    create table user_role (
       id bigserial not null,
        name smallint,
        primary key (id)
    )
 
    
    alter table if exists post_comment 
       drop constraint if exists UK_se9l149iyyao6va95afioxsrl
 
    
    alter table if exists post_comment 
       add constraint UK_se9l149iyyao6va95afioxsrl unique (comments_id)
 
    
    alter table if exists product_post 
       drop constraint if exists UK_g7k13n5ppl87341qwxltt5mmg
 
    
    alter table if exists product_post 
       add constraint UK_g7k13n5ppl87341qwxltt5mmg unique (posts_id)
 
    
    alter table if exists app_user 
       add constraint FKjehcai3yacqcjtivt4c790xsh 
       foreign key (bucket_id) 
       references bucket
 
    
    alter table if exists app_user 
       add constraint FKjsrnof3jy28c9eno19i04dxd5 
       foreign key (role_id) 
       references user_role
 
    
    alter table if exists app_user 
       add constraint FKqmgtpbsha7mnfwhnt292kn10b 
       foreign key (userAddress_id) 
       references user_address
 
    
    alter table if exists app_user 
       add constraint FKe0hdd0vdaii0ddb4e8e5of5xi 
       foreign key (userDetails_id) 
       references user_details
 
    
    alter table if exists app_user 
       add constraint FK4cu3q31b3qginbrbw6tihpdp7 
       foreign key (userPayment_id) 
       references user_payment
 
    
    alter table if exists bucket 
       add constraint FKni9q3au75tm8kke6xiwn03oed 
       foreign key (user_id) 
       references app_user
 
    
    alter table if exists bucket_has_product 
       add constraint FKl4ibxcosh8uxp4q6tvownb0pi 
       foreign key (product_id) 
       references item
 
    
    alter table if exists bucket_has_product 
       add constraint FKjnr92kq55cq1i3tcxkeypcd85 
       foreign key (bucket_id) 
       references bucket
 
    
    alter table if exists comment 
       add constraint FKs1slvnkuemjsq2kj4h3vhx7i1 
       foreign key (post_id) 
       references post
 
    
    alter table if exists wishlist
       add constraint FK4ufc24y6gaa606e994oytt868 
       foreign key (product_id) 
       references item
 
    
    alter table if exists wishlist
       add constraint FKmmxj3qvngcm3anmmpu3aktevo 
       foreign key (user_id) 
       references app_user
 
    
    alter table if exists option_item 
       add constraint FKi8a7hi02dpehhjq7c2qwh5sai 
       foreign key (optionGroup_id) 
       references option_group
 
    
    alter table if exists option_item_product 
       add constraint FKi50w9lx80mdq85qvm0r5nrakj 
       foreign key (products_id) 
       references item
 
    
    alter table if exists option_item_product 
       add constraint FK8iquwtarvhr1w8bib6wloe699 
       foreign key (Option_id) 
       references option_item
 
    
    alter table if exists order_history 
       add constraint FKpnw8e9962yho1tmyd9e5dr0x6 
       foreign key (order_id) 
       references order_item
 
    
    alter table if exists order_history 
       add constraint FK4ximere3hjjqduwoj7l8pbtuw 
       foreign key (user_id) 
       references app_user
 
    
    alter table if exists order_item 
       add constraint FK4nrd5793tvanb3yqg9gv28yde 
       foreign key (orderAddress_id) 
       references order_address
 
    
    alter table if exists order_item 
       add constraint FKs0ipkmy5qp5j4gklwimx4kb8u 
       foreign key (orderDetails_id) 
       references order_details
 
    
    alter table if exists order_item 
       add constraint FKdaf9qb7qp4puef6sglu8us441 
       foreign key (orderStatus_id) 
       references order_status
 
    
    alter table if exists order_item 
       add constraint FK551losx9j75ss5d6bfsqvijna 
       foreign key (product_id) 
       references item
 
    
    alter table if exists order_item 
       add constraint FKcgs0relfw84njsvcj5rbp94n3 
       foreign key (user_id) 
       references app_user
 
    
    alter table if exists post_comment 
       add constraint FKagqftk08s8sjuc6bbr9v99vbe 
       foreign key (comments_id) 
       references comment
 
    
    alter table if exists post_comment 
       add constraint FKnm3lpb10fjje1itg5x6c7w7w4 
       foreign key (Post_id) 
       references post
 
    
    alter table if exists item
       add constraint FKs6cydsualtsrprvlf2bb3lcam 
       foreign key (brand_id) 
       references brand
 
    
    alter table if exists item
       add constraint FK1mtsbur82frn64de7balymq9s 
       foreign key (category_id) 
       references category
 
    
    alter table if exists item
       add constraint FKps2e0q9jpd0i9kj83je4rsmf1 
       foreign key (discount_id) 
       references currencyDiscount
 
    
    alter table if exists item
       add constraint FKr85c7p8l1mscmgmpek6ur5nql 
       foreign key (productType_id) 
       references product_type
 
    
    alter table if exists product_option 
       add constraint FKpuybkmydotfrlb904t6fevwmo 
       foreign key (option_item_id) 
       references option_item
 
    
    alter table if exists product_option 
       add constraint FKn4hmm6ex1vgn60c6uiqte400f 
       foreign key (product_id) 
       references item
 
    
    alter table if exists product_post 
       add constraint FKo7tiqdd9c7kk70x22i4gfquec 
       foreign key (posts_id) 
       references post
 
    
    alter table if exists product_post 
       add constraint FKc1gc0qyxtd5mv44qb9ohmxhmj 
       foreign key (Product_id) 
       references item
 
    
    alter table if exists product_review 
       add constraint FKedgxhnvlh3k85mq80smlpwdej 
       foreign key (review_id) 
       references review
 
    
    alter table if exists product_review 
       add constraint FKkaqmhakwt05p3n0px81b9pdya 
       foreign key (product_id) 
       references item
 
    
    alter table if exists review 
       add constraint FKlt2geq3kgswh80db4q6rxw6je 
       foreign key (postComment_id) 
       references comment
 
    
    alter table if exists review_rating 
       add constraint FKfj1ya3a5vfomf4q2ggujvkbpb 
       foreign key (rating_id) 
       references rating
 
    
    alter table if exists review_rating 
       add constraint FKh37ipkn319mk4u2v5w193jx81 
       foreign key (review_id) 
       references review
 
    
    alter table if exists shop 
       add constraint FKpuckw89heucgdrk03r60hhira 
       foreign key (shopAddress_id) 
       references shop_address
 
    
    alter table if exists shop_product 
       add constraint FKagx9ubmm4qiq1whqrf5f7wdc6 
       foreign key (product_id) 
       references item
 
    
    alter table if exists shop_product 
       add constraint FK9n6cn7s1s7sdysss12k52v8sa 
       foreign key (shop_id) 
       references shop
 
    
    alter table if exists store 
       add constraint FKaxmox3jfaoo9tosgh5eh2bvkp 
       foreign key (storeAddress_id) 
       references store_address
 
    
    alter table if exists store_product 
       add constraint FKd91qk8cbmboomritdwn351tak 
       foreign key (product_id) 
       references item
 
    
    alter table if exists store_product 
       add constraint FKdncsr7lgl9pdsq71314etuwrp 
       foreign key (store_id) 
       references store
 
    
    alter table if exists store_shop 
       add constraint FKphg6q3idmg7752twftyi4mf26 
       foreign key (shop_id) 
       references shop
 
    
    alter table if exists store_shop 
       add constraint FKhsx2ivwxjwyd13iuluortvmo0 
       foreign key (store_id) 
       references store

