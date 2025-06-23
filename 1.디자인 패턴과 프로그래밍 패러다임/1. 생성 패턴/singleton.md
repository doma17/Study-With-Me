# 싱글턴 팬턴이란?

싱글턴(Singleton) 패턴은 특정 클래스의 인스턴스가 프로그램 전체에서 단 하나만 존재하도록 보장하는 디자인 패턴이다.


그렇다면 왜 객체를 하나만 만들도록 해야 할까? 예를 들어 스레드 풀, 캐시, 사용자 설정을 처리하는 객체처럼 공유되어야 하는 자원은 하나의 인스턴스로만 관리해도 충분하다. 오히려 이런 객체가 여러 개 생성되면 불필요한 자원 낭비나 예상치 못한 충돌이 발생할 수 있다.
그렇다면 싱글턴은 어떻게 구현할 수 있을까?

가장 단순한 방법은 전역 변수로 객체를 생성하는 것이다. 하지만 싱글턴으로 만들려는 객체가 무거운 자원을 사용하는 객체라면, 프로그램이 종료될 때까지 한 번도 사용되지 않더라도 메모리를 차지하게 된다. 즉, 사용되지도 않을 객체가 미리 생성되어 자원을 낭비하는 문제가 생길 수 있다.
이러한 점까지 고려해, 싱글턴 패턴을 구현해보자.

```java
class Singleton {
    private static Singleton uniqueInstance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        
        return uniqueInstance;
    }
}
```
- 생성자를 private 으로 선언했으므로 Singleton 에서만 클래스의 인스턴스를 만들 수 있다.
- getInstance() 메서드는 클래스의 인스턴스를 만들어서 리턴한다.

아직 인스턴스가 만들어지지 않았다면 private 으로 선언된 생성자를 사용해서 Singleton 객체를 만든 다음 uniqueInstance에 그 객체를 대입한다. 이러면 인스턴스가 필요한 상황이 닥치기 전까지 아예 인스턴스를 생성하지 않게 된다. 이런 방법을 lazy initialization 이라고 한다.

- 싱글턴 패턴을 실제로 적용할 때는 클래스에서 하나뿐인 인스턴스를 관리하도록 만들면 됩니다. 그리고 다른 어떤 클래스에서도 자신의 인스턴스를 추가로 만들지 못하게 해야한다. 인스턴스가 필요하다면 반드시 클래스 자신을 거치도록 해야한다.
- 어디서든 그 인스턴스에 접근할 수 있도록 전역 접근 지점을 제공한다. 언제든 이 인스턴스가 필요하면 요청할 수 있게 만들어 놓고, 요청이 들어오면 그 하나뿐인 인스턴스를 건네주도록 만들어야 한다.

# 멀티스레딩 문제

만약 2개 이상의 스레드에서 ```getInstance()``` 에 접근한다고 해보자.
```java
public static Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        
        return uniqueInstance;
    }
```
아직 ``` uniqueInstance```가 null 일 때 2개의 스레드가 ```getInstance()```에 접근하게 되면 2개의 다른 Singleton 객체가 만들어진다. 이렇게 되면 단 하나의 객체만 존재한다는 싱글턴 패턴이 깨지게 된다. 이 문제를 해결하는 방법은 사실 간단하다.

```java
public static synchronized Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }
        
        return uniqueInstance;
    }
```
```synchronized``` 키워드를 이용해 하나의 메서드만 ```getInstance()``` 에 접근할 수 있도록 동기화했다. 이렇게 보면 문제가 해결된 것 같지만 동기화 할 때 속도 문제가 생긴다.
만약 ```getInstance()```의 속도가 중요하지 않다면 그냥 놔두면 되겠지만 성능이 중요하다면 다른 방법을 생각해야 한다.

# 효율적으로 멀티스레딩 문제 해결하기

## DCL(Double-Checked Locking)
인스턴스가 생성되어 있는지 확인한 다음 생성되어 있지 않을 때만 동기화할 수 있다. 이러면 처음에만 동기화하고 나중에는 동기화하지 않아도 된다.
```java
class Singleton {
    private static Singleton uniqueInstance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            synchronized (Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        
        return uniqueInstance;
    }
}
```
# 문제점
1. JVM에서는 클래스 로더마다 별도의 네임스페이스를 가지므로, 같은 클래스라도 서로 다른 클래스 로더가 로드하면 서로 다른 클래스로 취급된다. 이로 인해 싱글턴 패턴으로 구현했더라도, 클래스 로더가 여러 개라면 인스턴스가 여러 개 생길 수 있다. 이러한 문제는 주로 웹 애플리케이션 서버 환경에서 발생하며, 클래스 로더를 명시적으로 관리하거나, 싱글턴을 공유할 수 있는 상위 클래스 로더에서 로드하도록 설계해야 한다.
2. 리플렉션, 직렬화, 역직렬화에 문제가 발생할 수 있다.
3. 느슨한 결합 원칙에 위배된다.
4. SRP(single responsibility principle) 원칙에 위배된다.
# enum 활용
지금까지 이야기한 동기화 문제, 리플렉션, 직렬화/역직렬화로 인한 인스턴스 생성 문제 등은 enum을 사용한 싱글턴 구현으로 대부분 해결할 수 있다. 다만, 클래스 로더가 분리된 환경에서는 여전히 인스턴스가 여러 개 생성될 수 있으므로 주의가 필요하다.
```java
public enum SingletonEnum {
    INSTANCE
}

public class Singleton {

    private Singleton() {
    }

    public static SingletonEnum getInstance() {
        return SingletonEnum.INSTANCE;
    }
}
```
enum은 JVM이 클래스 로딩 시 인스턴스를 생성하므로 자동으로 thread-safe 하다. 또한 enum은 리플렉션으로 인스턴스를 생성할 수 없다.

