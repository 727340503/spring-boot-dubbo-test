FROM qtsashoppo.azurecr.io/cherrypicks/tomcat:8.0

MAINTAINER Rahul rui <rahulrui@ideaculture.com>

WORKDIR /app

COPY ../tcc-cms/tcc-cms-campaign/tcc-cms-campaign-provider/target/tcc_cms_campaign.war /app/tomcat/webapps/

EXPOSE 8080

CMD ["/app/tomcat/bin/catalina.sh", "run"]