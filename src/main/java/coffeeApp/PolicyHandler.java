package coffeeApp;

import coffeeApp.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationRequested_ReservationAccept(@Payload OrderRequested orderRequested){

        if(orderRequested.isMe()){
            System.out.println("##### listener ReservationAccept : " + orderRequested.toJson());
            Delivery delivery = new Delivery();
            delivery.setOrderId(orderRequested.getOrderId());
            delivery.setPaymentId(orderRequested.getPaymentId());
            delivery.setProductId(orderRequested.getProductId());
            deliveryRepository.save(delivery);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCancelRequested_ReservationCancel(@Payload OrderCancelRequested orderCancelRequested){

        if(orderCancelRequested.isMe()){
            System.out.println("##### listener ReservationCancel : " + orderCancelRequested.toJson());
            Optional<Delivery> optionalDelivery = deliveryRepository.findByOrderId(orderCancelRequested.getOrderId());
            Delivery delivery = optionalDelivery.orElseGet(Delivery::new);
            deliveryRepository.delete(delivery);
        }
    }


}
