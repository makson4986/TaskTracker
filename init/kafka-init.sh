
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

create_topic "EMAIL_SENDING_TASKS"
create_topic "GREETING_SENDING"
create_topic "SUMMARIZATION_REQUESTS"
create_topic "SUMMARIZATION_RESPONSES"

echo "All topics created successfully"
