alter table if exists category
drop constraint if exists FKap0cnk1255oj4bwam7in1hxxv

alter table if exists contact_info
drop constraint if exists FKbbcsefxe8ax16j1dndb9l7mms

alter table if exists contact_phone
drop constraint if exists FKbvstb62j7buq9fxiaefcfxfnd

alter table if exists currency_discount
drop constraint if exists FK4c47cfya2tgceha3msekcmc7d

alter table if exists delivery
drop constraint if exists FKlayueon76a86wr2gbsyannaf7

alter table if exists delivery
drop constraint if exists FKiu02keoyiv4xhc28rsi86mhll

alter table if exists item
drop constraint if exists FK5pc91oc43bq6tqaugwvc8f4or

alter table if exists item_data
drop constraint if exists FK7yqp32kg84bewf1nnq1lhirqa

alter table if exists item_data
drop constraint if exists FKnp91gprwy000sgedoo83oymji

alter table if exists item_data
drop constraint if exists FK1jvdlvc3rfvki7v5jus0m19s3

alter table if exists item_data
drop constraint if exists FKcipiyxs05i6ek9hiwhscny7c0

alter table if exists item_data
drop constraint if exists FKdiwrprwyudwa44upjvl8k87h9

alter table if exists item_data
drop constraint if exists FK810alvugy52y6s9no5oppb26j

alter table if exists item_data
drop constraint if exists FKiksl8lnvms0xfx27yoo9fj02p

alter table if exists item_data
drop constraint if exists FKimkmn00n05kk4ap3ektej3itd

alter table if exists item_data_option
drop constraint if exists FKahke2r2nw7hvi4q3426oju9xw

alter table if exists item_data_option
drop constraint if exists FKcvvtx9qekfpisvrgqj136tft1

alter table if exists item_data_quantity_item_data
drop constraint if exists FK5r4ejgb9q1cwdjt6af4eusa6i

alter table if exists item_data_quantity_item_data
drop constraint if exists FKmokvmesmv3cxly9a5l6rux7pa

alter table if exists item_post
drop constraint if exists FK1grghtm8o53yfmfixtu97em69

alter table if exists item_post
drop constraint if exists FKhld6fk2skal2v9mk7na8qv9uf

alter table if exists item_review
drop constraint if exists FKewycb8h8ogg4ssiwd5v64sbgm

alter table if exists item_review
drop constraint if exists FKdk8811562blj2cqlok7cknd70

alter table if exists message
drop constraint if exists FKc1vhu79usc0hfr9gp6fuu3ah5

alter table if exists message
drop constraint if exists FK3kx251aos03ltpyc1dxofsrck

alter table if exists message_box
drop constraint if exists FKl5lntrj6p83iu21a4eno38vy0

alter table if exists message_box_message
drop constraint if exists FK7n91muashe5nwvufuaxicvrux

alter table if exists message_box_message
drop constraint if exists FKss2bsrmbp3w9d2guos7y0amdh

alter table if exists message_receivers
drop constraint if exists FKsfmc2unubrcf9ivflgn3nrc83

alter table if exists option_item
drop constraint if exists FKi8a7hi02dpehhjq7c2qwh5sai

alter table if exists order_item
drop constraint if exists FKbx40hdv2o0kdjt1xcbefjcl2h

alter table if exists order_item
drop constraint if exists FKdaf9qb7qp4puef6sglu8us441

alter table if exists order_item
drop constraint if exists FK2vebnru38ddo0wk30i9ybhx8c

alter table if exists order_item
drop constraint if exists FKl4ignmwea39w75fo9e925v7yi

alter table if exists order_item_contact_info
drop constraint if exists FKdkqpewgye3ykp7ja6026sj8ch

alter table if exists order_item_contact_info
drop constraint if exists FKfpi19pjk52p9r2cammbfe9n98

alter table if exists order_quantity_item
drop constraint if exists FKpmf6b30yudoi52frolacv34ud

