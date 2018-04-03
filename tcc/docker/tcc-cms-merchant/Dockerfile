FROM qtsashoppo.azurecr.io/cherrypicks/tomcat:8.0

MAINTAINER Rahul rui <rahulrui@ideaculture.com>

WORKDIR /app

COPY ../tcc-cms/tcc-cms-merchant/tcc-cms-merchant-provider/target/tcc_cms_merchant.war /app/tomcat/webapps/

EXPOSE 8080

CMD ["/app/tomcat/bin/catalina.sh", "run"]