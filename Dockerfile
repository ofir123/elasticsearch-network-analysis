FROM docker.elastic.co/elasticsearch/elasticsearch:5.5.2

COPY "target/releases/*.zip" "/opt/plugin.zip"
RUN ["bin/elasticsearch-plugin", "install", "file:///opt/plugin.zip"]

# This Docker image is meant for plugin testing only
ENV "xpack.security.enabled" "false"

