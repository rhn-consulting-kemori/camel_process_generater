# camel_process_generator
## Pod作成
* podman pod create --name camel-process-gen-pod -p 2181:2181/tcp,9092:9092/tcp,8190:8190/tcp,8191:8191/tcp,8192:8192/tcp
## Kafka
* podman run -d --pod=camel-process-gen-pod -e ZOOKEEPER_CLIENT_PORT="2181" -e KAFKA_OPTS="-Dlog4j.configuration=file:/etc/kafka/log4j.properties" --name=zookeeper_process_gen docker.io/confluentinc/cp-zookeeper:7.4.3
* podman run -d --pod camel-process-gen-pod -e KAFKA_ZOOKEEPER_CONNECT="localhost:2181" -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP="PLAINTEXT:PLAINTEXT" -e KAFKA_ADVERTISED_LISTENERS="PLAINTEXT://localhost:9092" -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR="1" -e KAFKA_CONFLUENT_LICENSE_TOPIC_REPLICATION_FACTOR="1" -e KAFKA_CONFLUENT_BALANCER_TOPIC_REPLICATION_FACTOR="1" -e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR="1" -e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR="1" -e CONFLUENT_METRICS_REPORTER_TOPIC_REPLICAS="1" -e KAFKA_OPTS="-Dlog4j.configuration=file:/etc/kafka/log4j.properties" --name kafka-broker-process-gen docker.io/confluentinc/cp-kafka:7.4.3
## Camel
### git_push_camel
* cd git_push_camel
* mvn package
* podman build -t git_push_camel .
* podman run -d --pod camel-process-gen-pod -e REPOSITORY_NAME="@" -e REMOTE_NAME="@" -e GIT_USER="@" -e GIT_TOKEN="@" -e TOPIC_NAME="@" --name git_push_camel git_push_camel
### camel_code_generator
* cd camel_code_generator
* mvn package
* podman build -t camel_code_generator .
* podman run -d --pod camel-process-gen-pod -e INPUT_TOPIC_NAME="@" -e OUTPUT_TOPIC_NAME="@" --name camel_code_generator camel_code_generator
### git_consumer
* cd git_consumer
* mvn package
* podman build -t git_consumer .
* podman run -d --pod camel-process-gen-pod -e CLONE_REPOSITORY_NAME="@" -e REMOTE_NAME="@" -e GIT_USER="@" -e GIT_TOKEN="@" -e TOPIC_NAME="@" -e REPOSITORY_FOLDER="/opt/repo" --name git_consumer git_consumer
