# BlogAPI
<br/>
<p align="center">
  <a href="https://github.com/AhmetAksunger/BlogAPI">
    <img src="https://i.hizliresim.com/m50ia26.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Blog API</h3>

  <p align="center">
    <a href="https://github.com/AhmetAksunger/BlogAPI"><strong>Explore the docs »</strong></a>
    <br/>
    <br/>
    <a href="https://github.com/AhmetAksunger/BlogAPI">View Demo</a>
    .
    <a href="https://github.com/AhmetAksunger/BlogAPI/issues">Report Bug</a>
    .
    <a href="https://github.com/AhmetAksunger/BlogAPI/issues">Request Feature</a>
  </p>
</p>

## About The Project

![Screen Shot](https://i.hizliresim.com/ctvwq8q.png)

Welcome to the Blog API!

BlogAPI was built with Java Spring, and it provides an easy-to-use solution for managing blog posts and comments. With authentication and authorization features, you can be sure that only authorized users can access the data they are permitted to.

The API includes 5 database tables: blogs, comments, users, user_role_junction, and roles. It has a lot of endpoints where you can create, read, update, and delete blog posts, comments, users, roles and more

## API Endpoint Table

### Authentication API Endpoints
|HTTP Method|URL Path|Status Code|Description|
|:-----:|:-----:|:-----:|:-----:|
| POST | /auth/login | 200 (OK) | Login |
| POST | /auth/register | 201 (Created) | Register |


### Blog API Endpoints
|HTTP Method|URL Path|Status Code|Description|
|:-----:|:-----:|:-----:|:-----:|
| POST | /api/blogs/ | 201 (Created) | Create a blog |
| PUT | /api/blogs/{blogId} | 200 (OK) | Update a blog |
| DELETE | /api/blogs/{blogId} | 200 (OK) | Delete a blog |
| GET | /api/blogs/ | 200 (OK) | Get all blogs |
| GET | /api/blogs/?orderBy={title, <br> createdAt, updatedAt} | 200 (OK) | Order blogs by title, <br> createdAt or updatedAt |
| GET | /api/blogs/{blogId} | 200 (OK) | Get blog by id |
| GET | /api/blogs/search?title=titlehere | 200 (OK) | Search blogs by title |

### Comment API Endpoints
|HTTP Method|URL Path|Status Code|Description|
|:-----:|:-----:|:-----:|:-----:|
| POST | /api/blogs/{blogId}/comments/ | 201 (Created) | Create a comment |
| PUT | /api/blogs/0/comments/{commentId} | 200 (OK) | Update a comment |
| DELETE | /api/blogs/0/comments/{commentId} | 200 (OK) | Delete a comment |
| GET | /api/blogs/{blogId}/comments/ | 200 (OK) | Get blogs comments |

### User API Endpoints
|HTTP Method|URL Path|Status Code|Description|
|:-----:|:-----:|:-----:|:-----:|
| GET | /api/users/my-profile | 200 (OK) | Get your profile |
| GET | /api/users/my-blogs | 200 (OK) | Get your blogs |
| GET | /api/users/my-comments | 200 (OK) | Get your comments |

## Usage of the API Endpoints

## Authentication Endpoints
### Login
**Request**
```http
POST /auth/login
Content-Type: application/json
```
```json
{
    "username": "ahmet",
    "password": "1234",
}
```
**Response**
```json
{
    "jwt": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWhtZXQiLCJpYXQiOjE2ODI4OTc0NzUsInJvbGVzIjoiVVNFUiJ9.teJzl47XjJqPJAU19Lhf8CrQOS2L1x11DPnilFqA1JKsiRwqVumAUZT7s89WPSm5-Cky5A0lG_u5BnB-Do1ROoqXkjK0MwIO3Hu9SFMusbcCD0D78sKiF3JMQHUU6RuJeKcSx5eVmqqVyPiw-UyqbiWJog2Y6JDsJyMxVwbHKBgh20RpCGtXf-hpDtpITbMJaQO01mY_FYNTu197wE8PXgsMoUv6Jjl_r7WIcFAoZzDnRF5VJ3niZVsncGRjqn9uQAPfWCrVgIa2f-VEpr2CveWDilKepOrboz3ypTBskmpNE_Av8aJwvu4DqePuqX-n_3n6DGv1phTvYjM_V0MdqA"
}
```

### Register
**Request**
```http
POST /auth/register
Content-Type: application/json
```
```json
{
    "username":"johndoe",
    "password":"12345",
    "email":"johndoe@gmail.com",
    "age":33
}
```
**Response**
```json
{
    "id": 7,
    "username": "johndoe",
    "password": "$2a$10$02CiV8kQn3n5Pzmb2zxdBepC0/LIoUxHtUk/cHD364s4hL37NWjbS",
    "email": "johndoe@gmail.com",
    "age": 33,
    "authorities": [
        {
            "id": 2,
            "authority": "USER"
        }
    ],
    "blogs": null,
    "comments": null,
    "enabled": true,
    "accountNonExpired": true,
    "credentialsNonExpired": true,
    "accountNonLocked": true
}
```
## Blog Endpoints
### Create Blog
**Request**
```http
POST /api/blogs/
Content-Type: application/json
Authorization: Bearer <token>
```
```json
{
"title": "blog title",
"body": "blog content"
}
```
**Response**
```
Created successfully
```
### Update Blog
**Request**
```http
PUT /api/blogs/{blogId}
Content-Type: application/json
Authorization: Bearer <token>
```
```json
{
"title": "updated blog title",
"body": "updated blog content"
}
```
**Response**
```json
{
"title": "updated blog title",
"body": "updated blog content"
}
```
### Delete Blog
**Request**
```http
DELETE /api/blogs/{blogId}
Content-Type: application/json
Authorization: Bearer <token>
```
**Response**
```
Deleted successfully
```
### Get All Blogs
```http
GET /api/blogs/?orderBy=title (or createdAt or updatedAt)
Content-Type: application/json
Authorization: Bearer <token>
```
**Response**
```json
[
    {
        "title": "Ahmet's Blog",
        "body": "Hey, this is my blog about Log4j2. Log4j2 is a logging framework that is mostly used in back-end services. It's the most popular logging framework. Last year, unfortunately there was a critical loop-hole was found in this framework that led all of companies to double-check and re-build their logging systems.",
        "username": "ahmet",
        "commentCount": 2,
        "createdAt": "2023-04-29T14:50:12.655+00:00"
    },
    {
        "title": "John's Lorem Blog",
        "body": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec auctor velit vel enim volutpat, quis rhoncus metus tempor. Integer imperdiet lacus eu augue imperdiet ultricies. Fusce sit amet lorem non massa pharetra rutrum. Sed commodo urna vitae mauris tincidunt, et congue dolor commodo. Sed eget bibendum neque. Sed nec dui augue. Nunc finibus hendrerit velit, eu commodo magna ultricies vel. Aliquam vel nisl enim. Vivamus at enim commodo, vulputate quam eget, cursus justo.",
        "username": "john",
        "commentCount": 1,
        "createdAt": "2023-04-30T16:34:54.336+00:00"
    },
    {
        "title": "My Thoughts on the Importance of Reading",
        "body": "Reading is one of the most important activities that a person can engage in. It allows us to learn about new topics and ideas, develop our critical thinking skills, and expand our imaginations. Whether you enjoy fiction or non-fiction, there is something for everyone in the world of books. So take some time today to curl up with a good book and see where it takes you!",
        "username": "mark12",
        "commentCount": 3,
        "createdAt": "2023-04-30T21:33:32.582+00:00"
    },
    {
        "title": "The Power of Practicing Gratitude",
        "body": "Practicing gratitude is a simple but powerful way to improve your mental health and overall well-being. By focusing on the good things in your life, you can increase feelings of happiness and satisfaction, reduce symptoms of anxiety and depression, and even improve physical health outcomes. Whether you keep a daily gratitude journal or simply take a few moments each day to reflect on the things you're thankful for, incorporating gratitude into your routine can have a big impact on your life.",
        "username": "admin",
        "commentCount": 1,
        "createdAt": "2023-04-29T14:42:41.628+00:00"
    }
]
```
### Get Blog By Id
```http
GET /api/blogs/{blogId}
Content-Type: application/json
Authorization: Bearer <token>
```
**Response**
```json
{
    "title": "My Thoughts on the Importance of Reading",
    "body": "Reading is one of the most important activities that a person can engage in. It allows us to learn about new topics and ideas, develop our critical thinking skills, and expand our imaginations. Whether you enjoy fiction or non-fiction, there is something for everyone in the world of books. So take some time today to curl up with a good book and see where it takes you!",
    "username": "mark12",
    "createdAt": "2023-04-30T21:33:32.582+00:00",
    "updatedAt": null,
    "comments": [
        {
            "body": "I really enjoyed reading your blog post. Your writing style is engaging and you make some great points about the importance of staying active. Keep up the good work!",
            "username": "ahmet",
            "createdAt": "2023-04-30T21:33:32.582+00:00"
        },
        {
            "body": "I really enjoyed reading your thoughts on this topic, Mark. You have a unique perspective that I find refreshing.",
            "username": "admin",
            "createdAt": "2023-04-30T21:33:32.582+00:00"
        },
        {
            "body": "Great post, Mark! Your writing always inspires me to think more deeply about the world around us!",
            "username": "john",
            "createdAt": "2023-04-30T21:33:32.582+00:00"
        }
    ]
}
```

### Search Blogs By Title
```http
GET /api/blogs/search?title=reading
Content-Type: application/json
Authorization: Bearer <token>
```
**Response**
```json
[
    {
        "title": "My Thoughts on the Importance of Reading",
        "body": "Reading is one of the most important activities that a person can engage in. It allows us to learn about new topics and ideas, develop our critical thinking skills, and expand our imaginations. Whether you enjoy fiction or non-fiction, there is something for everyone in the world of books. So take some time today to curl up with a good book and see where it takes you!",
        "username": "mark12",
        "commentCount": 3,
        "createdAt": "2023-04-30T21:33:32.582+00:00"
    }
]
```

## Comment Endpoints
### Create Comment
**Request**
```http
POST /api/blogs/{blogId}/comments/
Content-Type: application/json
Authorization: Bearer <token>
```
```json
{
"body": "comment body"
}
```
**Response**
```
Created successfully
```
### Update Comment
**Request**
```http
PUT /api/blogs/0/comments/{commentId}
Content-Type: application/json
Authorization: Bearer <token>
```
```json
{
"body": "updated blog content"
}
```
**Response**
```json
{
"body": "updated blog content"
}
```
### Get Blog Comments
**Request**
```http
DELETE /api/blogs/8/comments/
Content-Type: application/json
Authorization: Bearer <token>
```
**Response**
```
Deleted successfully
```
### Get Comment By Id
**Request**
```http
GET /api/blogs/0/comments/{commentId}
Content-Type: application/json
Authorization: Bearer <token>
```
**Response**
```json
{
    "body": "As an admin i liked this blog",
    "username": "admin",
    "createdAt": "2023-04-29T14:50:12.655+00:00",
    "updatedAt": "2023-04-30T17:27:12.697+00:00",
    "blogTitle": "Ahmet's Blog"
}
```

## User Endpoints
### Get My Profile
**Request**
```http
GET /api/user/my-profile
Content-Type: application/json
Authorization: Bearer <token>
```
**Response**
```json
{
    "username": "ahmet",
    "email": "saas@gmail.com",
    "age": 19,
    "blogs": [
        {
            "title": "Ahmet's Blog",
            "body": "Hey, this is my blog about Log4j2. Log4j2 is a logging framework that is mostly used in back-end services. It's the most popular logging framework. Last year, unfortunately there was a critical loop-hole was found in this framework that led all of companies to double-check and re-build their logging systems.",
            "commentCount": 2,
            "createdAt": "2023-04-29T14:50:12.655+00:00"
        }
    ],
    "comments": [
        {
            "body": "Hey, nice blog. Keep up the good work!",
            "createdAt": "2023-04-29T14:42:41.628+00:00",
            "updatedAt": "2023-04-30T17:17:36.502+00:00"
        },
        {
            "body": "updated test comment!!",
            "createdAt": "2023-04-29T14:50:12.655+00:00",
            "updatedAt": "2023-04-30T17:27:12.697+00:00"
        },
        {
            "body": "This is a great blog post! Thanks for sharing your insights.",
            "createdAt": "2023-04-30T16:34:54.336+00:00",
            "updatedAt": "2023-04-30T21:39:39.030+00:00"
        },
        {
            "body": "I really enjoyed reading your blog post. Your writing style is engaging and you make some great points about the importance of staying active. Keep up the good work!",
            "createdAt": "2023-04-30T21:33:32.582+00:00",
            "updatedAt": null
        }
    ]
}
```
### Get My Blogs
**Request**
```http
GET /api/user/my-blogs
Content-Type: application/json
Authorization: Bearer <token>
```
**Response**
```json
[
    {
        "title": "Ahmet's Blog",
        "body": "Hey, this is my blog about Log4j2. Log4j2 is a logging framework that is mostly used in back-end services. It's the most popular logging framework. Last year, unfortunately there was a critical loop-hole was found in this framework that led all of companies to double-check and re-build their logging systems.",
        "commentCount": 2,
        "createdAt": "2023-04-29T14:50:12.655+00:00"
    }
]
```
### Get My Comments
**Request**
```http
GET /api/user/my-comments
Content-Type: application/json
Authorization: Bearer <token>
```
**Response**
```json
[
    {
        "body": "Hey, nice blog. Keep up the good work!",
        "createdAt": "2023-04-29T14:42:41.628+00:00",
        "updatedAt": "2023-04-30T17:17:36.502+00:00"
    },
    {
        "body": "updated test comment!!",
        "createdAt": "2023-04-29T14:50:12.655+00:00",
        "updatedAt": "2023-04-30T17:27:12.697+00:00"
    },
    {
        "body": "This is a great blog post! Thanks for sharing your insights.",
        "createdAt": "2023-04-30T16:34:54.336+00:00",
        "updatedAt": "2023-04-30T17:36:38.135+00:00"
    }
]
```


## Built With

Java Spring Boot.
Dependencies:
- Spring WEB
- Spring Data JPA
- Spring Security
- OAuth2 Resource Server
- Spring DevTools
- PostgreSQL Driver
- ModelMapper
- Log4j2
