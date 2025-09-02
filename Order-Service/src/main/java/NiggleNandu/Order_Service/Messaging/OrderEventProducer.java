package NiggleNandu.Order_Service.Messaging;

import NiggleNandu.Order_Service.Dtos.OrderDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public OrderEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendOrderPlaced(OrderDto orderDto) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                orderDto
        );

        System.out.println("Sent OrderDto to queue: orderId=" + orderDto.getId());
    }
}
