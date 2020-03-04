# EasyMall System Design



### Content

- Requirement Analysis
  - Requirements
  - Using Techniques
- System Design
  - Function Module
  - Database Design
  - Interfaces Design
- Code Implementation
  - Implementation Backend
    - Build Project
    - Integrate Swagger
    - Developing Crawler with Apache Nutch
  - Implementation Frontends
- Test & Deployment
  - CD/CI
- Optimization Function
  - WeChat Notify.



### Main



### Requirement Analysis

#### Requirements

Project Requirements

- Frontend and backend splitting
- Automatic database initialization

Page Requirements

- Home Page
- User Center
- Shopping Cart

Function Requirements

- User
  - User Register
  - User Login
  - Basic Info management
  - Address management
- Goods
  - Display Category
  - Search
- Trade
  - Shopping Cart management
  - Order management

#### Using Technologies

- Backend
  - Web Development
    - Web Framework
      - Spring Boot
      - Hibernate-Validator
    - Persistence
      - MyBatis
      - MyBatis Plus
      - Druid
    - Cache
      - Ehcahe
      - Spring Boot Redis
    - API
      - Swagger UI
      - RESTful
    - Security
      - Shiro
      - JWT
    - Single Sign-on
      - Spring Boot and OAuth2
    - Logging
      - SLF4j
      - Log4j
    - Template
      - Thymeleaf
    - Tools
      - Lombok
      - Fastjson
      - Spring Boot Devtools
      - Apache Commons (commons-lang3, commons-io, commons-codec)
  - Test
    - JUnit
  - Deploy
    - Tomcat
    - Maven
    - Nginx
    - Docker
- Frontend
  - Vue.js
  - Vuex
  - Vue Router
  - Element UI
  - ES6
  - webpack
  - axios
  - Node.js

Distributed

- Dubbo: Service Middleware

- MyCat: Database Middleware

- Zookeeper: Centralized Service

- ElasticSearch: Search Engine

- ActiveMQ: Message Queue

### System Design

#### Function Module

- EasyMall Front
  - Signin & Signup
    - Third Platform
    - Verification code
  - Home Page
    - Category
    - Product List
  - Search Product list Page
  - Product Detail Page
  - Shopping Cart Page
  - User Center Page
    - User Addresses
    - User Orders
- EasyMall API
  - Signin & Signup
  - category
  - product
    - product_detail
    - product_review
  - shoppingcart
  - user
    - user_address
    - user_order
    - (user_follow)
    - (user_coupon)
- EasyMall Management
  - Every Entity CRUD

#### E-R

- Category
- Product
  - Review
- Shopping Cart
- User
  - Address
  - Follow
- Order



#### Database Design

Common

| Name        | Type      | Length | NULL     | Default | Key  | Description |
| ----------- | --------- | ------ | -------- | ------- | ---- | ----------- |
| id          | varchar   | 64     | not null |         |      |             |
| is_delete   | bool      | 1      | not null |         |      |             |
| create_time | timestamp |        | null     |         |      |             |
| modify_time | timestamp |        | null     |         |      |             |
| creator_id  | varchar   |        | null     |         |      |             |
| modifier_id | varchar   |        | null     |         |      |             |

t_product

| Name        | Type    | Length | NULL     | Default | Key  | Description      |
| ----------- | ------- | ------ | -------- | ------- | ---- | ---------------- |
| id          | varchar | 64     | not null |         | P    |                  |
| name        | varchar | 128    | not null |         |      |                  |
| desc        | varchar | 256    | null     |         |      |                  |
| price       | decimal | (18,2) | not null |         |      |                  |
| category_id | varchar | 64     | null     |         | F    | product category |

t_attachment

| Name        | Type      | Length | NULL     | Default | Key  | Description |
| ----------- | --------- | ------ | -------- | ------- | ---- | ----------- |
| id          | varchar   | 64     | not null |         | P    |             |
| file_name   | varchar   | 128    | not null |         |      |             |
| url         | varchar   | 256    | not null |         |      |             |
| create_time | timestamp |        | not null | now()   |      |             |

t_product_attachment

| Name          | Type    | Length | NULL     | Default | Key  | Description |
| ------------- | ------- | ------ | -------- | ------- | ---- | ----------- |
| id            | varchar | 64     | not null |         | P    |             |
| product_id    | varchar | 64     | not null |         |      |             |
| attachment_id | varchar | 64     | not null |         |      |             |

