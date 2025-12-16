FROM mysql:8.0
ENV MYSQL_ROOT_PASSWORD=rootpassword
COPY ./init-scripts:/docker-entrypoint-initdb.d
EXPOSE 3306
