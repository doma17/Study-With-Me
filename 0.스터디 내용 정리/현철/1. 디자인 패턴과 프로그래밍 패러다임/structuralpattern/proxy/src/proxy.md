# 프록시 패턴이란?
어떤 객체에 대한 접근 제어를 위해 대리 객체(프록시)를 두는 구조. 프록시는 실제 객체를 대신해서 클라이언트의 요청을 받아 처리하거나, 요청을 가로채어 추가 로직을 수행할 수 있다.

# 예제
```RMI(Remote Method Invocation)```는 Java에서 제공하는 기술로 원격에 있는 객체의 메서드를 로컬에서 호출하듯 사용할 수 있게 해준다. 쉽게 말해 다른 컴퓨터에 있는 객체의 메서드를 내 코드처럼 호출할 수 있다.
이 과정에서 중요한 역할을 하는 것이 바로 프록시다. 클라이언트는 원격 객체의 실제 구현을 모른 채, 프록시 객체를 통해 메서드를 호출한다.
```java
public interface FileServer extends Remote {
    void uploadFile(byte[] file, String fileName) throws RemoteException, IOException;
    void sendMessage(String userName, String message) throws RemoteException, IOException;
    String fileList() throws RemoteException;
}
```
```Remote``` 를 상속한 이 인터페이스는 RMI로 호출될 수 있는 메서드의 목록을 정의한다.
클라이언트와 서버는 이 인터페이스를 공유하며 이를 통해 통신하다.
```java
public class FileServerImpl extends UnicastRemoteObject implements FileServer {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(FileServer.class.getName());

    public FileServerImpl() throws RemoteException {
    }

    @Override
    public void uploadFile(byte[] file, String fileName) throws RemoteException, IOException {
        File f = new File("./upload/" + fileName);
        FileOutputStream out = new FileOutputStream(f);
        System.out.println("start file upload");

        out.write(file);

        out.close();
        logger.info("Uploaded file: " + f.getName());
    }

    @Override
    public void sendMessage(String userName, String message) throws RemoteException, IOException {
        logger.info("user name: " + userName + "\n" + "message: " + message);
    }

    @Override
    public String fileList() throws RemoteException {
        File folder = new File("./upload");
        StringBuilder sb = new StringBuilder();

        int index = 1;

        for (String s : folder.list()) {
            sb.append(index++).append(". ").append(s).append("\n");
        }

        return sb.toString();
    }
}
```
FileServerImpl 클래스는 실제 파일을 업로드하고 메시지를 출력하는 기능을 수행한다. 서버 측 코드로 ```UnicastRemoteObject```를 상속하여 RMI를 호출을 받을 수 있는 원격 객체가 된다.
```java
public class Main {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname","127.0.0.1");
            FileServer service = new FileServerImpl();
            Naming.rebind("rmi://127.0.0.1/fileUpload", service);
            System.out.println("File server is running...");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```
서버는 RMI 레지스트리에 fileUpload 라는 이름으로 객체를 등록한다. 이제 클라이언트는 이 이름으로 서버 객체에 접근할 수 있다.
```java
public class FileClient {
    public static byte[] fileToBytes(File file) throws IOException {

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
            return fileBytes;
        }
    }

    public static void main(String[] args) throws NamingException, IOException, NotBoundException {

        FileServer fileClient = (FileServer) Naming.lookup("rmi://127.0.0.1/fileUpload");

        File file = new File("/Users/phc/development/080258/ch04/exercises/image-gallery/Dockerfile");
        byte[] fileBytes = fileToBytes(file);
        fileClient.uploadFile(fileBytes, file.getName());

        file = new File("/Users/phc/development/080258/ch08/exercises/numbers/docker-compose.yml");
        fileBytes = fileToBytes(file);
        fileClient.uploadFile(fileBytes, file.getName());

        fileClient.sendMessage("hash", "done");

        System.out.println(fileClient.fileList());
    }
}
```
```Naming.lookup```을 통해 반한된 fileClient 객체는 사실 ```FileServerImpl``` 객체가 아닌 RMI가 자동으로 생성한 프록시다. 클라이언트는 이 프록시 객체에서 메서드를 호출하고, 프록시는 네트워크를 통해 서버의 실제 객체로 요청을 전달한다.

클라이언트는 프록시 객체(fileClient)를 통해서만 서버에 접근한다. 프록시는 RMI 시스템 내부에서 자동 생성되며, 네트워크 통신과 직렬화, 예외 처리 등 여러 작업을 대신 수행한다. ```FileServerImpl```에 직접 전근하지 않고 인터페이스 기반의 간접적인 호출 구조를 따른다.

# 장점
- 접근 제어 : 서버 리소스 보호, 인증 등의 작업을 프록시에서 수행 가능
- 네트워크 투명성 : 로컬 호출처럼 보이지만 실제로는 원격에서 동작
- 유지보수 용이성 : 클라이언트와 서버의 결합도 낮아짐

# 마무리
프록시 패턴은 단순한 대리 호출만이 아니라, 실제 시스템에서 원격 객체 호출, 보안 필터링, 캐시 처리, 지연 로딩 등 다양한 분야에서 활용되고 있다. 