t_product_review

| Name        | Type      | Length | NULL     | Default | Key  | Description |
| ----------- | --------- | ------ | -------- | ------- | ---- | ----------- |
| id          | varchar   | 64     | not null |         | P    |             |
| product_id  | varchar   | 64     | not null |         | F    |             |
| user_id     | varchar   | 64     | not null |         | F    |             |
| title       | varchar   | 64     | not null |         |      |             |
| content     | varchar   | 1024   | null     |         |      |             |
| create_time | timestamp |        | not null |         |      |             |

t_product_category

| Name      | Type    | Length | NULL     | Default | Key  | Description |
| --------- | ------- | ------ | -------- | ------- | ---- | ----------- |
| id        | varchar | 64     | not null |         | P    |             |
| name      | varchar | 64     | not null |         |      |             |
| sort_num  | int     | 8      | not null |         |      |             |
| parent_id | varchar | 64     | not null |         | F    |             |

t_shopping_cart

| Name       | Type    | Length | NULL     | Default | Key  | Description |
| ---------- | ------- | ------ | -------- | ------- | ---- | ----------- |
| id         | varchar | 64     | not null |         | P    |             |
| product_id | varchar | 64     | not null |         | F    |             |
| count      | int     | 8      | not null |         |      |             |
| subtotal   | decimal | (18,2) | not null |         |      |             |
| sort_num   | int     | 8      | null     |         |      |             |
| selected   | bool    | 1      | not null |         |      |             |

t_order

| Name            | Type      | Length | NULL     | Default | Key  | Description |
| --------------- | --------- | ------ | -------- | ------- | ---- | ----------- |
| id              | varchar   | 64     | not null |         | P    |             |
| order_no        | varchar   | 64     | not null |         |      |             |
| status          | varchar   | 64     | not null |         |      |             |
| pay_method      | varchar   | 32     | not null |         |      |             |
| products_amount | decimal   | (18,2) | not null |         |      |             |
| express_fee     | decimal   | (18,2) | not null |         |      |             |
| total_amount    | decimal   | (18,2) | not null |         |      |             |
| user_id         | varchar   | 64     | not null |         | F    |             |
| create_time     | timestamp |        | not null |         |      |             |
| pay_time        | timestamp |        | not null |         |      |             |
| close_time      | timestamp |        | not null |         |      |             |

t_user

| Name          | Type    | Length | NULL     | Default | Key  | Description |
| ------------- | ------- | ------ | -------- | ------- | ---- | ----------- |
| id            | varchar | 64     | not null |         | P    |             |
| username      | varchar | 64     | not null |         |      |             |
| password      | varchar | 64     | not null |         |      |             |
| mobile        | int     | 16     | not null |         |      |             |
| nickname      | varchar | 64     | not null |         |      |             |
| gender        | varchar | 8      | null     |         |      |             |
| birthdate     | varchar | 32     | null     |         |      |             |
| real_name     | varchar | 64     | null     |         |      |             |
| identity_type | varchar | 32     | null     |         |      |             |
| identity_id   | varchar | 64     | null     |         |      |             |
| email         | varchar | 64     | null     |         |      |             |
| avatar_url    | varchar | 256    | null     |         |      |             |

t_user_address

| Name           | Type    | Length | NULL     | Default | Key  | Description |
| -------------- | ------- | ------ | -------- | ------- | ---- | ----------- |
| id             | varchar | 64     | not null |         | P    |             |
| nickname       | varchar | 64     | null     |         |      |             |
| receiver_name  | varchar | 64     | not null |         |      |             |
| area_address   | varchar | 64     | not null |         |      |             |
| detail_address | varchar | 64     | not null |         |      |             |
| postcode       | int     | 16     | not null |         |      |             |
| mobile         | int     | 16     | not null |         |      |             |
| telephone      | varchar | 16     | null     |         |      |             |
| email          | varchar | 64     | null     |         |      |             |



#### Interfaces Design

Signin or Signup Page

- **/signin**

  - Method: POST
  - Data Type: JSON

  - Parameters: 

    | Name     | Required | Data Type | Description           |
    | -------- | -------- | --------- | --------------------- |
    | account  | required | string    | email/username/mobile |
    | password | required | string    |                       |

  - Result:

    ```
    {
        ret_code: 0,
        ret_msg: "success",
        access_token: ""
    }
    ```

