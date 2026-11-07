set -e

BOOTSTRAP_SERVER="${KAFKA_BOOTSTRAP_SERVER}"

until /opt/kafka/bin/kafka-topics.sh \
    --bootstrap-server "$BOOTSTRAP_SERVER" \
    --list >/dev/null 2>&1
do
    sleep 3
done

echo "Creating topics..."

create_topic() {
    local topic="$1"

    echo "Creating topic: $topic"

    /opt/kafka/bin/kafka-topics.sh \
        --bootstrap-server "$BOOTSTRAP_SERVER" \
        --create \
        --if-not-exists \
        --topic "$topic" \
        --partitions 3 \
        --replication-factor 3
}

create_topic "$KAFKA_GREETING_TOPIC"
create_topic "$KAFKA_SUMMARIZATION_REQUESTS_TOPIC"
create_topic "$KAFKA_SUMMARIZATION_RESPONSES_TOPIC"
create_topic "$KAFKA_REPORT_TOPIC"

echo "All topics created successfully"