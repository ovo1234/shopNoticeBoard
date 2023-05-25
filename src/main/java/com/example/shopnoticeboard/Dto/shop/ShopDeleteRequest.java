package com.example.shopnoticeboard.Dto.shop;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ShopDeleteRequest {
    private String shopName, password;
}
