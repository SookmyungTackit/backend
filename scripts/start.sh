#!/bin/bash

# 기존에 실행되던 앱 종료
pkill -f '.*\.jar'

# 새 .jar 파일 실행 (로그는 nohup.out에 기록됨)
nohup /usr/bin/java -jar /home/ubuntu/app/*.jar > /home/ubuntu/app/nohup.out 2>&1 &