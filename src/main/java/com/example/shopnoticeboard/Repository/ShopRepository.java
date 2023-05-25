package com.example.shopnoticeboard.Repository;

import com.example.shopnoticeboard.Entity.Shop;
import com.example.shopnoticeboard.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    boolean existsByShopName(String shopName);
    Optional<Shop> findByShopOwner(String shopOwner);
    Optional<Shop> findByShopName(String shopName);

    @Modifying
    @Query("DELETE FROM Shop s WHERE s.shopName = :shopName")
    void deleteByShopName(@Param("shopName")String shopName);
}
