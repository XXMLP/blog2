#!/bin/sh


DOCKER_DIR=/root/docker
mkdir -p $DOCKER_DIR
cd $DOCKER_DIR

#如果存在旧的blog文档，则删除
if [ -d "blog2" ];
then
    echo "rm dir blog2"
	rm -rf blog2
	sleep 10
	echo "rm over"
else
    echo "dir blog2 not exit"
fi

#下载源码
git clone git@github.com:XXMLP/blog2.git

echo "git clone over"
#打包
cd $DOCKER_DIR/blog2
mvn package

sleep 10
echo "package over"

cd $DOCKER_DIR
#删除原有的文件
rm -f Dockerfile
rm -f blog-0.0.1-SNAPSHOT.jar

mv $DOCKER_DIR/blog2/target/blog-0.0.1-SNAPSHOT.jar .
mv $DOCKER_DIR/blog2/Dockerfile .

#删除旧的镜像和容器
docker stop blog
docker rm blog
docker rmi blog:1.0
sleep 10
#打包成镜像
#说明:
#  blog   代表要打包成的镜像名称。按照自己实际情况写。
#  :1.0   代表版本号，可以不写则默认为latest
#  .    代表为当前目录。这就是为什么一直在步骤一文件夹中进行操作,并且Dockerfile在此文件夹中的原因。
#
#若之前Dockerfile不在步骤一的文件夹中 则需要指定到对应的地址
docker build -t blog:1.0 .
#启动容器，前提是已开放这个端口
docker run --name blog -d -p 80:8080 blog:1.0

#mvn clean verify -f pom_cc.xml
#jar -xvf blog-0.0.1-SNAPSHOT.jar
#java -javaagent:jacocoagent.jar=includes=*,output=tcpserver,port=2015,address=localhost -jar blog-0.0.1-SNAPSHOT.jar
#mvn clean verify -f pom_onthefly.xml