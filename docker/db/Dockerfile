FROM arm64v8/mysql:8.0-oracle

RUN microdnf install glibc-locale-source glibc-langpack-ja

ENV LC_ALL=ja_JP.UTF-8
ENV LANG=ja_JP.UTF-8

ADD ./docker/db/my.cnf /etc/mysql/conf.d/my.cnf
