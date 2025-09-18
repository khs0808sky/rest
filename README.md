# rest

## 📅 목차

- [2025-09-10](#2025-09-10)
- [2025-09-11](#2025-09-11)
- [2025-09-12](#2025-09-12)
- [2025-09-15](#2025-09-15)
- [2025-09-16](#2025-09-16)
- [2025-09-17](#2025-09-17)
- [2025-09-18](#2025-09-18)

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
주로 **DB와 객체(Entity)**, **Entity와 DTO(Data Transfer Object)** 사이에서 사용됩니다.
즉, **데이터를 한 형태에서 다른 형태로 바꿔주는 중간 관리자**라고 이해하면 쉽습니다.

---

#### 🔹 Mapper의 기본 개념

1. **DB ↔ Entity 변환**

   * DB 테이블의 한 행(row)을 자바 객체(Entity)로 매핑
   * Entity 객체를 DB에 저장할 때 다시 SQL용 데이터로 변환
   * 예: `users` 테이블 → `User` 객체

2. **Entity ↔ DTO 변환**

   * Entity: DB와 직접 연결된 객체, 비즈니스 로직 중심
   * DTO: 화면(UI)이나 API에서 주고받는 데이터 객체, 필요한 데이터만 담음
   * Mapper는 Entity ↔ DTO 변환 담당 → 불필요한 데이터 전달 방지, 구조 깔끔 유지

3. **자동 변환 라이브러리 활용**

   * MapStruct, Dozer 같은 라이브러리로 반복적인 변환 코드 최소화
   * 예: `UserMapper.toDto(userEntity)`

---

#### 🔹 Spring / Java에서 Mapper 사용 예시

```java
// Entity
public class User {
    private Long id;
    private String name;
    // getter, setter
}

// DTO
public class UserDto {
    private Long id;
    private String name;
    // getter, setter
}

// Mapper 인터페이스 (MapStruct 사용 예)
@Mapper
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
```

---

#### 🔹 Mapper 활용 포인트

1. **데이터 변환 집중**

   * Mapper는 데이터 구조 변환만 담당
   * DB 조회/삽입 로직은 Repository나 DAO가 담당

2. **코드 깔끔함 유지**

   * 변환 로직이 한 곳에 모이므로 관리 용이
   * 복잡한 엔티티 → DTO 변환 시 반복 코드 제거

3. **유지보수 용이**

   * DTO 구조 변경 시 Mapper만 수정하면 됨

---

#### 🔹 쉽게 비유하면

* “DB어” → “Java어”
* “Entity어” → “DTO어”
* 데이터 구조가 바뀔 때 **중간에서 맞춰주는 번역가** 같은 존재

---

📅[목차로 돌아가기](#-목차)

---

### 2025-09-12

---

### FastAPI란?

**FastAPI**는 **Python으로 빠르고 간단하게 REST API 서버**를 만들 수 있게 해주는 웹 프레임워크입니다.
이름처럼 **빠른 개발 속도**와 **높은 성능**이 장점이며, Starlette과 Pydantic을 기반으로 만들어졌습니다.

---

#### 🔹 FastAPI의 특징

1. **빠른 개발**

   * 자동으로 Swagger UI / ReDoc API 문서 생성
   * 코드 작성 → 바로 테스트 가능 → 생산성↑

2. **성능**

   * 비동기(async/await) 지원 → Node.js 수준의 높은 처리량
   * Uvicorn / ASGI 서버 기반 → 빠른 응답속도

3. **타입 힌트 기반**

   * Python의 `type hint`를 활용해 요청·응답 자동 검증
   * IDE 자동완성, 오류 사전 방지 가능

4. **간단한 문법**

   * Flask처럼 가볍지만, 기능은 더 풍부
   * 배워서 쓰기 쉬움

---

#### 🔹 기본 사용 예시

```python
from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "Hello FastAPI!"}

@app.get("/users/{user_id}")
def read_user(user_id: int):
    return {"user_id": user_id}
```

* `@app.get("/users/{user_id}")` → HTTP GET 요청을 해당 함수와 매핑
* `user_id: int` → 자동으로 타입 변환 및 유효성 검사

---

#### 🔹 요청/응답 처리

1. **Query Parameter**

   ```python
   @app.get("/search")
   def search(q: str):
       return {"query": q}
   ```

   → `/search?q=python`

2. **Request Body**

   ```python
   from pydantic import BaseModel

   class User(BaseModel):
       name: str
       age: int

   @app.post("/users")
   def create_user(user: User):
       return {"name": user.name, "age": user.age}
   ```

   → JSON 요청을 자동으로 `User` 객체로 변환

3. **Response Model**

   ```python
   @app.get("/users/{user_id}", response_model=User)
   def get_user(user_id: int):
       return {"name": "철수", "age": 20}
   ```

   → 응답을 자동으로 검증하고 JSON으로 변환

---

#### 🔹 장점 정리

* **자동 문서화** → Swagger UI, ReDoc 바로 제공
* **비동기 처리** → 빠른 속도
* **타입 힌트 기반 검증** → 안전한 API
* **간단한 문법** → Flask처럼 직관적

---

#### 🔹 언제 쓰면 좋은가?

* 빠르게 REST API, 백엔드 서버를 만들어야 할 때
* 데이터 검증이 중요한 프로젝트
* 비동기 처리가 필요한 실시간 서비스 (채팅, 스트리밍 등)
* 서버 + 자동 문서화 + 테스트를 한 번에 해결하고 싶을 때

---

📅[목차로 돌아가기](#-목차)

---

### 2025-09-15

---

### GIT이란?

**Git**은 **분산 버전 관리 시스템(Distributed Version Control System)** 입니다.
즉, 프로젝트의 변경 이력을 기록하고, 협업 시 코드 충돌을 최소화하며, 과거 버전으로 되돌릴 수 있게 해주는 도구입니다.
개발자라면 필수로 익혀야 하는 협업·버전 관리 툴입니다.

---

#### 🔹 Git의 핵심 개념

1. **로컬 저장소 & 원격 저장소**

   * 로컬 저장소(Local Repository): 내 컴퓨터에 저장된 버전 관리 공간
   * 원격 저장소(Remote Repository): GitHub, GitLab, Bitbucket 같은 서버에 있는 저장소
   * `git push` → 로컬에서 원격으로 올림
     `git pull` → 원격에서 로컬로 가져옴

2. **스냅샷(Snapshot)**

   * Git은 파일을 단순히 변경점(diff)으로만 저장하는 것이 아니라,
     **그 시점의 전체 스냅샷을 저장** (중복되는 파일은 내부적으로 효율적으로 처리)

3. **브랜치(Branch)**

   * 독립적인 작업 공간
   * `main` (혹은 `master`) 브랜치에서 새로운 브랜치를 만들어 실험, 기능 추가 가능
   * 머지(Merge) 시 협업 효율 ↑

---

#### 🔹 Git 기본 명령어

1. **설정**

   ```bash
   git config --global user.name "Hyunsoo"
   git config --global user.email "hyunsoo@example.com"
   ```

2. **저장소 초기화 & 상태 확인**

   ```bash
   git init        # 새로운 로컬 저장소 생성
   git status      # 현재 상태 확인
   ```

3. **스테이징 & 커밋**

   ```bash
   git add file.py       # 특정 파일 스테이징
   git add .             # 모든 변경 파일 스테이징
   git commit -m "메시지"  # 스냅샷(커밋) 생성
   ```

4. **브랜치 & 머지**

   ```bash
   git branch feature/login    # 브랜치 생성
   git checkout feature/login  # 브랜치 이동
   git merge feature/login     # 브랜치 병합
   ```

5. **원격 저장소 연결 & 동기화**

   ```bash
   git remote add origin https://github.com/user/repo.git
   git push -u origin main
   git pull
   ```

---

#### 🔹 Git의 장점

* **안전한 버전 관리** → 이전 상태로 쉽게 되돌릴 수 있음
* **협업 최적화** → 여러 명이 동시에 작업해도 충돌 최소화
* **분산형 시스템** → 인터넷이 없어도 로컬에서 작업 가능
* **오픈소스 & 표준** → GitHub, GitLab, Bitbucket 등 어디서나 사용 가능

---

#### 🔹 Git 활용 시나리오

* 개인 프로젝트 버전 관리 (실험 → 롤백 → 개선)
* 팀 프로젝트 협업 (브랜치 전략, PR 코드 리뷰)
* 오픈소스 기여 (Fork → 수정 → Pull Request)

---

📅[목차로 돌아가기](#-목차)

---

### 2025-09-16

---

### Docker란?

**Docker**는 애플리케이션을 **컨테이너(container)** 라는 독립적인 환경에 담아 실행할 수 있게 해주는 플랫폼입니다.
쉽게 말해, 개발 환경과 운영 환경을 완전히 일치시켜서 **“내 컴퓨터에서는 되는데 서버에서는 안 돼요”** 문제를 해결해 줍니다.

---

#### 🔹 Docker의 핵심 개념

1. **이미지(Image)**

   * 애플리케이션 실행에 필요한 OS, 라이브러리, 코드 등을 묶어둔 템플릿
   * `Dockerfile`로 정의
   * 예: `python:3.12-slim`, `nginx:latest`

2. **컨테이너(Container)**

   * 이미지를 실제로 실행한 인스턴스
   * 독립적인 환경에서 동작하지만, 호스트 OS 자원을 공유
   * 가볍고 빠르게 생성/삭제 가능

3. **레지스트리(Registry)**

   * Docker 이미지를 저장/배포하는 서버
   * Docker Hub가 대표적 (GitHub의 Docker 버전이라고 생각하면 됨)

---

#### 🔹 Docker 주요 장점

* **환경 일관성** → 어디서 실행해도 동일한 환경 보장
* **가벼움** → VM보다 빠르고 리소스 적게 사용
* **이식성** → 한 번 빌드한 이미지로 로컬/테스트/운영 서버 어디서나 실행 가능
* **확장성** → 컨테이너 여러 개 띄워 손쉽게 스케일링 가능

---

#### 🔹 기본 명령어

```bash
# 이미지 다운로드
docker pull nginx:latest

# 컨테이너 실행
docker run -d -p 8080:80 nginx:latest

# 현재 실행 중인 컨테이너 확인
docker ps

# 컨테이너 중지
docker stop <컨테이너ID>

# Dockerfile로 이미지 빌드
docker build -t myapp:1.0 .
```

---

#### 🔹 Dockerfile 예시

```dockerfile
FROM python:3.12-slim
WORKDIR /app
COPY requirements.txt .
RUN pip install -r requirements.txt
COPY . .
CMD ["python", "main.py"]
```

* **FROM**: 기반 이미지 선택
* **WORKDIR**: 작업 디렉토리 설정
* **COPY**: 코드 복사
* **RUN**: 빌드 시 실행할 명령
* **CMD**: 컨테이너 실행 시 기본 명령

---

#### 🔹 실제 활용 예시

* 로컬에서 개발한 FastAPI 앱을 컨테이너로 감싸서 배포
* 팀원 모두 같은 개발 환경 사용 → "환경 차이 버그" 제거
* CI/CD 파이프라인에서 빌드 & 테스트 자동화

---

📅[목차로 돌아가기](#-목차)

---

### 2025-09-17

---

### Docker란?

**Docker**는 애플리케이션과 그 실행 환경(OS, 라이브러리, 의존성)을 하나의 **컨테이너(Container)** 로 묶어 실행할 수 있게 해주는 플랫폼입니다.
즉, 개발 환경과 배포 환경을 동일하게 맞춰서 **“내 PC에서는 되는데 서버에서 안 돼요”** 문제를 없애줍니다.

---

#### 🔹 Docker의 핵심 개념

1. **이미지(Image)**

   * 애플리케이션 실행에 필요한 모든 것을 담은 **템플릿**
   * 불변(Immutable) → 변경 시 새 이미지 생성
   * 예: `python:3.12-slim`, `nginx:latest`

2. **컨테이너(Container)**

   * 이미지를 실제로 실행한 **인스턴스**
   * 격리된 공간에서 돌아가지만, 호스트 자원을 공유 → 빠르고 가벼움
   * 필요할 때 생성/삭제/복제 가능

3. **Dockerfile**

   * 이미지를 만드는 **레시피 파일**
   * 예:

     ```dockerfile
     FROM python:3.12-slim
     WORKDIR /app
     COPY requirements.txt .
     RUN pip install -r requirements.txt
     COPY . .
     CMD ["python", "main.py"]
     ```

4. **레지스트리(Registry)**

   * 이미지를 보관·공유하는 서버
   * Docker Hub, AWS ECR, GitHub Container Registry 등

---

#### 🔹 Docker의 주요 장점

* **환경 일관성 보장** → 어디서 실행해도 동일한 환경
* **경량·빠른 실행** → VM보다 훨씬 가볍고 빠르게 배포 가능
* **이식성** → 로컬 → 테스트 서버 → 운영 서버 한 번에 배포 가능
* **스케일링 용이** → 컨테이너 여러 개 띄워 부하 분산 가능

---

#### 🔹 자주 쓰는 명령어

```bash
# 이미지 다운로드
docker pull nginx:latest

# 컨테이너 실행 (백그라운드 실행 & 포트 매핑)
docker run -d -p 8080:80 nginx:latest

# 현재 컨테이너 목록 보기
docker ps

# 컨테이너 중지
docker stop <컨테이너ID>

# Dockerfile로 이미지 빌드
docker build -t myapp:1.0 .
```

---

#### 🔹 실제 활용 예시

* FastAPI, Spring Boot 앱을 컨테이너로 묶어 팀원 전원 같은 환경에서 실행
* 테스트/운영 서버에 동일 이미지 배포 → “환경 차이 버그” 제거
* CI/CD 파이프라인에서 자동 빌드 & 테스트 → 신속한 배포

---

📅[목차로 돌아가기](#-목차)

---

### 2025-09-19

---

### 쿠버네티스(Kubernetes)란?

\*\*쿠버네티스(Kubernetes, 줄여서 K8s)\*\*는 **컨테이너 오케스트레이션(Container Orchestration)** 플랫폼입니다.
즉, Docker 같은 컨테이너를 **대규모로 배포·관리·확장**하기 위해 만들어진 시스템입니다.
구글이 내부에서 사용하던 Borg 시스템을 오픈소스로 공개한 것이 시작점입니다.

---

#### 🔹 쿠버네티스의 핵심 개념

1. **클러스터(Cluster)**

   * 여러 개의 서버(Node)를 묶어 하나의 거대한 컴퓨터처럼 동작
   * **마스터(Node)** → 전체 스케줄링·관리
   * **워커(Node)** → 실제 컨테이너 실행

2. **Pod**

   * 쿠버네티스에서 배포되는 가장 작은 단위
   * 1개 이상의 컨테이너로 구성 (보통 1 Pod = 1 컨테이너)

3. **Deployment**

   * Pod를 몇 개 띄울지, 어떻게 업데이트할지 정의
   * 롤링 업데이트, 롤백 등을 지원

4. **Service**

   * Pod들이 외부와 통신할 수 있도록 네트워크 추상화
   * 로드밸런싱 제공 → 트래픽 균등 분배

5. **ConfigMap & Secret**

   * 환경 변수, 설정 파일, 비밀번호 등을 안전하게 주입

---

#### 🔹 쿠버네티스의 장점

* **자동화된 배포 & 롤백** → 새로운 버전 배포 시 점진적 업데이트
* **자동 복구(Self-Healing)** → 죽은 Pod를 자동 재시작
* **수평 확장(Scaling)** → 트래픽 증가 시 Pod 개수를 자동으로 늘림
* **서비스 디스커버리 & 로드밸런싱** → Pod 위치와 상관없이 접근 가능
* **클라우드/온프레미스 어디서나 동작** → AWS, GCP, Azure, 로컬 클러스터(Minikube) 등

---

#### 🔹 간단한 예시 (Deployment & Service)

```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
      - name: myapp-container
        image: myapp:1.0
        ports:
        - containerPort: 80
```

```yaml
# service.yaml
apiVersion: v1
kind: Service
metadata:
  name: myapp-service
spec:
  selector:
    app: myapp
  ports:
    - port: 80
      targetPort: 80
  type: LoadBalancer
```

* **Deployment** → `myapp` 컨테이너를 3개 실행
* **Service** → 로드밸런서로 트래픽을 세 Pod에 분배

---

#### 🔹 Docker vs Kubernetes 관계

* Docker → 컨테이너 **한 개**를 만들고 실행
* Kubernetes → **수십\~수천 개의 컨테이너**를 관리, 확장, 네트워킹, 모니터링

즉, Docker로 만든 앱을 실제 서비스 환경에서 안정적으로 운영하려면
Kubernetes 같은 오케스트레이션 툴이 필요합니다.

---

📅[목차로 돌아가기](#-목차)

---