alter table if exists order_quantity_item
drop constraint if exists FK6odyl3q2hixxqk1md6c367rqt

alter table if exists payment
drop constraint if exists FKexl3xxffhku33dfeakcxb9oaq

alter table if exists payment
drop constraint if exists FKjii2n6db6cje3km5ybsbgo8cl

alter table if exists payment
drop constraint if exists FK6no43xuawlg1klytnke01u2pk

alter table if exists payment
drop constraint if exists FK41brulskee4m5ppls85o6go9r

alter table if exists payment
drop constraint if exists FKiay5v7ko98xmg8kmmbod9g5a8

alter table if exists payment
drop constraint if exists FKqgepwy3c7leg5lucaq5411msx

alter table if exists post
drop constraint if exists FKcujdyjmpscm8hpiv70fbafgdb

alter table if exists price
drop constraint if exists FKjufxmhfbykaua4p43u87udfqq

alter table if exists review
drop constraint if exists FKfoadt2illecegj32wlk5hau9p

alter table if exists review
drop constraint if exists FKonb8hnnh1sf9udkwu37e7olqq

alter table if exists store
drop constraint if exists FK11ufvbaca85vw4h3kak8tkm55

alter table if exists store
drop constraint if exists FKht5yqojt5c4yw21kiax7apcsd

alter table if exists user_details
drop constraint if exists FKt7ck1erxe5phbhrdk1cn92h52

alter table if exists user_details
drop constraint if exists FKbb0yxp4hid37u6wvl212sl9xq

alter table if exists user_details_credit_card
drop constraint if exists FK9cdt2l66ilcafej5ave1gvkga

alter table if exists user_details_credit_card
drop constraint if exists FK4ovk6akxkfli65wqegfx9t4ue

alter table if exists user_details_post
drop constraint if exists FKhj7tl66aevwcx7n8i387plk9q

alter table if exists user_details_post
drop constraint if exists FKfs87np3bixtnk9tggfenkfpjw

drop table if exists address cascade

drop table if exists category cascade
    
drop table if exists contact_info cascade
    
drop table if exists contact_phone cascade
    
drop table if exists count_type cascade
    
drop table if exists country cascade
    
drop table if exists country_phone_code cascade
    
drop table if exists credit_card cascade
    
drop table if exists currency cascade
    
drop table if exists currency_discount cascade
    
drop table if exists delivery cascade
    
drop table if exists delivery_type cascade
    
drop table if exists item cascade
    
drop table if exists item_data cascade
    
drop table if exists item_data_option cascade
    
drop table if exists item_data_quantity cascade
    
drop table if exists item_data_quantity_item_data cascade
    
drop table if exists item_post cascade
    
drop table if exists item_review cascade
    
drop table if exists item_status cascade
    
drop table if exists item_type cascade
    
drop table if exists message cascade
    
drop table if exists message_box cascade
    
drop table if exists message_box_message cascade
    
drop table if exists message_receivers cascade
    
drop table if exists message_status cascade
    
drop table if exists message_type cascade
    
drop table if exists option_group cascade
    
drop table if exists option_item cascade
    
drop table if exists order_item cascade
    
drop table if exists order_item_contact_info cascade
    
drop table if exists order_quantity_item cascade
    
drop table if exists order_status cascade
    
drop table if exists payment cascade
    
drop table if exists payment_method cascade
    
drop table if exists percent_discount cascade
    
drop table if exists post cascade
    
drop table if exists price cascade
    
drop table if exists rating cascade
    
drop table if exists review cascade
    
drop table if exists store cascade
    
drop table if exists user_details cascade
    
drop table if exists user_details_credit_card cascade
    
drop table if exists user_details_post cascade
    
create table address (
                         apartmentNumber integer not null,
                         buildingNumber integer not null,
                         florNumber integer not null,
                         country_id bigint,
                         id bigserial not null,
                         city varchar(255),
                         street varchar(255),
                         street2 varchar(255),
                         zipCode varchar(255),
                         primary key (id)
)

