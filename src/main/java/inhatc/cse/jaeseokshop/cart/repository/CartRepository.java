package inhatc.cse.jaeseokshop.cart.repository;

import inhatc.cse.jaeseokshop.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

}
