FROM node:alpine AS builder

WORKDIR /workspace/app

COPY . .

RUN npm install && npm run build

FROM nginx:alpine

COPY --from=builder /workspace/app/dist/* /usr/share/nginx/html/

COPY /nginx.conf /etc/nginx/conf.d/default.conf
