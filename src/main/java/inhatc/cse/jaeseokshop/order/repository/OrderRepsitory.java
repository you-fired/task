package inhatc.cse.jaeseokshop.order.repository;

import inhatc.cse.jaeseokshop.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepsitory extends JpaRepository<Order,Long> {

}
