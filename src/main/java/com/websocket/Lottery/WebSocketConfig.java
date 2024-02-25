package com.websocket.Lottery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    MyInterceptor myInterceptor;

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/lottery/ws")//.addInterceptors(myInterceptor)
                .setAllowedOriginPatterns("http://localhost:8081")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("X-Authorization");
                    List<String> user = accessor.getNativeHeader("X-User");
//                    log.debug("X-Authorization: {}", authorization);

                    String accessToken = authorization.get(0).split(" ")[1];
                    String userId = user.get(0);
                    if(!userId.equalsIgnoreCase("Abhinav")){
                        return null;
                    }

                }
                return message;
            }
        });
    }


    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("X-Authorization");
                    List<String> user = accessor.getNativeHeader("X-User");
//                    log.debug("X-Authorization: {}", authorization);

                    String accessToken = authorization.get(0).split(" ")[1];
                    String userId = user.get(0);
                    if(!userId.equalsIgnoreCase("Abhina")){
                        return null;
                    }
                    HttpServletRequest httpServletRequest;

                }
                return message;
            }
        });
    }

//    private HttpServletRequest mapToHSR()
}

/**
 * request.getMethod()  stompCOmmand()
 * req.getRequestURI()
 * req.getHeader("")
 *
 * req.setAttribute(key, val)
 *
 *
 *
 */