create table category (
                          category_id bigint,
                          id bigserial not null,
                          name varchar(255) not null,
                          primary key (id)
)
    
create table contact_info (
                              contact_phone_id bigint,
                              id bigserial not null,
                              name varchar(255),
                              secondName varchar(255),
                              uniqueContactInfoId varchar(255),
                              primary key (id)
)
    
create table contact_phone (
                               country_phone_code_id bigint,
                               id bigserial not null,
                               phoneNumber varchar(255),
                               primary key (id)
)
    
create table count_type (
                            id bigserial not null,
                            value varchar(255) not null,
                            primary key (id)
)
    
create table country (
                         id bigserial not null,
                         flagImagePath varchar(255),
                         value varchar(255),
                         primary key (id)
)
    
create table country_phone_code (
                                    id bigserial not null,
                                    code varchar(255),
                                    primary key (id)
)
    
create table credit_card (
                             isActive boolean not null,
                             id bigserial not null,
                             cardNumber varchar(255),
                             cvv varchar(255),
                             dateOfExpire varchar(255),
                             ownerName varchar(255),
                             ownerSecondName varchar(255),
                             primary key (id)
)
    
create table currency (
                          id bigserial not null,
                          value varchar(255) not null,
                          primary key (id)
)
    
create table currency_discount (
                                   amount float(53) not null,
                                   currency_id bigint,
                                   id bigserial not null,
                                   charSequenceCode varchar(255) not null unique,
                                   primary key (id)
)
    
create table delivery (
                          address_id bigint,
                          delivery_type_id bigint,
                          id bigserial not null,
                          primary key (id)
)
    
create table delivery_type (
                               id bigserial not null,
                               value varchar(255) not null,
                               primary key (id)
)
    
create table item (
                      id bigserial not null,
                      itemData_id bigint unique,
                      primary key (id)
)
    
create table item_data (
                           category_id bigint,
                           currencyDiscount_id bigint,
                           dateOfCreate bigint not null,
                           fullPrice_id bigint,
                           id bigserial not null,
                           itemType_id bigint,
                           percentDiscount_id bigint,
                           status_id bigint,
                           totalPrice_id bigint,
                           articularId varchar(255),
                           name varchar(255),
                           primary key (id)
)
    
create table item_data_option (
                                  itemdata_id bigint not null,
                                  optionitem_id bigint not null,
                                  primary key (itemdata_id, optionitem_id)
)
    
create table item_data_quantity (
                                    quantity integer not null,
                                    id bigserial not null,
                                    primary key (id)
)
    
create table item_data_quantity_item_data (
                                              item_data_id bigint not null,
                                              item_data_quantity_id bigint not null,
                                              primary key (item_data_id, item_data_quantity_id)
)
    
create table item_post (
                           item_id bigint not null,
                           post_id bigint not null,
                           primary key (item_id, post_id)
)
    
create table item_review (
                             item_id bigint not null,
                             review_id bigint not null,
                             primary key (item_id, review_id)
)
    
create table item_status (
                             id bigserial not null,
                             value varchar(255) not null,
                             primary key (id)
)
    
create table item_type (
                           id bigserial not null,
                           value varchar(255) not null,
                           primary key (id)
)
    
create table message (
                         dateOfSend bigint not null,
                         id bigserial not null,
                         status_id bigint,
                         type_id bigint,
                         message varchar(255),
                         messageUniqNumber varchar(255) not null unique,
                         sendSystem varchar(255),
                         sender varchar(255),
                         subscribe varchar(255),
                         title varchar(255),
                         primary key (id)
)
    
create table message_box (
                             id bigserial not null,
                             userdetails_id bigint unique,
                             primary key (id)
)
    
create table message_box_message (
                                     message_box_id bigint not null,
                                     message_id bigint not null,
                                     primary key (message_box_id, message_id)
)
    
