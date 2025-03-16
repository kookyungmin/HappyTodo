#!/bin/sh


BASE_DIR=/Users/kookyungmin/mypets/HappyTodo/client
SERVER_PATH=deepwide@140.238.19.69
APP_PATH=/app/todo/client

cd $BASE_DIR
npm run build:dev
rsync -avh $BASE_DIR/dist/ $SERVER_PATH:$APP_PATH -e "ssh -o StrictHostKeyChecking=no"
