--
--  Copyright 2009 Igor Azarnyi, Denys Pavlov
--
--     Licensed under the Apache License, Version 2.0 (the "License");
--     you may not use this file except in compliance with the License.
--     You may obtain a copy of the License at
--
--         http://www.apache.org/licenses/LICENSE-2.0
--
--     Unless required by applicable law or agreed to in writing, software
--     distributed under the License is distributed on an "AS IS" BASIS,
--     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--     See the License for the specific language governing permissions and
--     limitations under the License.
--

--
-- This script is for MySQL only with some Derby hints inline with comments
-- We highly recommend you seek YC's support help when upgrading your system
-- for detailed analysis of your code.
--
-- Upgrades organised in blocks representing JIRA tasks for which they are
-- necessary - potentially you may hand pick the upgrades you required but
-- to keep upgrade process as easy as possible for future we recommend full
-- upgrades
--

--
-- YC-410 Provide centralised storage for ShoppingCart as opposed to cookie
--



    create table TSHOPPINGCARTSTATE (
        TSHOPPINGCARTSTATE_ID bigint not null auto_increment,
        VERSION bigint not null default 0,
        CREATED_TIMESTAMP datetime,
        UPDATED_TIMESTAMP datetime,
        CREATED_BY varchar(64),
        UPDATED_BY varchar(64),
        GUID varchar(36) not null unique,
        CART_STATE varbinary(16384),
        CUSTOMER_EMAIL varchar(255),
        primary key (TSHOPPINGCARTSTATE_ID)
    );

--
--     create table TSHOPPINGCARTSTATE (
--         TSHOPPINGCARTSTATE_ID bigint not null GENERATED BY DEFAULT AS IDENTITY,
--         VERSION bigint not null default 0,
--         CREATED_TIMESTAMP timestamp,
--         UPDATED_TIMESTAMP timestamp,
--         CREATED_BY varchar(64),
--         UPDATED_BY varchar(64),
--         GUID varchar(36) not null unique,
--         CART_STATE blob(16384),
--         CUSTOMER_EMAIL varchar(255),
--         primary key (TSHOPPINGCARTSTATE_ID)
--     );


    create index PROMOTIONCOUPONUSAGE_EMAIL on TPROMOTIONCOUPONUSAGE (CUSTOMER_EMAIL);

    create index SHOPPINGCARTSTATE_EMAIL on TSHOPPINGCARTSTATE (CUSTOMER_EMAIL);


--
-- YC-150 RemoteImageServiceImpl addImageToRepository path resolution review
-- YC-237 Image vault resolution
--

UPDATE TSYSTEMATTRVALUE SET VAL = 'context://../imagevault' WHERE CODE = 'SYSTEM_IMAGE_VAULT';
ALTER TABLE TSHOP DROP COLUMN IMGVAULT;

--
-- YC-423 Add new property to ManagerEntity to allow selection of Shop's manager has access to
--

    create table TMANAGERSHOP (
        MANAGERSHOP_ID bigint not null auto_increment,
        VERSION bigint not null default 0,
        MANAGER_ID bigint not null,
        SHOP_ID bigint not null,
        CREATED_TIMESTAMP datetime,
        UPDATED_TIMESTAMP datetime,
        CREATED_BY varchar(64),
        UPDATED_BY varchar(64),
        GUID varchar(36) not null unique,
        primary key (MANAGERSHOP_ID)
    ) ;


    alter table TMANAGERSHOP
        add index FK_MS_SHOP (SHOP_ID),
        add constraint FK_MS_SHOP
        foreign key (SHOP_ID)
        references TSHOP (SHOP_ID);

    alter table TMANAGERSHOP
        add index FK_MS_MANAGER (MANAGER_ID),
        add constraint FK_MS_MANAGER
        foreign key (MANAGER_ID)
        references TMANAGER (MANAGER_ID)         on delete cascade;


--     create  table TMANAGERSHOP (
--         MANAGERSHOP_ID bigint not null GENERATED BY DEFAULT AS IDENTITY,
--         VERSION bigint not null DEFAULT 0,
--         MANAGER_ID bigint not null,
--         SHOP_ID bigint not null,
--         CREATED_TIMESTAMP timestamp,
--         UPDATED_TIMESTAMP timestamp,
--         CREATED_BY varchar(64),
--         UPDATED_BY varchar(64),
--         GUID varchar(36) not null unique,
--         primary key (MANAGERSHOP_ID)
--     );
--
--
--     alter table TMANAGERSHOP
--         add constraint FK_MS_SHOP
--         foreign key (SHOP_ID)
--         references TSHOP;
--
--
--     alter table TMANAGERSHOP
--         add constraint FK_MS_MANAGER
--         foreign key (MANAGER_ID)
--         references TMANAGER         on delete cascade;

--
--  YC-395 Federated environment for YUM
--

INSERT INTO TROLE (ROLE_ID, GUID, CODE, DESCRIPTION) VALUES (5, 'ROLE_SMCONTENTADMIN',  'ROLE_SMCONTENTADMIN', 'Content manager');
INSERT INTO TROLE (ROLE_ID, GUID, CODE, DESCRIPTION) VALUES (6, 'ROLE_SMMARKETINGADMIN',  'ROLE_SMMARKETINGADMIN', 'Marketing manager');
