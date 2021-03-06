Template Jakarta MVC 1.0 untuk dipasang di Apache Tomcat 7.0.109 ke atas, dengan Eclipse Temurin Java JDK versi 17
Kode sumber bisa saja di _build_ dengan editor Eclipse, Netbeans, IntelliJ, Visual Studio Code, ataupun lewat command prompt/terminal dengan menjalankan perintah _gradlew war_
Setelah dibuild lalu berkas _ROOT.war_ akan tersedia di folder _build/libs/ROOT.war_
Berkas _ROOT.war_ bisa disalin ke folder webapps di dalam folder tomcat 7.0.109
Setelah tomcat dijalankan, silahkan akses laman web berikut: http://127.0.0.1:8080/barang

## Konfigurasi Projek
1. Di folder $CATALINA_HOME/lib, pustaka annotations-api.jar diganti [tomcat-annotations-api-8.0.53.jar](https://repo1.maven.org/maven2/org/apache/tomcat/tomcat-annotations-api/8.0.53/tomcat-annotations-api-8.0.53.jar) (JSR 250), pustaka jasper-el.jar diganti [tomcat-jasper-el-8.0.53.jar](https://repo1.maven.org/maven2/org/apache/tomcat/tomcat-jasper-el/8.0.53/tomcat-jasper-el-8.0.53.jar), dan pustaka el-api.jar diganti [tomcat-el-api-8.0.53.jar](https://repo1.maven.org/maven2/org/apache/tomcat/tomcat-el-api/8.0.53/tomcat-el-api-8.0.53.jar) (JSR 245)
2. Driver JDBC Oracle **[ojdbc11.jar](https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc11/21.5.0.0)** dan **[ManyUserRolesRealm](https://github.com/dawud-tan/ManyUserRolesRealm).jar** disalin ke $CATALINA_HOME/lib
3. Tambahkan `<Resource />` `OracleDataSourceFactory` dan `<ResourceLink />` ke blok `<Context />` di dalam berkas **webapp/META-INF/context.xml**
    ```xml
    <Resource name="jdbc/NamaJDBC" auth="Container"
              type="oracle.jdbc.pool.OracleDataSource" factory="oracle.jdbc.pool.OracleDataSourceFactory"
			  validationQuery="SELECT 1 FROM DUAL"
			  user="xxx" password="xxx"
			  driverClassName="oracle.jdbc.driver.OracleDriver"
			  connectionProperties="oracle.jdbc.J2EE13Compliant=true"
			  url="jdbc:oracle:thin:@//127.0.0.1:1521/contoh_nama_service" />

    <ResourceLink name="jdbc/NamaJDBC" global="jdbc/NamaJDBC" type="oracle.jdbc.pool.OracleDataSource"/>
    ```
4. Tambahkan blok `<Realm />` ke blok `<Context />` di dalam berkas **webapp/META-INF/context.xml**
    ```xml
    <Realm className="org.apache.catalina.realm.ManyUserRolesRealm" dataSourceName="jdbc/NamaJDBC" localDataSource="true" ... />
    ```
5. Definisikan Konfigurasi Login di web.xml
    ```xml
    <login-config>
        <auth-method>BASIC</auth-method>
        <realm-name>ManyUserRolesRealm</realm-name>
    </login-config>
    ```
6. Di berkas **src/main/resources/META-INF/persistence.xml**, tambahkan `<non-jta-data-source>java:/comp/env/jdbc/NamaJDBC</non-jta-data-source>` ke blok `<persistence-unit />`
    ```

## cara compile tanpa gradle

#### windows
```bat
"C:\Program Files\Eclipse Adoptium\jdk-17.0.2.8-hotspot\bin\javac" -cp "jakarta.enterprise.cdi-api-3.0.0.jar;jakarta.inject-api-2.0.0.jar;jakarta.mvc-api-2.0.0.jar;jakarta.servlet-api-5.0.0.jar;jakarta.validation-api-3.0.0.jar;jakarta.ws.rs-api-3.0.0.jar" kemasanku/*.java
```

#### linux
```bash
/usr/lib/jvm/temurin-17-jdk/bin/javac -cp "jakarta.enterprise.cdi-api-3.0.0.jar:jakarta.inject-api-2.0.0.jar:jakarta.mvc-api-2.0.0.jar:jakarta.servlet-api-5.0.0.jar:jakarta.validation-api-3.0.0.jar:jakarta.ws.rs-api-3.0.0.jar" kemasanku/*.java
```
