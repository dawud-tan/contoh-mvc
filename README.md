Template Jakarta MVC 2.0 untuk dipasang di Apache Tomcat 10.0.18 ke atas, dengan Eclipse Temurin Java JDK versi 17
Kode sumber bisa saja di _build_ dengan editor Eclipse, Netbeans, IntelliJ, Visual Studio Code, ataupun lewat command prompt/terminal dengan menjalankan perintah _gradlew war_
Setelah dibuild lalu berkas _ROOT.war_ akan tersedia di folder _build/libs/ROOT.war_
Berkas _ROOT.war_ bisa disalin ke folder webapps di dalam folder tomcat 10.0.18
Setelah tomcat dijalankan, silahkan akses web berikut: http://127.0.0.1:8080/barang

## Deskripsi Projek
1. Tomcat 10.0.18 mengimplementasikan API Servlet versi 5.0, JSP versi 3.0, dan EL versi 4.0
2. Driver JDBC Oracle **ojdbc11.jar** dan **ManyUserRolesRealm.jar** disalin ke $CATALINA_HOME/lib
3. Tambahkan `<Resource />` DataSourceFactory dan `<ResourceLink />` ke blok `<Context />` di dalam berkas **webapp/META-INF/context.xml**
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
4. Tambahkan `ManyUserRolesRealm` ke blok `<Engine />` di dalam berkas **webapp/META-INF/context.xml**: `<Realm className="org.apache.catalina.realm.ManyUserRolesRealm" />`
5. Di berkas **persistence.xml**, tambahkan `<non-jta-data-source>java:/comp/env/jdbc/NamaJDBC</non-jta-data-source>` ke blok `<persistence-unit />`
6. JSTL versi 2.0 (beda JSR-52) disediakan Glassfish jakarta jstl versi 2.0
7. Jakarta Restful versi 3.0 (JSR-370) disediakan Jersey versi 3.0.4
8. CDI versi 3.0 (JSR-365) disediakan Weld versi 4.0.3
9. JPA versi 3.0 (JSR-338) disediakan hibernate core jakarta versi 5.6.7
10. Biar bisa cepat ketika pengembangan, perlu tambahkan `apply plugin: 'com.bmuschko.cargo'`
    ```xml
    <!-- Definisikan Konfigurasi Login -->
    <login-config>
        <auth-method>BASIC</auth-method>
        <realm-name>ManyUserRolesRealm</realm-name>
    </login-config>
    ```
11. Tambahkan blok `<Realm />` ke blok `<Context />` di dalam berkas **webapp/META-INF/context.xml**
    ```xml
    <Realm className="org.apache.catalina.realm.ManyUserRolesRealm" dataSourceName="jdbc/PungkookidVietnam" localDataSource="true" ... />
    ```

## cara compile tanpa gradle

gradlew war -Dorg.gradle.java.home="C:/Program Files/AdoptOpenJDK/jdk-8.0.275.1-hotspot"

javac -cp "jakarta.enterprise.cdi-api-3.0.0.jar;jakarta.inject-api-2.0.0.jar;jakarta.mvc-api-2.0.0.jar;jakarta.servlet-api-5.0.0.jar;jakarta.validation-api-3.0.0.jar;jakarta.ws.rs-api-3.0.0.jar" kemasanku/*.java