create table message_receivers (
                                   message_id bigint not null,
                                   receiver_email varchar(255) not null
)
    
create table message_status (
                                id bigserial not null,
                                value varchar(255) not null,
                                primary key (id)
)
    
create table message_type (
                              id bigserial not null,
                              value varchar(255) not null,
                              primary key (id)
)
    
create table option_group (
                              id bigserial not null,
                              value varchar(255) not null,
                              primary key (id)
)
    
create table option_item (
                             id bigserial not null,
                             optionGroup_id bigint,
                             optionName varchar(255),
                             primary key (id)
)
    
create table order_item (
                            dateOfCreate bigint not null,
                            delivery_id bigint,
                            id bigserial not null,
                            orderStatus_id bigint,
                            payment_id bigint,
                            userdetails_id bigint,
                            note varchar(255),
                            order_id varchar(255) not null unique,
                            primary key (id)
)
    
create table order_item_contact_info (
                                         OrderItem_id bigint not null,
                                         beneficiaries_id bigint not null unique
)
    
create table order_quantity_item (
                                     item_quantity_id bigint not null,
                                     order_item_id bigint not null,
                                     primary key (item_quantity_id, order_item_id)
)
    
create table order_status (
                              id bigserial not null,
                              value varchar(255) not null,
                              primary key (id)
)
    
create table payment (
                         credit_card_id bigint,
                         currencyDiscount_id bigint,
                         fullprice_id bigint,
                         id bigserial not null,
                         payment_method_id bigint,
                         percentDiscount_id bigint,
                         totalprice_id bigint,
                         paymentId varchar(255),
                         primary key (id)
)
    
create table payment_method (
                                id bigserial not null,
                                method varchar(255),
                                primary key (id)
)
    
create table percent_discount (
                                  amount float(53) not null,
                                  isPercentage boolean not null,
                                  id bigserial not null,
                                  charSequenceCode varchar(255) not null unique,
                                  primary key (id)
)
    
create table post (
                      dateOfCreate bigint not null,
                      id bigserial not null,
                      post_id bigint,
                      authorEmail varchar(255),
                      authorUserName varchar(255),
                      message varchar(255),
                      title varchar(255),
                      uniquePostId varchar(255),
                      primary key (id)
)
    
create table price (
                       amount float(53) not null,
                       currency_id bigint,
                       id bigserial not null,
                       primary key (id)
)
    
create table rating (
                        value integer not null,
                        id bigserial not null,
                        primary key (id)
)
    
create table review (
                        dateOfCreate bigint not null,
                        id bigserial not null,
                        rating_id bigint,
                        userdetails_id bigint unique,
                        message varchar(255),
                        title varchar(255),
                        primary key (id)
)
    
create table store (
                       count integer not null,
                       count_type bigint,
                       id bigserial not null,
                       option_item bigint unique,
                       primary key (id)
)
    
create table user_details (
                              isActive boolean not null,
                              address_id bigint unique,
                              contactInfo_id bigint unique,
                              dateOfCreate bigint not null,
                              id bigserial not null,
                              email varchar(255),
                              username varchar(255),
                              primary key (id)
)
    
create table user_details_credit_card (
                                          userdetails_id bigint not null,
                                          creditCardList_id bigint not null unique
)
    
create table user_details_post (
                                   userdetails_id bigint not null,
                                   postList_id bigint not null unique
)
    
alter table if exists address
    add constraint FKe54x81nmccsk5569hsjg1a6ka
    foreign key (country_id)
    references country
    
alter table if exists category
    add constraint FKap0cnk1255oj4bwam7in1hxxv
    foreign key (category_id)
    references category
    
alter table if exists contact_info
    add constraint FKbbcsefxe8ax16j1dndb9l7mms
    foreign key (contact_phone_id)
    references contact_phone
    
alter table if exists contact_phone
    add constraint FKbvstb62j7buq9fxiaefcfxfnd
    foreign key (country_phone_code_id)
    references country_phone_code
    
