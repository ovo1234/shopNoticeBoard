package com.example.shopnoticeboard.Entity;

import com.example.shopnoticeboard.Dto.shop.ShopThingUpdateRequest;
import com.example.shopnoticeboard.Dto.shop.ShopUpdateRequest;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String shopOwner;

    @Column
    private String shopName;

    @Column
    private String shopLocation;

    @Column(name = "shopThing", columnDefinition = "json")
    private String shopThing;

    public void updateShop(ShopUpdateRequest shopUpdateRequest) {
        this.shopName = shopUpdateRequest.getShopName();
        this.shopLocation = shopUpdateRequest.getShopLocation();
    }

    public void updateThing(ShopThingUpdateRequest shopThingUpdateRequest) {
        this.shopThing = shopThingUpdateRequest.getShopThing();
    }

}
