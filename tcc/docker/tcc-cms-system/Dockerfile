FROM qtsashoppo.azurecr.io/cherrypicks/tomcat:8.0

MAINTAINER Rahul rui <rahulrui@ideaculture.com>

WORKDIR /app

COPY ../../tcc-cms/tcc-cms-system/tcc-cms-system-provider/target/tcc_cms_system.war /app/tomcat/webapps/

EXPOSE 8080

CMD ["/app/tomcat/bin/catalina.sh", "run"]