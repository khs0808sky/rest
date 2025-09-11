# rest

## 📅 목차

- [2025-09-10](#2025-09-10)
- [2025-09-11](#2025-09-11)
  
<br><br><br>

---

## 2025-09-10

### Spring에서 REST

Spring에서 REST(Representational State Transfer)는 **HTTP 프로토콜을 기반으로 리소스를 표현하고 접근하는 방식**을 구현할 때 자주 사용하는 아키텍처 스타일이에요.
Spring은 이를 쉽게 개발할 수 있도록 다양한 애노테이션과 기능을 제공합니다.

---

#### 🔹 REST의 기본 개념

1. **리소스(Resource) 중심 설계**

   * URL은 동작이 아니라 **리소스**를 표현해야 함
   * 예: `/users/1` → `id=1`인 사용자 리소스

2. **HTTP 메서드 활용**

   * `GET`: 리소스 조회
   * `POST`: 리소스 생성
   * `PUT`: 리소스 전체 수정
   * `PATCH`: 리소스 부분 수정
   * `DELETE`: 리소스 삭제

3. **표현(Representation)**

   * 리소스의 상태는 JSON, XML 등으로 표현
   * Spring에서는 주로 JSON 사용

---

#### 🔹 Spring에서 REST API 구현 핵심

1. **@RestController**

   * Spring MVC에서 REST API를 쉽게 만들기 위해 사용하는 애노테이션
   * 내부적으로 `@Controller + @ResponseBody` 합친 것

   ```java
   @RestController
   @RequestMapping("/api")
   public class UserController {
       @GetMapping("/users/{id}")
       public User getUser(@PathVariable Long id) {
           return new User(id, "철수");
       }
   }
   ```

2. **@RequestMapping / @GetMapping / @PostMapping ...**

   * 요청 URL과 HTTP 메서드를 매핑
   * 예: `@GetMapping("/users")` → GET `/users`

3. **@PathVariable / @RequestParam / @RequestBody**

   * `@PathVariable`: URL 경로 변수 매핑
   * `@RequestParam`: 쿼리 파라미터 매핑
   * `@RequestBody`: 요청 본문(JSON)을 객체로 변환

4. **ResponseEntity**

   * 상태 코드와 응답 헤더를 포함해 상세한 응답을 만들 때 사용

   ```java
   @GetMapping("/users/{id}")
   public ResponseEntity<User> getUser(@PathVariable Long id) {
       User user = new User(id, "철수");
       return ResponseEntity.ok(user);
   }
   ```

5. **자동 직렬화**

   * Jackson 라이브러리 덕분에 객체 → JSON 변환 자동 처리
   * 별도 설정 없이 `User` 객체를 리턴하면 JSON 응답으로 변환됨

---

#### 🔹 REST 설계 원칙 (Spring에서 지키면 좋은 것)

1. **리소스 기반 URL**

   * `/users/1` (O), `/getUserById?id=1` (X)
2. **HTTP 메서드 명확히 사용**

   * GET은 조회 전용, POST는 생성 전용 등
3. **Stateless**

   * 서버가 클라이언트 상태를 세션에 저장하지 않음
4. **계층적 구조**

   * `/users/{userId}/posts/{postId}`처럼 계층 구조 설계

---

#### 🔹 간단한 CRUD REST API 예시

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @PostMapping
    public User createUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        users.put(id, user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        users.remove(id);
    }
}
```

---

📅[목차로 돌아가기](#-목차)

---

### 2025-09-11

---

### Mapper란?

**Mapper**는 서로 다른 데이터 구조를 **변환(mapping)** 해주는 역할을 하는 개념입니다.
주로 **데이터베이스(DB)와 객체(Entity)**, 혹은 **Entity와 DTO(Data Transfer Object)** 사이에서 사용됩니다.
즉, **데이터를 한 형태에서 다른 형태로 바꿔주는 중간 관리자**라고 이해하면 쉽습니다.

---

#### 1. DB ↔ Entity

* DB 테이블의 한 행(row)을 자바 객체(Entity)로 변환.
* 자바 객체(Entity)를 DB에 저장할 때, 다시 SQL 쿼리용 데이터로 변환.
* 예: `user` 테이블의 데이터를 `User` 객체로 매핑.

#### 2. Entity ↔ DTO

* Entity: DB와 직접 연결된 객체, 비즈니스 로직 중심.
* DTO: 화면(UI)이나 API에서 주고받는 데이터 객체, 필요한 데이터만 담음.
* Mapper는 Entity ↔ DTO 변환을 담당하여, **불필요한 데이터 전달을 줄이고 구조를 깔끔하게 유지**.

---

### 핵심 포인트

* **Mapper = 변환 담당**, DB 조회나 삽입 같은 직접적인 로직은 담당하지 않음.
* 구현 방법: **인터페이스**, 혹은 라이브러리(MyBatis, MapStruct, Dozer 등) 활용.
* 구조가 다른 데이터 간의 **중간 브릿지 역할**을 수행.

---

### 쉽게 비유하면

* “DB어” → “Java어”
* “Entity어” → “DTO어”
* **데이터 구조가 바뀔 때 중간에서 변환해주는 번역가** 같은 존재입니다.
* 덕분에 코드가 깔끔해지고, 데이터 전달 과정에서 오류를 줄일 수 있음.

---

📅[목차로 돌아가기](#-목차)

---
