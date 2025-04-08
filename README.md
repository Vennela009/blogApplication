# Blog Application

## Project Overview
This is a Blog Application built using **Spring MVC**, **Spring Data JPA**, **Thymeleaf**, and **Spring Security**. It provides a platform for creating, viewing, and managing blog posts, with user authentication and role-based access control.

---

## Part 1: CRUD

### Technologies Used:
- **Spring MVC**
- **Spring Data JPA**
- **Thymeleaf**

### Use Cases for Non-Logged-In Users:
- **View Blog Posts**: 
  - Read a list of blog posts showing title, excerpt, author, published DateTime, and tags.
  - Read full blog posts with title, content, author, published DateTime, and tags.
  - Filter blog posts by author, published DateTime, and tags.
  - Sort blog posts by published DateTime.
  - Search blog posts using full-text search on title, content, author, and tags.
  - Navigate through paginated blog lists (10 posts per page).
  
- **Commenting**:
  - Comment on blog posts using a form containing name, email, and comment.
  - Read, update, and delete comments.

- **CRUD Operations for Posts**:
  - Create, update, and delete blog posts.

### Database Schema:
- **User**:
  - id, name, email, password

- **Posts**:
  - id, title, excerpt, content, author, published_at, is_published, created_at, updated_at

- **Tags**:
  - id, name, created_at, updated_at

- **Post_Tags**:
  - post_id, tag_id, created_at, updated_at

- **Comments**:
  - id, name, email, comment, post_id, created_at, updated_at

### Steps to Build:
1. **Design & Implement Database Schema**: Create tables for users, posts, tags, post_tags, and comments.
2. **Create HTML & CSS**: Develop the layout for pages such as the homepage, create post page, and comment section.
3. **Integrate Thymeleaf**: Use Thymeleaf to dynamically render data from the database in HTML templates.
4. **Connect Frontend with Backend**: Use Spring MVC and Spring Data JPA to interact with the database and serve data to the frontend.

### Steps for M2:
- Implement functionality for authors and tags.
- Enable commenting features.
- Implement pagination, filters, sorting, and searching.

### Steps for M3 (Future Development):
- **Authentication & Authorization**:
  - Implement user login and role-based access control using Spring Security.

---

## Part 2: Authentication & Authorization

### Technologies Used:
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **Thymeleaf**

### Use Cases for Non-Logged-In & Logged-In Users:
- **Non-Logged-In Users**:
  - Read, filter, sort, and search blog posts.
  
- **Logged-In Users**:
  - Create, update, and delete blog posts (based on roles).
  - Comment on blog posts and manage their own comments.

- **Admin Privileges**:
  - Admins can create, update, and delete posts.

### Steps to Implement:
1. **Incorporate Spring Security**: Set up authentication and authorization with Spring Security.
2. **Enable User Roles**: Define roles (e.g., Admin, User) and assign permissions for different actions like CRUD operations.
3. **Implement Role-Based CRUD**: Allow admins to perform all CRUD operations, while users can only create and manage their own posts.

---

## BlogApplication Directory Structure

```plaintext
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── vennela009
│   │   │           └── blogApplication
│   │   │               ├── BlogApplication.java
│   │   │               ├── controller
│   │   │               │   ├── CommentController.java
│   │   │               │   ├── PostController.java
│   │   │               │   └── UserController.java
│   │   │               ├── entity
│   │   │               │   ├── Comment.java
│   │   │               │   ├── Post.java
│   │   │               │   ├── Tag.java
│   │   │               │   └── User.java
│   │   │               ├── repository
│   │   │               │   ├── CommentRepository.java
│   │   │               │   ├── PostRepository.java
│   │   │               │   ├── TagRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── security
│   │   │               │   └── SecurityConfig.java
│   │   │               └── service
│   │   │                   ├── CommentService.java
│   │   │                   ├── CommentServiceImpl.java
│   │   │                   ├── PostService.java
│   │   │                   ├── PostServiceImpl.java
│   │   │                   ├── TagService.java
│   │   │                   ├── TagServiceImpl.java
│   │   │                   ├── UserService.java
│   │   │                   ├── UserServiceImpl.java
│   └── resources
│       ├── application.properties
│       ├── static
│       └── templates
│           ├── create-post.html
│           ├── edit-post.html
│           ├── homepage.html
│           ├── login.html
│           ├── registration.html
│           ├── updatecomment.html
│           └── view-post.html
│   └── test
│       └── java
│           └── com
│               └── vennela009
│                   └── blogApplication
│                       └── BlogApplicationTests.java

```
---

# Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **Thymeleaf** (optional)
- **Maven**

---

## Installation

### Prerequisites
- **Java 17** or higher
- **PostgreSQL** (configured and running)
- **Maven**

### Clone the Repository

```bash
git clone https://github.com/yourusername/blogapplication.git
cd blogapplication

