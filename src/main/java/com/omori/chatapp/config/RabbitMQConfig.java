package com.omori.chatapp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  public static final String CHAT_QUEUE = "chat.queue";
  public static final String CHAT_EXCHANGE = "chat.exchange";
  public static final String CHAT_ROUTING_KEY = "chat.key";

  @Bean
  public Queue queue() {
    return new Queue(CHAT_QUEUE, false);
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(CHAT_EXCHANGE);
  }

  @Bean
  public Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(CHAT_ROUTING_KEY);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
          ConnectionFactory factory,
          Jackson2JsonMessageConverter messageConverter) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    return  new Jackson2JsonMessageConverter();
  }

}
