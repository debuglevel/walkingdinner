## Building stage
FROM node:16.13.0-alpine3.12 AS builder
WORKDIR /src/

# cache dependencies
COPY package*.json /src/
#RUN npm install --legacy-peer-deps
RUN npm install

# build source
COPY . /src/
RUN $(npm bin)/ng build --output-path=./dist/out

## Final image
FROM nginx:1.21.4-alpine
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /src/docker/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /src/dist/out/ /usr/share/nginx/html

# create settings.json file based on environment variables on startup
CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/assets/settings.template.json > /usr/share/nginx/html/assets/settings.json && exec nginx -g 'daemon off;'"]
