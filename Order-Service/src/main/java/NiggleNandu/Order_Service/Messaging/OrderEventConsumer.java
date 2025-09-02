package NiggleNandu.Order_Service.Messaging;

import NiggleNandu.Order_Service.Dtos.OrderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    // Listen to the orderQueue
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveOrder(OrderDto orderDto) {
        System.out.println("Received OrderDto from queue: orderId=" + orderDto.getId());
    }
}
