import org.apache.camel.builder.RouteBuilder;

public class CamelKafka extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    from("telegram:bots")
      .log("Command received: ${body}")
      .setHeader("kafka.KEY", constant("Telegram")) // Key of the message
    .to("kafka:my-topic");

    from("kafka:my-topic")
      .log("Message received from Kafka : ${body}")
      .log("    on the topic ${headers[kafka.TOPIC]}")
      .log("    on the partition ${headers[kafka.PARTITION]}")
      .log("    with the offset ${headers[kafka.OFFSET]}")
      .log("    with the key ${headers[kafka.KEY]}");

  }
}

