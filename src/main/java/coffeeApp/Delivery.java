package coffeeApp;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long deliveryId;
    private Long orderId;
    private Long productId;
    private Long paymentId;
    private String status;

    @PostPersist
    public void onPostPersist(){
        OrderAccepted orderAccepted = new OrderAccepted();
        BeanUtils.copyProperties(this, orderAccepted);
        orderAccepted.setOrderId(this.getOrderId());
        orderAccepted.setStatus("주문접수완료");
        orderAccepted.publishAfterCommit();
    }
    @PostRemove
    public void onPostRemove(){
        OrderCanceled orderCanceled = new OrderCanceled();
        BeanUtils.copyProperties(this, orderCanceled);
        orderCanceled.setStatus("주문취소완료");
        orderCanceled.publishAfterCommit();
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryIdd) {
        this.deliveryId = deliveryIdd;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
