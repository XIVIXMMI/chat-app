package com.omori.chatapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = ChatApplication.class)
@Import(TestConfig.class)
class ChatApplicationTests {

  @Test
  void contextLoads() {
  }

}