alter table if exists currency_discount
    add constraint FK4c47cfya2tgceha3msekcmc7d
    foreign key (currency_id)
    references currency
    
alter table if exists delivery
    add constraint FKlayueon76a86wr2gbsyannaf7
    foreign key (address_id)
    references address
    
alter table if exists delivery
    add constraint FKiu02keoyiv4xhc28rsi86mhll
    foreign key (delivery_type_id)
    references delivery_type
    
alter table if exists item
    add constraint FK5pc91oc43bq6tqaugwvc8f4or
    foreign key (itemData_id)
    references item_data
    
alter table if exists item_data
    add constraint FKnp91gprwy000sgedoo83oymji
    foreign key (category_id)
    references category
    
alter table if exists item_data
    add constraint FK1jvdlvc3rfvki7v5jus0m19s3
    foreign key (currencyDiscount_id)
    references currency_discount
    
alter table if exists item_data
    add constraint FKcipiyxs05i6ek9hiwhscny7c0
    foreign key (fullPrice_id)
    references price
    
alter table if exists item_data
    add constraint FKdiwrprwyudwa44upjvl8k87h9
    foreign key (itemType_id)
    references item_type
    
alter table if exists item_data
    add constraint FK810alvugy52y6s9no5oppb26j
    foreign key (percentDiscount_id)
    references percent_discount
    
alter table if exists item_data
    add constraint FKiksl8lnvms0xfx27yoo9fj02p
    foreign key (status_id)
    references item_status
    
alter table if exists item_data
    add constraint FKimkmn00n05kk4ap3ektej3itd
    foreign key (totalPrice_id)
    references price
    
alter table if exists item_data_option
    add constraint FKahke2r2nw7hvi4q3426oju9xw
    foreign key (optionitem_id)
    references option_item
    
alter table if exists item_data_option
    add constraint FKcvvtx9qekfpisvrgqj136tft1
    foreign key (itemdata_id)
    references item_data
    
alter table if exists item_data_quantity_item_data
    add constraint FK5r4ejgb9q1cwdjt6af4eusa6i
    foreign key (item_data_id)
    references item_data
    
alter table if exists item_data_quantity_item_data
    add constraint FKmokvmesmv3cxly9a5l6rux7pa
    foreign key (item_data_quantity_id)
    references item_data_quantity
    
alter table if exists item_post
    add constraint FK1grghtm8o53yfmfixtu97em69
    foreign key (post_id)
    references post
    
alter table if exists item_post
    add constraint FKhld6fk2skal2v9mk7na8qv9uf
    foreign key (item_id)
    references item
    
alter table if exists item_review
    add constraint FKewycb8h8ogg4ssiwd5v64sbgm
    foreign key (review_id)
    references review
    
alter table if exists item_review
    add constraint FKdk8811562blj2cqlok7cknd70
    foreign key (item_id)
    references item
    
alter table if exists message
    add constraint FKc1vhu79usc0hfr9gp6fuu3ah5
    foreign key (status_id)
    references message_status
    
alter table if exists message
    add constraint FK3kx251aos03ltpyc1dxofsrck
    foreign key (type_id)
    references message_type
    
alter table if exists message_box
    add constraint FKl5lntrj6p83iu21a4eno38vy0
    foreign key (userdetails_id)
    references user_details
    
alter table if exists message_box_message
    add constraint FK7n91muashe5nwvufuaxicvrux
    foreign key (message_id)
    references message
    
alter table if exists message_box_message
    add constraint FKss2bsrmbp3w9d2guos7y0amdh
    foreign key (message_box_id)
    references message_box
    
alter table if exists message_receivers
    add constraint FKsfmc2unubrcf9ivflgn3nrc83
    foreign key (message_id)
    references message
    
alter table if exists option_item
    add constraint FKi8a7hi02dpehhjq7c2qwh5sai
    foreign key (optionGroup_id)
    references option_group
    