- **/signup**

  - Method: POST
  - Data Type: JSON

  - Parameters: 

    | Name     | Required | Data Type | Description                          |
    | -------- | -------- | --------- | ------------------------------------ |
    | username | required | string    |                                      |
    | mobile   | required | string    |                                      |
    | password | required | string    | 6-10 letters, numbers or characters. |
    | email    | optional | string    |                                      |

  - Result

    ```
    {
        ret_code: 0,
        ret_msg: "success"
    }
    ```

Home Page

- /home/categories

  - Method: GET
  - Data Type: JSON

  - Parameters: 

  - Result:

    ```json
    {
        ret_code: 0,
        ret_msg: "success"
        data:{
        	categories:[{
            	name: "", 
        		url: "",
        		sort_num: 
        	},
        	...
        	]
        }
    }
    ```

- /home/products

  - Method: GET
  - Data Type: JSON

  - Parameters: 

  - Result:

    ```json
    {
        ret_code: 0,
        ret_msg: "success"
        data:{
        	carousel_list:[{
            	name: "", 
        		url: "",
        		sort_num: 
        	},
        	...
        	]，
        	product_list_arr:[{
        		id: "",
        		list_name: "",
        		list_data:[{
        			id: "",
        			name: "",
        			url:""
        		},
        		...
        		]
        	},
        	...
        	]
        }
    }
    ```

Products list Page

- /categories/{id}/products

  - Method: GET
  - Data Type: JSON

  - Parameters: 

  - Result:

    ```
    {
        ret_code: 0,
        ret_msg: "success"
        data:{
        	product_list:[{
        		id: "",
            	name: "", 
        		url: "",
        		sort_num: 
        	},
        	...
        	]
        }
    }
    ```

- /search?{params}

  - Method: GET
  - Data Type: JSON

  - Parameters: 

  - Result:

    ```
    {
        ret_code: 0,
        ret_msg: "success"
        data:{
        	product_list:[{
        		id: "",
            	name: "", 
        		url: "",
        		sort_num: 
        	},
        	...
        	]
        }
    }
    ```

Product Detail Page

- /products/{id}

  - Method: GET
  - Data Type: JSON

  - Parameters: 

  - Result:

    ```
    {
        ret_code: 0,
        ret_msg: "success"
        data:{
        	product_info:{
        		id: "",
            	name: "",
            	desc: "",
            	price: 
        	}
        }
    }
    ```

- /products/{id}/desc

- /products/{id}/reviews

- /products/{id}/recommends

Shopping Cart Page

- /shoppingcarts

  - Method: GET
  - Data Type: JSON

  - Parameters: 

  - Result:

    ```
    {
        ret_code: 0,
        ret_msg: "success"
        data:{
        	shopping_cart:{
        		id: "",
        		total: ,
                list:[{
                    id: "",
                    url: "",
                    count: "",
                    selected: ,
                    subtotal: ,
                    sort_num: ,
                    product_info:{
                        id: "",
                        name: "",
                        price: 
                    }
                },
                ...
                ]
        }
    }
    ```

User Center Page

- /users/{id}
- /users/{id}/addresses
- /users/{id}/addresses/{id}

- /users/{id}/orders
- /users/{id}/orders/{id}

### Code Implementation

#### Project Build

Project Organization

Maven Project

- easy-mall-parent
- easy-mall-common
- easy-mall-rest
- easy-mall-admin

Vue Project

Project Directory Structure



### References

- 大型网站系统与Java中间件实践 by 曾宪杰

- Management Systems
  - [Guns](https://github.com/stylefeng/Guns)
  - [spring-boot-plus](https://github.com/geekidea/spring-boot-plus)
  - [hope-boot](https://github.com/hope-for/hope-boot)
  - [BootDo](https://github.com/lcg0124/bootdo)

- Mall Systems
  - [yii2_fecshop](https://github.com/fecshop/yii2_fecshop)
  - [gpmall](https://github.com/2227324689/gpmall)
  - [mall](https://github.com/macrozheng/mall)
  - [xmall](https://github.com/Exrick/xmall)

- Frontend
  - [xmall-front](https://github.com/Exrick/xmall-front)
  - [ecommerce-netlify](https://github.com/sdras/ecommerce-netlify)

  