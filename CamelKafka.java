import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.component.kafka.KafkaConstants;


public class CamelKafka extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    String kafkaTopic = "my-topic";
    String kafkaBootstrap = "my-cluster-kafka-bootstrap";
    String kafkaDestination = "kafka:"+kafkaTopic+"?brokers="+kafkaBootstrap;

    from("timer:message-publisher")
      .setBody().simple("Ramalho publishing into Kafka")
      .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
    .to("kafka:my-topic?brokers=my-cluster-kafka-bootstrap:9092");

    from("kafka:my-topic?brokers=my-cluster-kafka-bootstrap:9092")
      .log("Message received from Kafka : ${body}")
      .log("    on the topic ${headers[kafka.TOPIC]}")
      .log("    on the partition ${headers[kafka.PARTITION]}")
      .log("    with the offset ${headers[kafka.OFFSET]}")
      .log("    with the key ${headers[kafka.KEY]}");

  }
}

