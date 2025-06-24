# 1. MVC (Model - View - Controller)
## 구조
 - Model : 비즈니스 로직, 데이터 처리 담당
 - View : 사용자 인터페이스
 - Controller : 사용자 입력을 처리. Model 과 View 를 연결하는 중간 다리

## 흐름
사용자 입력 -> Controller -> Model -> View 업데이트


```java
// Model
public class UserModel {
    public String getMessage() {
        return "Hello, User!";
    }
}

// View
public class UserView {
    public void displayMessage(String message) {
        System.out.println(message);
    }
}

// Controller
public class UserController {
    private UserModel model;
    private UserView view;

    public UserController(UserModel model, UserView view) {
        this.model = model;
        this.view = view;
    }

    public void onButtonClick() {
        String message = model.getMessage();
        view.displayMessage(message);
    }
}

// 클라이언트
public class Main {
    public static void main(String[] args) {
        UserModel model = new UserModel();
        UserView view = new UserView();
        UserController controller = new UserController(model, view);

        controller.onButtonClick();
    }
}

```

## 장점
 - 구조가 단순하고 이해하기 쉬움 : 코드가 명확하게 분리되어 있어 가독성이 높고, 오류 발생 시 해당 영역만 디버깅
 - 입력, 비즈니스, UI 로직이 각각 분리되어 있어 유지보수와 확장이 용이
 - 다양한 프레임워크에서 기본적으로 지원

## 단점
 - View 와 Controller 간의 결합도가 높아짐
 - 애플리케이션이 복잡해질수록 Controller가 비대화

## 사용 사례
 - Java의 Spring MVC

# 2. MVP (Model - View - Presenter)
## 구조
 - Model : 데이터와 비즈니스 로직
 - View : 사용자 UI
 - Presenter : View 와 Model 사이의 모든 로직 담당

## 흐름
View -> Presenter -> Model -> Presenter -> View 업데이트

```java
// Model
public class UserModel {
    public String getWelcomeMessage() {
        return "Welcome, MVP!";
    }
}
```
데이터를 제공하는 역할. 서버 요청이나 DB에서 데이터를 가져오는 코드가 들어가는 자리.
```java
// View
public interface UserView {
    void showMessage(String message);
}
```
사용자에게 보여줄 UI 동작 정의. View는 인터페이스로 정의되어 있어 실제 UI 구현체를 Presenter가 몰라도 됨.
```java
// Presenter
public class UserPresenter {
    private UserModel model;
    private UserView view;

    public UserPresenter(UserModel model, UserView view) {
        this.model = model;
        this.view = view;
    }

    public void onLoginButtonClicked() {
        String msg = model.getWelcomeMessage();
        view.showMessage(msg);
    }
}
```
View와 Model 사이의 중간 다리. 버튼이 클릭되었을 떄 어떤 처리를 할지 결정하고 UI를 어떻게 바꿀지도 Presenter가 담당.
```java
// 실제 View
public class ConsoleUserView implements UserView {
    public void showMessage(String message) {
        System.out.println(message);
    }
    
    // 실제 콘솔 UI 구현체

    public static void main(String[] args) {
        UserModel model = new UserModel();
        ConsoleUserView view = new ConsoleUserView();
        UserPresenter presenter = new UserPresenter(model, view);

        presenter.onLoginButtonClicked();
    }
}
```

## 장점
 - View 와 Model의 완전한 분리 : Presenter가 Model과 View 사이의 중재자 역할을 하면서, View와 Model 간의 의존성이 없어짐
 - UI 로직과 비즈니스 로직의 명확한 분리 : View는 UI에만 집중하고, Presenter가 비즈니스 로직을 처리해 코드의 가독성과 유지보수성이 높아짐
 - 확장성 및 유연성 : 새로운 기능 추가 시 관련 부분만 수정하면 되며, 각 컴포넌트의 역할이 명확해 확장이 용이
 - 모듈화 및 재사용성 : Presenter를 통해 모듈화가 잘 이루어져, 일부 코드를 다른 뷰에서 재사용할 수 있음

