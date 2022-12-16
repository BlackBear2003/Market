FROM java:8

MAINTAINER "luke <348358584@qq.com>"

COPY *.jar /app.jar

CMD ["--server.port=8080"]

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]