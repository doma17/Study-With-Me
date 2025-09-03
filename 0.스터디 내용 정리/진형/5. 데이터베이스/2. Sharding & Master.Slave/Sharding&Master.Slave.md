

# Sharding & Master/Slave(Primary/Replica)

> 정의 : 데이터가 많아서 검색이 느린데 더 빠르게 할 수 있는 방법이 있을까?
> -> 테이블을 나눠서 검색하자 ( 샤딩 )

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FdBeDo1%2FbtrZc67MqmK%2FAAAAAAAAAAAAAAAAAAAAADA2t0X8eH9dXlpEFEkXg7ACty9NK7I8RRn8lYDCewqf%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DMugScdQG3DrKxkzg5%252FmQPWxNhKA%253D)

- 같은 테이블 스키마를 가진 데이터를 다수의 데이터베이스에 분산하여 저장하는 방법
- **테이블을 특정 기준으로 나눠서 저장 및 검색**
- **sharding key** : 나눠진 Shard 중 어떤 shard를 선택할 지 결정하는 키, 결정방식에 따라 Sharding 방법이 나누어짐

---

### Shard key 결정 방식

## Hash Sharding

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FTJOBR%2FbtrZatpx4cp%2FAAAAAAAAAAAAAAAAAAAAAJDV5Z8D8nL54tc4JjukhKUwdrnAFOucPeqg_sTTBPjs%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DX1MfXUoPW3qlDdpu%252Fit8yQNtCWk%253D)

- Shard Key : Database id를 Hashing 하여 결정
- Hash 크기는 Cluster안에 있는 Node개수로 정함

- *구현이 간단*하다
- 확장성이 떨어진다
  - Node 개수를 늘리거나 줄일 경우, Hash 크기와 Key가 변함 -> Data 분산 Rule 어긋남
  - => Resharding 필요
  - *공간에 대한 효율을 고려 안함*

---

### Dynamic Sharding

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fdzwyrh%2FbtrZa4XkEfH%2FAAAAAAAAAAAAAAAAAAAAAPZeuH9ed5KNPbAYFXZWWlylo1nq4deiy87WD09CQhYO%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3D77t8rAJKyhHxKNppb0cBeRlMphc%253D)

- *확장에 용이함*
  - Node의 개수를 늘릴 경우 : Locator Service에 Shard Key 추가만 하면 됨
  - 기존 Data Shard Key 변경 X
- 데이터를 재배치 시 Locator Service의 Shared Key Table도 동기화 해야함
- Locator에 의존적이다
  - Locator가 성능을 위해 Cache하거나 Replication할 경우, 잘못된 Routing을 통해 Data 찾지 못하고 Error 발생

- 위 두가지 방법은 Key-Value 형태를 지원하기 위해 나온 방법

---

### Entity Group

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FkX3zW%2FbtrZbqMDEzL%2FAAAAAAAAAAAAAAAAAAAAAOtZ2XsfexhwKjKFynq2fKpTNDmMuNafLK0L73QMU--w%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DcLmBZQ3KUsg%252Fu0TvMlLHplxuXP8%253D)

- Key-Value가 아닌 다양한 객체로 구성된 경우 Applicaiton의 복잡도를 줄이는 방향으로 Sharding하는 방법
- 관계가 있는 Entity끼리 같은 Shard 내에 공유하도록 만든 방식
- 단일 Shard 내에서 쿼리가 효율적
- 단일 Shard 내에서 강한 응집도를 가진다
- 다른 Shard의 Entity와 연관되는 경우 비효율적
- 사용자가 늘어남에 따라 확장성이 좋은 Partitioning
- cross-partition 쿼리는 single partition 쿼리보다 consistency의 보장과 성능을 잃음

---

# Query Offloading과 CDC 정리

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F99A48B485B7ACF9D22)

## 1. Query Offloading 개요
- **목적**: DB 성능 향상, 특히 처리량(Throughput) 증가를 위한 설계 기법
- **특징**: DB 트랜잭션 중 70~90%는 **READ(조회)**, 10~30%는 **Create/Update/Delete**
- **핵심 아이디어**: **쓰기(Write)** 와 **읽기(Read)** 트랜잭션을 분리

---

## 2. 구조
- **Master DB**: 쓰기(Update) 전용
- **Staging DB**: Master의 데이터를 중간에서 Slave로 복제하기 위한 중간 거점
- **Slave DB (N개)**: 읽기(Read) 전용

```
Application
  ├── 쓰기(Write) → Master DB
  └── 읽기(Read)  → Slave DB (Load Balancing + HA)
Master → Staging → N개의 Slave DB
```

---

## 3. Application 측 고려사항
- **쓰기 로직과 읽기 로직 분리**
- **DB Connection 분리** (쓰기용, 읽기용)
- **Connection Pool 관리**
  - Slave DB는 여러 개 → **Load Balancing 필요**
  - 장애 발생 시 다른 Slave로 연결할 수 있도록 **High Availability(HA)** 필요
- Load Balancing과 HA는 Connection Pool 또는 DBMS JDBC Driver에서 지원 가능

---

## 4. Staging DB의 역할
- Master DB의 부하를 줄이기 위해 **중간 복제 거점** 역할 수행
- Master DB가 직접 다수의 Slave DB로 복제하면 성능 저하 발생 → 이를 방지하기 위해 Staging DB가 대신 담당

---

