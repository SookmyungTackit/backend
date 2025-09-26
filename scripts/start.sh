#!/bin/bash

# Systemd가 새로운 서비스 파일을 인식하도록 리로드
sudo systemctl daemon-reload

# tackit 서비스를 활성화하여 서버 부팅 시 자동 시작되도록 설정
sudo systemctl enable smwu-tackit.service

# tackit 서비스를 재시작 (기존 프로세스는 자동 종료)
sudo systemctl restart smwu-tackit.service