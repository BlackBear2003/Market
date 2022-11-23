package com.wzl.market.config;

/**
 * @Author : JCccc
 * @CreateTime : 2020/8/26
 * @Description :
 **/
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzl.market.messaging.SendMsgService;
import com.wzl.market.pojo.ChatMessage;
import com.wzl.market.utils.JsonUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.JavaScriptUtils;


@Configuration
public class MyRabbitConfig {

    @Autowired
    SendMsgService sendMsgService;


    //绑定键
    public final static String chatTopicKey = "message.chat";
    public final static String notifyTopicKey = "message.notify";
    //队列
    public static final String chatQueue = "chatQueue";
    public static final String notifyQueue = "notifyQueue";

    @Bean
    public Queue chatQueue() {
        return new Queue(MyRabbitConfig.chatQueue,true,false,false);
    }
    @Bean
    public Queue notifyQueue() {
        return new Queue(MyRabbitConfig.notifyQueue,true,false,false);
    }


    @Bean
    TopicExchange chatModuleExchange() {
        return new TopicExchange("chatModuleExchange",true,false);
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1", 5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true); // 必须要设置
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingExchangeChatMessage() {
        return BindingBuilder.bind(chatQueue()).to(chatModuleExchange()).with(chatTopicKey);
    }
    @Bean
    Binding bindingExchangeNotifyMessage() {
        return BindingBuilder.bind(notifyQueue()).to(chatModuleExchange()).with(notifyTopicKey);
    }


    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
                System.out.println("ConfirmCallback:     "+"原因："+cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback:     "+"消息："+message);
                System.out.println("ReturnCallback:     "+"回应码："+replyCode);
                System.out.println("ReturnCallback:     "+"回应信息："+replyText);
                System.out.println("ReturnCallback:     "+"交换机："+exchange);
                System.out.println("ReturnCallback:     "+"路由键："+routingKey);
            }
        });

        return rabbitTemplate;
    }




    /**
     * 接受消息的监听，这个监听会接受消息队列1的消息
     * 针对消费者配置
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer chatQueueListenerContainer() {
        SimpleMessageListenerContainer chatQueueListenerContainer = new SimpleMessageListenerContainer(connectionFactory());
        //SimpleMessageListenerContainer container1 = new SimpleMessageListenerContainer(connectionFactory());
        chatQueueListenerContainer.setQueues(chatQueue());
        chatQueueListenerContainer.setExposeListenerChannel(true);
        chatQueueListenerContainer.setMaxConcurrentConsumers(1);
        chatQueueListenerContainer.setConcurrentConsumers(1);
        chatQueueListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        chatQueueListenerContainer.setMessageListener(new ChannelAwareMessageListener() {

            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                byte[] body = message.getBody();
                String msg = new String(body);
                ChatMessage chatMessage = JsonUtil.parseJsonToObj(msg, ChatMessage.class);

                System.out.println("rabbitmq收到消息 : " +msg);

                Boolean sendToWebsocket = sendMsgService.sendChatMsg(chatMessage);

                System.out.println("is Send? "+sendToWebsocket);

                if (sendToWebsocket){
                    System.out.println("消息处理成功！ 已经推送到websocket！");
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), true); //确认消息成功消费
                }
            }
        });
        return chatQueueListenerContainer;
    }
}
