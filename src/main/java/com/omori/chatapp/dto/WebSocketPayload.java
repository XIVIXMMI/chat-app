
package com.omori.chatapp.dto;

import com.omori.chatapp.domain.enums.WebSocketAction;
import java.time.LocalDateTime;

public class WebSocketPayload<T> {

  private WebSocketAction action;
  private T data;
  private LocalDateTime timestamp;

  public WebSocketPayload(WebSocketAction action, T data) {
    this.action = action;
    this.data = data;
    this.timestamp = LocalDateTime.now();
  }

  // getter & getter
  public WebSocketAction getAction() {
    return action;
  }

  public void setAction(WebSocketAction action) {
    this.action = action;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void getTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}