## 5. 복제 기술: CDC (Change Data Capture)
- **원리**: DB의 BackLog(쓰기 요청 기록)를 기반으로 데이터 변경 사항을 추적 및 복제
- **과정**
  1. Source DB에서 Insert/Update/Delete 수행 → BackLog에 기록
  2. CDC가 BackLog를 읽음
  3. Target DB에 동일 작업을 **Replay** (예: insert A, insert B, insert C)

- **대표 제품**
  - Oracle Golden Gate
  - Quest Share Flex
  - 오픈소스: Galera

---

# Optional . . .

## 1) 한눈에 보기
- **Sharding(샤딩)**: 큰 테이블/데이터를 **여러 DB로 나눠서 저장**(수평 분할). 보통 `키 → 샤드` 규칙으로 라우팅.
- **Master/Slave(Primary/Replica)**: **쓰기(Primary)**와 **읽기(Replica)** 역할을 분리해 **읽기 성능/가용성**을 올림.

---

## 2) 왜 쓰나?
- **샤딩**: 데이터가 너무 크거나 **쓰기 트래픽**이 많을 때, **저장/쓰기 성능을 수평 확장**하기 위해.
- **프라이머리/리플리카**: **읽기 요청**이 많을 때, 읽기를 여러 대로 **분산**하고 장애 시 **대체(HA)**를 위해.

---

## 3) 핵심 차이 표

| 구분 | Sharding | Master/Slave(Primary/Replica) |
|---|---|---|
| 목적 | **데이터/쓰기 확장** | **읽기 확장·가용성** |
| 방식 | 샤드 키로 **데이터 분할** | Primary에서 변경 → Replica로 **복제** |
| 장점 | 저장용량·쓰기 처리량 확장 | 읽기 성능 향상, 장애 대응 |
| 단점 | 크로스 샤드 JOIN/트랜잭션 어려움, 리밸런싱 비용 | 복제 지연 → **Read-after-Write 불일치** 가능, 쓰기는 1대 한계(멀티 프라이머리 제외) |

---

## 4) 구조 그림 (ASCII)

**Sharding**
```
Client → Router → Shard0(DB0)
                → Shard1(DB1)
                → Shard2(DB2)
(예: userId % N 으로 분배)
```

**Primary/Replica**
```
           ┌─ Replica1 (read)
Client → Primary (write)
           └─ Replica2 (read)
```

**둘 다 함께 쓰기(현업 표준)**
```
          [Shard0]  Primary ─ Replicas
Router →  [Shard1]  Primary ─ Replicas
          [Shard2]  Primary ─ Replicas
```

---

## 5) 언제 무엇을 쓰나? (간단 체크리스트)

- **읽기가 훨씬 많다** → 먼저 **Primary/Replica**(읽기 분리).
- **쓰기도 많아 병목** → **Sharding** 고려(키 설계가 성공의 8할).
- **둘 다 필요** → “**샤드당 Primary/Replica**” 구조로 병행.

---

## 6) 샤딩 핵심 포인트

- **샤드 키 선택**:  
  - 높은 **카디널리티** & **균등 분포**(Hot Shard 방지)  
  - 트래픽 흐름과 **자연스러운 정렬**: 예) 금융은 `accountId`, 게임은 `roomId`
- **크로스 샤드 이슈**: JOIN/집계/트랜잭션은 비용 큼 →  
  - 가능하면 **한 샤드 안에서 처리**되도록 모델링  
  - 분산 트랜잭션 대신 **Saga/Outbox + 멱등 재시도** 등 패턴 활용
- **리밸런싱**: 샤드 수 변경 시 데이터 이동 필요 → **계획/툴링** 필수

---

## 7) Primary/Replica 핵심 포인트

- **복제 모드**: 비동기 ↔ 세미동기 ↔ 동기 (지연 vs 일관성 트레이드오프)
- **읽기 일관성**: 방금 쓴 데이터를 바로 읽어야 하면 **Primary 강제 읽기** 또는 **지연 허용**
- **Failover**: Primary 장애 시 **자동 승격** 전략과 **클라이언트 재시도** 설계

---

## 8) 예시로 이해하기

- **게임방(실시간)**: `roomId` 샤딩 + 방 단위 세션 고정(예: 컨시스턴트 해시). 서버 간 상태 동기화는 Pub/Sub.
- **금융(계좌 중심)**: `accountId` 또는 `customerId` 샤딩으로 쓰기 분산, 조회는 Replica.  
  Read-after-Write 필요한 경로는 Primary 읽기/캐시 일관성 정책 적용.

---

## 9) 운영 팁

- **모니터링**: 샤드 불균형, 복제 지연, 실패율을 대시보드로 상시 확인(Prometheus/Grafana 등)
- **테스트**: 리밸런싱/Failover **리허설** 정례화
- **스키마 변경**: 샤딩 환경에서는 **점진적 마이그레이션**(dual write/expand-contract) 원칙

---

## 10) 용어 요약
- **Sharding**: 데이터 수평 분할로 **쓰기/용량** 확장
- **Primary/Replica**: 복제로 **읽기/가용성** 확장
- **Hot Shard**: 특정 샤드로 트래픽이 몰리는 상태
- **Read-after-Write**: 쓰기 직후 읽기 일관성 요구

> 한 줄 요약: **읽기 분산은 Replica, 쓰기·용량 확장은 Sharding, 대부분은 둘을 함께**.