FROM qtsashoppo.azurecr.io/cherrypicks/tomcat:8

MAINTAINER Rahul rui <rahulrui@ideaculture.com>

WORKDIR /app

COPY ../../tcc-cms/tcc-cms-web/target/tcc_cms.war /app/tomcat/webapps/

EXPOSE 8080

CMD ["/app/tomcat/bin/catalina.sh", "run"]