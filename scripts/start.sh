#!/bin/bash

# Systemd가 새로운 서비스 파일을 인식하도록 리로드
sudo systemctl daemon-reload

# 새 .jar 파일 실행 (로그는 nohup.out에 기록됨)
# nohup java -jar /home/ec2-user/app/build/libs/*[!plain].jar > /home/ec2-user/app/nohup.out 2>&1 &
sudo systemctl restart Tackit.Application
