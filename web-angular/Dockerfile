## Building stage
FROM node:10.15.3-alpine AS builder
WORKDIR /src/

# cache dependencies
COPY package*.json /src/
RUN npm install

# build source
COPY . /src/
RUN $(npm bin)/ng build --output-path=./dist/out

## Final image
FROM nginx:1.15
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /src/docker/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /src/dist/out/ /usr/share/nginx/html

# create settings.json file based on environment variables on startup
CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/assets/settings.template.json > /usr/share/nginx/html/assets/settings.json && exec nginx -g 'daemon off;'"]