## 단점
 - View와 Presenter의 강한 의존성 : View와 Presenter가 1:1 관계로 강하게 결합되어 있어, 앱이 커질수록 Presenter가 많아지고 관리가 어려워질 수 있음
 - Presenter의 복잡성 증가 : Presenter가 View와 Model 사이의 모든 중재를 담당하므로, 대규모 앱에서는 Presenter의 코드가 복잡해질 수 있음
 - 유지보수 부담 : View가 많아질수록 Presenter도 많아지고, 구조가 복잡해져 유지보수에 부담이 될 수 있음

## 사용 사례
 - 안드로이드 개발

# 3. MVVM (Model - View - ViewModel)
## 구조
 - Model : 비즈니스 로직, 데이터
 - View : UI
 - ViewModel : View 를 위한 데이터와 상태를 제공하며 양방향 바인딩 지원

## 흐름
View <-> ViewModel <-> Model
ViewModel 은 Model 의 데이터를 가공하여 View 에 전달, View 의 사용자 입력도 ViewModel 을 통해 처리
```java
// Model
public class UserModel {
    public String getGreeting(String name) {
        return "Hello, " + name;
    }
}
```
데이터와 비즈니스 로직 담당. 실제 앱에서는 DB, API 통신 등의 로직이 들어갈 수 있음.

```java
import java.beans.PropertyChangeSupport;

// ViewModel
public class UserViewModel {
    private UserModel model = new UserModel();
    private String name;
    private String greeting;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    // View 와 Model을 연결하는 중간 역할
    // PropertyChangeSupport 는 Java에서 데이터 바인딩을 흉내내기 위한 이벤트 시스템
    
    public void setName(String name) {
        this.name = name;
        this.greeting = model.getGreeting(name);
        support.firePropertyChange("greeting", null, this.greeting);
    }
    // setName 을 호출하면 내부 상태가 바뀌고 Model 을 통해 새로운 데이터 생성하고 변경 이벤트 발생
    
    public String getGreeting() {
        return greeting;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    // view 가 ViewModel에 리스너를 등록해서 데이터를 구독할 수 있음.
    // 바뀐 greeting 을 가져오기 위해 getGreeting() 사용
}
```
```java
// View
public class MVVMExample {
    public static void main(String[] args) {
        UserViewModel vm = new UserViewModel();

        vm.addPropertyChangeListener(evt -> System.out.println("Updated Greeting: " + vm.getGreeting()));
        
        vm.setName("Alice");
        vm.setName("Bob");
    }
}
```
사용자가 이름을 입력한 것 처럼 ```setName()``` 을 호출하고 데이터가 바뀌면서 ViewModel이 이벤트 발송. View가 이를 감지해 UI 자동 갱신
## 장점
 - View와 Model이 서로를 직접적으로 알지 못하므로, 독립성을 유지
 - 데이터 바인딩을 통해 View와 ViewModel 간의 데이터 변화가 자동으로 반영되어, 코드 양이 줄고, 일관성 있는 UI 업데이트
 - 각 컴포넌트(Model, View, ViewModel)가 독립적으로 동작하므로, 유닛 테스트가 용이
 - UI 로직은 View, 비즈니스 로직은 ViewModel로 분리되어 코드의 가독성과 관리가 쉬워짐
 - ViewModel을 여러 View에서 재사용할 수 있고, 모듈화가 쉬워 유지보수와 확장성이 높아짐

## 단점
 - 데이터 바인딩, Observable 등 관련 개념을 이해해야 하므로 초보 개발자에게는 진입 장벽이 높을 수 있음
 - ViewModel 설계가 어렵고, 데이터 바인딩을 위한 추가적인 코드가 필요
 - 잘못 사용할 경우 메모리 소모가 크거나, 불필요한 UI 리빌드로 인해 성능 저하가 발생
 - 모든 플랫폼에서 동일한 수준의 데이터 바인딩 지원이 보장되지 않아, 호환성 문제가 있을 수 있음

## 사용 사례
 - 안드로이드 개발
 - IOS 및 SwiftUI