alter table if exists order_item
    add constraint FKbx40hdv2o0kdjt1xcbefjcl2h
    foreign key (delivery_id)
    references delivery
    
alter table if exists order_item
    add constraint FKdaf9qb7qp4puef6sglu8us441
    foreign key (orderStatus_id)
    references order_status
    
alter table if exists order_item
    add constraint FK2vebnru38ddo0wk30i9ybhx8c
    foreign key (payment_id)
    references payment
    
alter table if exists order_item
    add constraint FKl4ignmwea39w75fo9e925v7yi
    foreign key (userdetails_id)
    references user_details
    
alter table if exists order_item_contact_info
    add constraint FKdkqpewgye3ykp7ja6026sj8ch
    foreign key (beneficiaries_id)
    references contact_info
    
alter table if exists order_item_contact_info
    add constraint FKfpi19pjk52p9r2cammbfe9n98
    foreign key (OrderItem_id)
    references order_item
    
alter table if exists order_quantity_item
    add constraint FKpmf6b30yudoi52frolacv34ud
    foreign key (item_quantity_id)
    references item_data_quantity
    
alter table if exists order_quantity_item
    add constraint FK6odyl3q2hixxqk1md6c367rqt
    foreign key (order_item_id)
    references order_item
    
alter table if exists payment
    add constraint FKexl3xxffhku33dfeakcxb9oaq
    foreign key (fullprice_id)
    references price
    
alter table if exists payment
    add constraint FKjii2n6db6cje3km5ybsbgo8cl
    foreign key (payment_method_id)
    references payment_method
    
alter table if exists payment
    add constraint FK6no43xuawlg1klytnke01u2pk
    foreign key (totalprice_id)
    references price
    
alter table if exists payment
    add constraint FK41brulskee4m5ppls85o6go9r
    foreign key (credit_card_id)
    references credit_card
    
alter table if exists payment
    add constraint FKiay5v7ko98xmg8kmmbod9g5a8
    foreign key (currencyDiscount_id)
    references currency_discount
    
alter table if exists payment
    add constraint FKqgepwy3c7leg5lucaq5411msx
    foreign key (percentDiscount_id)
    references percent_discount
    
alter table if exists post
    add constraint FKcujdyjmpscm8hpiv70fbafgdb
    foreign key (post_id)
    references post
    
alter table if exists price
    add constraint FKjufxmhfbykaua4p43u87udfqq
    foreign key (currency_id)
    references currency
    
alter table if exists review
    add constraint FKfoadt2illecegj32wlk5hau9p
    foreign key (rating_id)
    references rating
    
alter table if exists review
    add constraint FKonb8hnnh1sf9udkwu37e7olqq
    foreign key (userdetails_id)
    references user_details
    
alter table if exists store
    add constraint FK11ufvbaca85vw4h3kak8tkm55
    foreign key (count_type)
    references count_type
    
alter table if exists store
    add constraint FKht5yqojt5c4yw21kiax7apcsd
    foreign key (option_item)
    references option_item
    
alter table if exists user_details
    add constraint FKt7ck1erxe5phbhrdk1cn92h52
    foreign key (address_id)
    references address
    
alter table if exists user_details
    add constraint FKbb0yxp4hid37u6wvl212sl9xq
    foreign key (contactInfo_id)
    references contact_info
    
alter table if exists user_details_credit_card
    add constraint FK9cdt2l66ilcafej5ave1gvkga
    foreign key (creditCardList_id)
    references credit_card
    
alter table if exists user_details_credit_card
    add constraint FK4ovk6akxkfli65wqegfx9t4ue
    foreign key (userdetails_id)
    references user_details
    
alter table if exists user_details_post
    add constraint FKhj7tl66aevwcx7n8i387plk9q
    foreign key (postList_id)
    references post
    
alter table if exists user_details_post
    add constraint FKfs87np3bixtnk9tggfenkfpjw
    foreign key (userdetails_id)
    references user_details