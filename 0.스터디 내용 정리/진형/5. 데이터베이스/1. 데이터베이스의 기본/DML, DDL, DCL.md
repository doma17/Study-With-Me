# DML / DDL / DCL 

> 정의 : SQL은 기능에 따라 크게 **DML**, **DDL**, **DCL**(그리고 보통 별도로 **TCL**)로 나뉩니다. 아래 요약과 예제로 개념–차이–사용처를 한 번에 정리합니다.

---

## 1) 개념 요약

- **DML (Data Manipulation Language)**  
  테이블 **데이터를 조회/변경**합니다. `SELECT`, `INSERT`, `UPDATE`, `DELETE`, `MERGE` 등.  
  → **트랜잭션 대상**이며 `COMMIT/ROLLBACK` 가능.

- **DDL (Data Definition Language)**  
  **스키마(구조)를 정의/변경**합니다. `CREATE`, `ALTER`, `DROP`, `TRUNCATE`, `RENAME` 등.  
  → 많은 DBMS에서 **암시적(implicit) COMMIT**이 발생(롤백 불가인 경우 多).

- **DCL (Data Control Language)**  
  **권한/보안** 제어입니다. `GRANT`, `REVOKE` 등.  
  → 사용자/역할 생성은 DBMS에 따라 DDL로 분류되기도 함.

> 참고: **TCL (Transaction Control Language)** — `COMMIT`, `ROLLBACK`, `SAVEPOINT`, `SET TRANSACTION` 등.

---

## 2) 분류 & 대표 명령어

| 분류 | 목적 | 대표 명령어 |
|---|---|---|
| DML | 행 단위 **데이터** 조작 | `SELECT`, `INSERT`, `UPDATE`, `DELETE`, `MERGE` |
| DDL | 객체/스키마 **정의/변경** | `CREATE`, `ALTER`, `DROP`, `TRUNCATE`, `RENAME` |
| DCL | **권한** 부여/회수 | `GRANT`, `REVOKE` |
| (TCL) | **트랜잭션 제어** | `COMMIT`, `ROLLBACK`, `SAVEPOINT` |

---

## 3) 자주 쓰는 예제

### 3-1. DML ( Data Manipulation Language )

### - 데이터 조작어란? 정의된 데이터베이스에 입력된 레코드를 조회하거나 수정하거나 삭제하는 등의 역할을 하는 언어.
### - select : 데이터 조회
### - insert : 데이터 삽입
### - update : 데이터 수정
### - delete : 데이터 삭제

### * 데이터베이스 사용자가 프로그램이나 질이어의 통하여 저장된 데이터를 실질적으로 처리하는데 사용하는 언어
### * 데이터베이스 사용자와 데이터베이스 관리 시스템 간의 인터페이스를 제공

```sql
-- INSERT
INSERT INTO account (id, owner, balance) VALUES (1, 'JH', 1000);

-- UPDATE
UPDATE account SET balance = balance - 100 WHERE id = 1;

-- DELETE
DELETE FROM account WHERE id = 99;

-- SELECT (집계)
SELECT owner, SUM(balance) AS total_balance
FROM account
GROUP BY owner;

-- MERGE (있으면 갱신, 없으면 삽입) — DBMS별 문법 상이
MERGE INTO inventory i
USING (SELECT :sku AS sku, :qty AS qty) s
ON (i.sku = s.sku)
WHEN MATCHED THEN UPDATE SET qty = i.qty + s.qty
WHEN NOT MATCHED THEN INSERT (sku, qty) VALUES (s.sku, s.qty);
```

### 3-2. DDL ( Data Definition Language )

### - 데이터 정의어란? 데이터베이스를 정의하는 언어이며, 데이터를 생성, 수정, 삭제 하는 등의 데이터의 전체 골격을 결정하는 언어
### - create : 데이터베이스. 테이블등을 생성
### - alter : 테이블을 수정
### - drop : 데이터베이스, 테이블을 삭제
### - truncate : 테이블을 초기화

