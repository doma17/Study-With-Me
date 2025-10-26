# 💰 최소 비용 알고리즘 (Minimum Cost Algorithms)

---

## 🎯 개념 요약

**최소 비용(Minimum Cost)** 알고리즘은 그래프에서 모든 정점을 연결하는 데 필요한 **간선들의 총 비용이 최소**가 되도록 하는 방법을 말한다.
즉, 그래프 내에서 **사이클이 없고 모든 노드가 연결된 최소 비용 트리**를 만드는 문제이며, 이를 **최소 신장 트리(MST, Minimum Spanning Tree)** 라고 부른다.

---

# 1️⃣ 최소 신장 트리 (MST, Minimum Spanning Tree)

### 💡 정의

* 그래프의 모든 정점을 연결하는 부분 그래프 중, **간선의 합이 최소인 트리**.
* **사이클이 없어야 함 (트리 구조)**.
* **모든 정점이 연결되어야 함 (Spanning).**

대표 알고리즘: **Kruskal(크루스칼)**, **Prim(프림)**

---

# 2️⃣ 크루스칼 알고리즘 (Kruskal’s Algorithm)

### 💡 개념

> 간선을 **가중치 기준으로 오름차순 정렬**한 뒤,
> **사이클이 생기지 않게** 하나씩 선택하여 MST를 만든다.

### ⚙️ 핵심 아이디어

* 간선 중심(Greedy) 접근법
* **Union-Find**(서로소 집합)를 이용해 사이클 여부를 판단

---

### 💻 코드 (Java)

```java
import java.util.*;

class Edge implements Comparable<Edge> {
    int from, to, cost;
    Edge(int from, int to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
    public int compareTo(Edge o) {
        return this.cost - o.cost;
    }
}

public class Kruskal {
    static int[] parent;

    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) parent[b] = a;
    }

    static int kruskal(int v, List<Edge> edges) {
        Collections.sort(edges);
        parent = new int[v + 1];
        for (int i = 1; i <= v; i++) parent[i] = i;

        int total = 0;
        for (Edge e : edges) {
            if (find(e.from) != find(e.to)) {
                union(e.from, e.to);
                total += e.cost;
            }
        }
        return total;
    }
}
```

---

### 🧩 예시 흐름

```
간선 리스트 (비용 기준 정렬)
(1-2: 3), (2-3: 4), (1-3: 5)

1. (1-2) 선택 → 연결됨
2. (2-3) 선택 → 연결됨 (모든 정점 연결 완료)
3. (1-3) 제외 (사이클 발생)
결과: MST 비용 = 7
```

---

### ⚙️ 특징

| 항목     | 내용                        |
| ------ | ------------------------- |
| 접근 방식  | Greedy (간선 중심)            |
| 자료구조   | Union-Find (Disjoint Set) |
| 시간 복잡도 | O(E log E)                |
| 장점     | 구현 단순, 간선 위주 탐색           |
| 단점     | 간선이 매우 많을 경우 비효율적         |

---

# 3️⃣ 프림 알고리즘 (Prim’s Algorithm)

### 💡 개념

> **시작 정점 하나에서 출발**,
> 인접한 간선 중 **가장 비용이 작은 간선**을 선택하며 MST를 확장.

### ⚙️ 핵심 아이디어

* 정점 중심(Greedy) 접근법
* 방문하지 않은 정점 중 **최소 비용 간선**을 계속 선택

---

### 💻 코드 (Java)

```java
import java.util.*;

class Node implements Comparable<Node> {
    int vertex, cost;
    Node(int vertex, int cost) {
        this.vertex = vertex;
        this.cost = cost;
    }
    public int compareTo(Node o) {
        return this.cost - o.cost;
    }
}

public class Prim {
    static List<List<Node>> graph = new ArrayList<>();
    static boolean[] visited;

    static int prim(int start, int v) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        visited = new boolean[v + 1];

        int total = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (visited[cur.vertex]) continue;
            visited[cur.vertex] = true;
            total += cur.cost;

            for (Node next : graph.get(cur.vertex)) {
                if (!visited[next.vertex]) {
                    pq.offer(next);
                }
            }
        }
        return total;
    }
}
```

---

### 📊 작동 흐름 예시

```
시작 정점: 1
1 → 2 (비용 3)
2 → 3 (비용 4)

총 비용 = 7
```

---

### ⚙️ 특징

| 항목     | 내용                        |
| ------ | ------------------------- |
| 접근 방식  | Greedy (정점 중심)            |
| 자료구조   | PriorityQueue             |
| 시간 복잡도 | O(E log V)                |
| 장점     | 조밀한 그래프(Dense)에 효율적       |
| 단점     | 희소 그래프(Sparse)에서는 다소 비효율적 |

---

# 4️⃣ 크루스칼 vs 프림 비교

| 구분      | 크루스칼             | 프림             |
| ------- | ---------------- | -------------- |
| 접근 단위   | 간선 중심            | 정점 중심          |
| 구현 자료구조 | Union-Find       | Priority Queue |
| 시간 복잡도  | O(E log E)       | O(E log V)     |
| 그래프 유형  | 희소 그래프(Sparse)   | 조밀 그래프(Dense)  |
| 대표 사용 예 | 도로 네트워크, 연결선 최소화 | 네트워크 케이블 연결    |

---

# 🧭 핵심 요약

1. **MST란?** 모든 노드를 연결하는 최소 비용의 트리.
2. **크루스칼:** 간선 중심, Union-Find로 사이클 방지.
3. **프림:** 정점 중심, 최소 비용 간선으로 확장.

> ✅ 모든 최소 비용 알고리즘의 핵심은 **“사이클 방지 + 최소 선택”**이다.

---

# 🧩 응용 포인트

* 전력망, 도로, 통신망 구축 비용 절감 문제에 활용.
* 크루스칼은 **분리된 네트워크 연결**, 프림은 **기존 네트워크 확장**에 유리.

> 💡 사고 포인트: “지금까지 연결된 구조를 유지하면서, 가장 싸게 전체를 연결하라.”
