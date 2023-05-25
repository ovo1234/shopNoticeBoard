package com.example.shopnoticeboard.Dto.shop;

import com.example.shopnoticeboard.Entity.Shop;
import com.example.shopnoticeboard.Entity.User;
import com.example.shopnoticeboard.enums.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.Map;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ShopViewResponse {
    private String shopOwner, shopName, shopLocation, shopThing;

    // JSON 형식의 물건 이름과 가격을 가져오는 메서드
    public Map<String, String> getShopThing() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(this.shopThing, new TypeReference<Map<String, String>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
