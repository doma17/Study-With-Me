# Clustered index
## Clustered index 란?
InnoDB에서 기본 키(Primary Key) 가 곧 Clustered Index 다. 테이블의 실제 데이터(레코드)가 Clustered Index의 Leaf Node에 저장된다. 즉, 데이터 자체가 인덱스에 정렬된 형태로 붙어 있어 데이터를 조회할 때 인덱스를 따로 거치지 않고 데이터가 바로 나온다.

## 구조
InnoDB는 모든 인덱스를 B+Tree 로 구현한다. Clustered Index의 리프 노드(Leaf Node)에는 해당 행(Row) 전체가 저장된다. 그래서 리프 노드에 도달하는 순간, 곧바로 데이터 행을 읽을 수 있다. Clustered Index는 항상 Primary Key 순서대로 정렬되어 저장되어 범위 검색(range scan) 이 매우 빠르다.

## 생성 규칙
1. InnoDB에서 Clustered Index는 반드시 하나만 존재한다.
2. 명시적인 PRIMARY KEY가 있으면 → 그것이 Clustered Index
3. PRIMARY KEY가 없고 UNIQUE NOT NULL 인덱스가 있으면 → 그것이 Clustered Index
4. 둘 다 없으면 → InnoDB가 내부적으로 숨겨진 6바이트 Row ID 를 생성해서 Clustered Index로 사용

즉, PK를 잘못 정의하거나 빼버리면 성능에 영향을 줄 수 있음

## 장점
- 데이터 접근 속도가 빠름
- 리프 노드 자체에 데이터가 있어서 인덱스 조회 후 바로 결과 반환 가능
- 범위 검색 효율적
- PK 정렬을 유지하므로 BETWEEN, >, < 같은 조건에서 순차 스캔이 매우 빠름
- PK 기반 조인 최적화
- 다른 테이블에서 FK → PK 조인 시 성능 유리
## 단점
- INSERT 비용 증가
- 항상 PK 순서대로 정렬 저장 → 중간에 삽입되면 페이지 분할(Page Split) 발생 가능
- Auto Increment PK가 선호되는 이유
- PK 크기가 곧 다른 인덱스 크기 증가
- 모든 Secondary Index 리프 노드에는 PK 값이 포함됨. 따라서 PK 컬럼이 크면(예: UUID, 긴 문자열) 모든 인덱스 크기도 커져버림
- PK 변경 비용이 큼
- Clustered Index는 데이터 자체의 저장 순서를 결정하므로, PK를 바꾸면 레코드를 물리적으로 재배치해야 함

# Non-Clustered index (보조 인덱스, Secondary Index)
## Non-Clustered index
Primary Key(Clustered Index) 가 아닌 컬럼에 대해 생성되는 인덱스. 리프 노드에는 실제 데이터가 저장되지 않고, 대신 해당 레코드의 PK 값이 저장됨. 따라서 실제 데이터를 읽으려면, PK 값을 이용해 다시 Clustered Index를 찾아야 함 → 이 과정을 Double Lookup 또는 Bookmark Lookup 이라고 부름

## 구조
- Secondary Index: 리프 노드 = (인덱싱된 컬럼 값 + PK 값)

- 데이터를 찾는 과정:
    1. email = 'bob@test.com' → Secondary Index 검색
    2. 리프 노드에서 PK(id=2) 획득
    3. Clustered Index 탐색 → 실제 레코드 반환 ((2, Bob, bob@test.com))

## 장점
- 여러 개 생성 가능
    - 한 테이블에 PK는 1개지만, Secondary Index는 원하는 만큼 만들 수 있음
    - 다양한 검색 조건 최적화 가능 (WHERE name = ?, WHERE email = ?)
- 조건 검색 성능 향상
    - 전체 테이블 스캔(Full Table Scan) 대신 인덱스를 통해 빠르게 후보 레코드 찾기
- 커버링 인덱스(Covering Index) 활용 가능
    - 쿼리에 필요한 컬럼이 전부 Secondary Index에 들어 있다면, Clustered Index까지 가지 않고 인덱스에서 바로 결과 반환 가능

예: SELECT email FROM users WHERE email='bob@test.com';
→ idx_email 만으로 처리

## 단점
- Double Lookup 비용 발생
    - 대부분의 경우, 인덱스에서 PK를 찾고 다시 Clustered Index로 접근해야 함 → 랜덤 I/O 증가
    - 즉, PK 기반 검색보다 느림
- 인덱스 크기 증가
    - 리프 노드에 PK가 항상 포함됨
    - PK가 길면(예: UUID, 복합 키) Secondary Index 크기도 커짐
- DML(INSERT/UPDATE/DELETE) 비용 증가
    - 인덱스는 데이터와 별도 B+Tree 구조이므로, 레코드 변경 시 인덱스도 함께 갱신해야 함
    - 특히 인덱스가 많으면 DML 성능 저하