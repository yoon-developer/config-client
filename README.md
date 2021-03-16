Spring Cloud Config (Client)
==========

# 1. build.gradle 설정
> 버전
- spring cloud: 2020.0.1  
- spring-boot-starter-web: 2.4.3
- spring-boot-starter-actuator: 2.4.3  
- spring-cloud-starter-config: 3.0.2

> dependencies 추가
- spring-boot-starter-actuator (config refresh 기능)
- spring-cloud-starter-config (spring cloud config client)

```text
ext {
	set('springCloudVersion', "2020.0.1")
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-actuator'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.cloud:spring-cloud-starter-config'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

dependencyManagement {
	imports {
		mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
	}
}
```

# 2. config 작성
https://github.com/yoonDevGit/config-repository

> config 파일 이름 규약
```text
> application-profile-label.yml
> application-profile.yml
> label-application-profile.yml
> application-profile.properties
> label-application-profile.properties
```

> msa-dev.yml
```yaml
msa:
  value: "MSA Config"
```

# 3. Application.yml 설정

> config server 연동

- spring.application.name (applicationName 작성)
- spring.config.impoer (config server uri)
- spring.profiles.active (profileName 작성)

```yaml
spring:
  application:
    name: msa
  config:
    import: optional:configserver:http://localhost:8888
  profiles:
    active: dev
```

> actuator 설정

```yaml
management:
  endpoints:
    web:
      exposure:
        include: refresh
```

# 4. Test

> Test Code 작성
- @RefreshScope (refresh 기능)
- @Value("${msa.value}") (yaml value)

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {

  @Value("${msa.value}")
  private String value;

  @GetMapping("/value")
  public String getConfig() {
    return value;
  }
}
```

> Test
```text
[GET] http://localhost:8080/value
```

> Refresh
```text
[POST] localhost:8080/actuator/refresh
```