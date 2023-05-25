package com.example.shopnoticeboard.Dto.shop;

import com.example.shopnoticeboard.Entity.Shop;
import com.example.shopnoticeboard.Entity.User;
import com.example.shopnoticeboard.enums.UserRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ShopUpdateRequest {
    private String shopOwner, shopName, shopLocation;

    public Shop toEntity() {
        return Shop.builder()
                .shopName(shopName)
                .shopLocation(shopLocation)
                .build();
    }
}
