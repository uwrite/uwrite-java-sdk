# Uwrite java SDK

### Installation
Deploy jar to your local maven repo:
```
$ gradlew install
```


To run integration tests:
```
$ gradlew itest -Dkey=<apikey> -Dsecret=<apisecret>
```

### Usage

Dependency: 
```
<dependency>
    <groupId>com.uwrite</groupId>
    <artifactId>uwrite-java-sdk</artifactId>
    <version>1.0</version>
</dependency>
```

Get APIKey & APISecret https://uwrite.proctoru.com/profile/apisettings

```java
public class Main {
    
	public static void main(String[] args) throws InterruptedException{
        Uwrite uwrite = new Uwrite("xxx", "yyy");
        UFile uFile = uwrite.uploadFile(file, format, name);
        UCheck uCheck = uwrite.createCheck(uFile.getId(), UType.WEB, null, null, null);
        
        System.out.println(uCheck);
    }
    
}
```


