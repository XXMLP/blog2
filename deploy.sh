#!/bin/sh


DOCKER_DIR=/root/docker
mkdir -p $DOCKER_DIR
cd $DOCKER_DIR


if [ -d "blog2" ];
then
    echo "rm dir blog2"
	rm -rf blog2
	sleep 10
	echo "rm over"
else
    echo "dir blog2 not exit"
fi

git clone git@github.com:XXMLP/blog2.git

echo "git clone over"

cd $DOCKER_DIR/blog2
mvn clean package

sleep 10
echo "package over"

cd $DOCKER_DIR
#删除原有的文件
rm -f Dockerfile
rm -f blog-2.0.0.jar

mv $DOCKER_DIR/blog2/target/blog-2.0.0.jar .
mv $DOCKER_DIR/blog2/Dockerfile .
mv $DOCKER_DIR/blog2/src/main/resources/application-dev.yml .
mv $DOCKER_DIR/blog2/src/main/java/com/xxmlp/util/IP/qqwry.dat .
#docker cp blog-0.0.1-SNAPSHOT.jar blog:/

#docker restart blog
#删除旧的镜像容器
docker stop blog
docker rm blog
docker rmi blog:2.0
sleep 5
#打包成镜像
#说明:
#  blog   代表要打包成的镜像名称。按照自己实际情况写。
#  :1.0   代表版本号，可以不写则默认为latest
#  .    代表为当前目录。这就是为什么一直在步骤一文件夹中进行操作,并且Dockerfile在此文件夹中的原因。
#
#若之前Dockerfile不在步骤一的文件夹中 则需要指定到对应的地址
docker build -t blog:2.0 .
#启动容器
docker run --name blog -d -p 8080:8080 -v /uploadFile:/uploadFile -v/root/docker/log:/log  -v /root/docker/application-dev.yml:/application-dev.yml blog:2.0



