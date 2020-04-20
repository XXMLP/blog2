#注意，docker命令全部大写，这是规定。
#   From 关键字表示，jar包依赖的环境。java:8  相当于jdk1.8
FROM java:8

#ADD命令
#   blog-0.0.1-SNAPSHOT.jar：这是你上传jar包的名称。
#   /blog.jar：这是自定义的名称。但是注意要有之前的/
ADD blog-2.0.0.jar /blog-2.0.0.jar
#COPY jacocoagent.jar $APP_DIR
ADD
#MAINTAINER  作者名称。可以删除不写。
MAINTAINER xxmlp

#EXPOSE 项目暴露的端口号
EXPOSE 8080

#/blog.jar此处的名称要和ADD命令后面的一样。
ENTRYPOINT ["java","-jar","/blog-2.0.0.jar"]