# TMI
위에 적은 문제점 외에도 다양한 문제가 있어 싱글턴은 안티패턴(Anti-pattern) 으로 간주되기도 한다. 문제를 해결하는 것처럼 보이지만, 실제로는 더 많은 문제가 발생하기 때문이다. 어떤 문제가 있을까?
1. 테스트가 어렵다 
   - 싱글턴 객체는 전역 상태를 가지므로, 테스트 간 격리가 어렵다.
   - 상태가 남아있어 테스트 결과에 영향을 줄 수 있고, Mocking이나 Stub으로 대체가 어렵다.
2. 강한 결합
   - 싱글턴 객체를 직접 호출하거나 내부에서 생성하면, 해당 클래스는 특정 구현체에 의존하게 된다.
   - 인터페이스 분리나 DI(의존성 주입)를 적용하기 어려워 확장성과 유연성이 떨어진다.
3. 생명주기 관리가 어렵다
   - 직접 생성하는 싱글턴 객체는 언제 생성되고 언제 소멸되는지 제어하기 어렵다.
   - 특히 멀티스레드 환경에서는 동기화, 초기화 시점 등에서 오류가 발생할 가능성이 있다.
4. 숨겨진 의존성
   - 코드상에서 Singleton.getInstance() 와 같이 호출하면, 해당 클래스가 외부 의존성이 있는지 명확히 드러나지 않아 코드의 의도와 책임이 모호해진다.
5. 클래스 로더 이슈
   - 같은 클래스라도 서로 다른 클래스 로더에서 로드되면 서로 다른 객체로 간주되어 싱글턴이 깨진다.
   - 웹 애플리케이션 서버에서 war 파일이 여러개 배포되는 구조에서 주로 발생
6. 리플렉션, 직렬화/역직렬화 문제
   - 리플렉션을 이용하면 private 생성자도 호출 가능해 인스턴스가 여러개 생성될 수 있으며, 직렬화-역직렬화 시에도 동일한 문제가 발생한다.

Spring Boot는 객체를 싱글턴으로 관리하되, 위와 같은 문제들을 해결하기 위해 컨테이너 기반의 생명주기 관리, 의존성 주입, 테스트 지원 등의 기능을 제공한다.

## Spring Boot에서 싱글턴의 문제점을 해결하는 방법
Spring Boot는 객체를 직접 싱글턴으로 구현하지 않고, ```Spring 컨테이너(ApplicationContext)```가 대신 객체를 생성하고 생명주기를 관리한다. 
이 컨테이너는 기본적으로 모든 Bean을 싱글턴 범위로 관리하지만, 직접 싱글턴 패턴을 구현할 때 발생하는 여러 문제점들을 해결할 수 있도록 설계되어 있다.
1. 의존성 주입(DI)으로 결합도 완화
   - Spring에서는 객체를 new로 생성하거나 Singleton.getInstance() 와 같이 가져오지 않고 컨테이너가 주입한다.
      - 객체간의 강한 경합을 피할 수 있다.
      - 추후 테스트나 기능 확장 시 구현체만 교체하면 되기 때문에 유연한 설계가 가능하다
2. 테스트 격리성 확보
   - DI로 객체 간 의존성이 명확해지고, 테스트 시 주입되는 객체를 변경할 수 있음.
      - 테스트 간 상태 공유로 인한 문제 발생을 방지한다.
      - 각 테스트가 독립적으로 수행될 수 있다.
      - @MockBean, @TestConfiguration 같은 Spring의 테스트 도구들을 활용해 테스트 전용 객체를 주입할 수 있다.
3. 생명주기 제어
   - Spring 은 Bean의 생성 시점부터 소멸 시점까지 관리
      - ```@PostConstruct```, ```@PreDestroy```를 통해 초기화 및 종료 로직을 처리할 수 있다.
      - 필요 시 ```@Scope("prototype")``` 등으로 객체 범위를 조정할 수도 있어 싱글턴 외의 다양한 패턴도 적용 가능하다.
4. 클래스 로더 이슈 방지
   - Spring은 클래스 로딩과 Bean 관리를 컨테이너 중심으로 통일해서 처리하므로 war 파일이 여러개 배포되거나 클래스 로더가 복잡한 환경에서도 싱글턴이 깨지는 문제를 예방할 수 있다.
5. 숨겨진 의존성 제거
   - Singleton.getInstance()처럼 객체 내부에서 직접 의존성을 생성하지 않고, 모든 의존성을 외부에서 주입받도록 강제함으로써 코드의 흐름과 책임이 명확해진다. 이는 유지보수성과 가독성 향상으로 이어진다.
6. 리플렉션, 직렬화-역직렬화 이유 해소
   - Spring 컨테이너가 객체 생성을 중앙에서 통제한다
      - 리플렉션을 통해 객체가 무문별하게 생성되는 것을 방지한다.
      - 직렬화-역직렬화로 인해 새로운 인스턴스가 생기는 문제를 방지할 수 있다.
