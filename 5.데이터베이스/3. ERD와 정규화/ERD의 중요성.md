# ERD(Entity–Relationship Diagram) 쉽게 이해하기

> 정의 : **ERD**는 서비스의 데이터를 **어떤 개체(Entity)가 어떤 관계(Relationship)** 로 연결되어 있는지 **그림으로 표현**한 모델입니다.  
요구사항 → ERD → 테이블/컬럼/키(물리 모델) 순으로 설계가 이어집니다.

> 왜 중요 한가 : DB는 시스템의 핵심, 잘못 설계된 DB는 성능 저하, 중복 데이터,
데이터 무결성 문제 등 다양한 문제를 초래함. 따라서 설계 초기 단계에서 문제점을 파악하는게 중요.
---

## 1) 핵심 개념

- **엔터티(Entity)**: 데이터로 관리해야 하는 **명사형 대상**  
  예) 고객, 계좌, 주문, 상품, 게임방, 메시지
- **속성(Attribute)**: 엔터티가 가진 **특성(컬럼 후보)**  
  예) 고객(고객ID, 이름, 휴대폰번호)
- **키(Key)**  
  - **PK(기본키)**: 행을 유일하게 식별(변하지 않는 값 권장)  
  - **FK(외래키)**: 다른 엔터티의 PK를 참조해 **관계**를 표현  
  - 후보키/대체키, 복합키 등
- **관계(Relationship)**: 엔터티 간 연결  
  - **1:1**, **1:N**, **N:M**(교차 엔터티 필요)
  - **카디널리티**(개수)와 **옵셔널리티**(필수/선택)를 함께 표기

---

## 2) 표기법 한눈에 (Crow’s Foot 기준)

- **1:N** : `A ──< B` (발가락 모양이 **많음**을 뜻함)  
- **N:M** : `A >──< B` → **교차 엔터티**로 풀어야 함  
- **필수/선택** : 실선(필수), 원/○ 또는 ‘0’(선택)으로 표시하는 도구도 있음

간단 예시(텍스트 표기):

```
학생 ──< 수강신청 >── 과목
(Student)       (Enrollment)       (Course)
```

- `수강신청`은 N:M을 풀어내는 **교차 엔터티**이며, 보통 PK는 `(student_id, course_id)` 또는 별도 `enroll_id`.

---

## 3) 예시로 보는 ERD → 테이블

### 도메인
- **고객(Customer)** – **계좌(Account)** – **거래(Transaction)**
- 규칙: 한 고객은 여러 계좌를 가질 수 있음(1:N). 한 계좌에는 여러 거래가 존재(1:N).

텍스트 ERD:

```
Customer ──< Account ──< Transaction
```

테이블 스케치:

```sql
CREATE TABLE customer (
  customer_id BIGINT PRIMARY KEY,
  name        VARCHAR(50) NOT NULL,
  phone       VARCHAR(20) UNIQUE
);

CREATE TABLE account (
  account_id  BIGINT PRIMARY KEY,
  customer_id BIGINT NOT NULL,
  number      VARCHAR(32) UNIQUE NOT NULL,
  balance     DECIMAL(18,2) NOT NULL DEFAULT 0,
  FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE transaction (
  tx_id       BIGINT PRIMARY KEY,
  account_id  BIGINT NOT NULL,
  amount      DECIMAL(18,2) NOT NULL,
  tx_type     VARCHAR(10)   NOT NULL, -- DEBIT/CREDIT
  occurred_at TIMESTAMP     NOT NULL,
  FOREIGN KEY (account_id) REFERENCES account(account_id)
);
```

> 매핑 규칙  
> - **1:N**: N쪽(자식)에 **FK**를 둔다. 필수 관계면 `NOT NULL`, 선택 관계면 `NULL` 허용.  
> - **N:M**: **교차 엔터티**(조인 테이블) 생성 → `(A_id, B_id)` 복합 PK + 추가 속성(예: 등록일).  
> - **1:1**: 선택/필수 여부에 따라 어느 쪽에 FK를 둘지 결정, 유니크 제약으로 1:1 보장.

---

## 4) 설계 절차(실무 체크리스트)

1. **요구사항**에서 **명사 추출 → 엔터티 후보** 만들기  
2. 엔터티별 **핵심 속성/식별자(PK)** 정의  
3. **관계/카디널리티/옵셔널리티** 정하기(1:1, 1:N, N:M)  
4. **정규화**(중복/함수적 종속 제거) 후 **조회 패턴**에 맞게 최소한의 비정규화 검토  
5. **네이밍/데이터 타입/제약**(NOT NULL, UNIQUE, FK, INDEX) 확정  
6. 샘플 **쿼리**로 모델 검증(핵심 화면/리포트/집계)  
7. ERD ⇄ 물리 스키마 간 **동기화**(마이그레이션 스크립트 관리)

---

## 5) 좋은 ERD를 위한 팁

- **PK는 안정적**(변하지 않는 값)으로: 주민번호/전화번호처럼 바뀌는 값은 지양  
- **의미 있는 속성은 별도 컬럼**(파싱 의존 X)  
- **인덱스**: FK, 검색 조건, 정렬/조인 컬럼에 적절히 설계  
- **비즈니스 규칙 주석**: “계좌 잔액은 음수가 될 수 없다”처럼 ERD에 메모  
- **도메인 언어 단수형 네이밍**: `Customer`, `Account`(테이블은 복수형도 무방하나 일관성 유지)

---

## 6) 흔한 실수

- **N:M을 직접 조인으로만 생각**하고 교차 엔터티를 만들지 않음  
- **변동 가능 자연키를 PK로 채택**(변경 파급 큼)  
- **옵셔널리티 무시**: 모든 FK를 NOT NULL로 고정  
- **시간/이력**을 모델링하지 않아 감사/통계 요구 대응 실패

---

## 7) 요약

- **ERD = 데이터 구조를 시각화한 설계도**  
- **엔터티/속성/키/관계**가 핵심  
- 매핑 규칙(1:N은 자식 FK, N:M은 교차 엔터티), **카디널리티·옵셔널리티**를 명확히  
- 실제 쿼리와 비즈니스 규칙으로 **지속 검증**하며 발전시키자.

Reference
- https://shin-e-dog.tistory.com/61