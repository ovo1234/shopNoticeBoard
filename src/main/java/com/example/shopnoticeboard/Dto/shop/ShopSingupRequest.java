package com.example.shopnoticeboard.Dto.shop;

import com.example.shopnoticeboard.Entity.Shop;
import com.example.shopnoticeboard.Entity.User;
import com.example.shopnoticeboard.enums.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ShopSingupRequest {
    private String shopOwner ,shopName, shopLocation, shopThing;

    // JSON 형식으로 물건 이름과 가격을 설정하는 메서드
    public void setShopThing(Map<String, String> shopThingMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.shopThing = objectMapper.writeValueAsString(shopThingMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Shop toEntity() {
        return Shop.builder()
                .shopOwner(shopOwner)
                .shopName(shopName)
                .shopLocation(shopLocation)
                .shopThing(shopThing)
                .build();
    }
}