### * SCHEMA, DOMAIN, TABLE, VIEW, INDEX를 정의하거나 변경 또는 삭제할 때 사용하는 언어
### * 데이터 베이스 관리자나 데이터베이스 설계자가 사용

```sql
-- CREATE TABLE
CREATE TABLE account (
  id        BIGINT PRIMARY KEY,
  owner     VARCHAR(50) NOT NULL,
  balance   DECIMAL(15,2) DEFAULT 0,
  created_at TIMESTAMP
);

-- ALTER TABLE (컬럼 추가)
ALTER TABLE account ADD COLUMN last_tx_at TIMESTAMP;

-- TRUNCATE (모든 행 즉시 삭제, 보통 롤백 불가)
TRUNCATE TABLE account;

-- DROP TABLE (테이블 자체 삭제)
DROP TABLE account;
```

### 3-3. DCL ( Data Control Language )

### - 데이터베이스에 접근하거나 객체에 권한을 주는등의 역할을 하는 언어
### - grant : 특정 데이터베이스 사용자에게 특정 작업에 대한 수행 권한을 부여
### - revoke : 특정 데이터베이스 사용자에게 특정 작업에 대한 수행 권한을 **박탈, 회수**
### - commit : 트랜잭션의 작업을 저장
### - rollback : 트랜잭션의 작업을 **취소, 원래대로 복구**


```sql
-- 권한 부여
GRANT SELECT, INSERT, UPDATE ON account TO app_user;

-- 권한 회수
REVOKE UPDATE ON account FROM app_user;

-- 역할 기반 사용 (DBMS별 문법 상이)
CREATE ROLE readonly;
-- 예: PostgreSQL의 스키마 단위 권한 부여
-- GRANT USAGE ON SCHEMA public TO readonly;
-- GRANT SELECT ON ALL TABLES IN SCHEMA public TO readonly;
GRANT readonly TO app_user;
```

---

## 4) 트랜잭션/커밋 동작 차이

- **DML**: 트랜잭션에 포함. `COMMIT` 전에는 변경이 확정되지 않으며 `ROLLBACK` 가능.
- **DDL**:  
  - **Oracle**: DDL 실행 **전/후 암시적 COMMIT**, 롤백 불가.  
  - **MySQL(InnoDB)**: 대다수 DDL에서 **암시적 COMMIT**, 롤백 불가(원자적 DDL이라도 트랜잭션과 별개).  
  - **PostgreSQL**: 대부분의 DDL이 **트랜잭션 내에서 실행/롤백 가능**(일부 예외 명령은 트랜잭션 밖 요구).
- **DCL**: DBMS에 따라 암시적 커밋이 발생하거나, 트랜잭션 내 적용 범위가 제한될 수 있음.

> 실무 팁: 배포 스크립트에서 **DDL과 DML을 분리**하고, 장애 대비 **마이그레이션 롤백 경로**(역 DDL)를 준비하세요.

---

## 5) FAQ

- **DELETE vs TRUNCATE vs DROP**  
  - `DELETE`: 조건 기반 **행 삭제**(DML). 트랜잭션 가능, 트리거 동작.  
  - `TRUNCATE`: **모든 행 즉시 삭제**(DDL). 빠르지만 보통 롤백 불가, 트리거 동작 X.  
  - `DROP`: **객체 자체 삭제**(DDL). 테이블/인덱스 등 구조가 사라짐.

- **CREATE USER/ROLE은 어디에 속하나?**  
  보통 **DDL**로 취급. 권한 부여/회수는 **DCL**.

- **권한 부여 모범 사례**  
  사용자에 직접 권한을 주기보다 **역할(Role)**을 만들고 역할에 권한을 모아 **역할을 사용자에게 부여**.

---

## 6) 체크리스트

- 변경이 **데이터**인가? → DML  
- 변경이 **구조(스키마)**인가? → DDL  
- **권한/보안**인가? → DCL  
- 롤백 필요 시, DDL 포함 여부/DBMS별 동작을 미리 확인하자.
