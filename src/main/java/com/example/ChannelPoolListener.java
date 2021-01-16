package com.example;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class ChannelPoolListener extends ChannelInitializer {

    @Override
    public void initialize(Channel channel) throws IOException {
        channel.exchangeDeclare("micronaut", BuiltinExchangeType.DIRECT, true); //deklarujemy wymianę o nazwie "micronaut"
        channel.queueDeclare("analytics", true, false, false, null); //deklarujemy kolejkę. Konsumer będzie nasłuchiwał wiadomości w tej kolejce
        channel.queueBind("analytics", "micronaut", "analytics"); //definiujemy powiązanie między exchange (wymianą) a kolejką używając kluczy
    }
}
