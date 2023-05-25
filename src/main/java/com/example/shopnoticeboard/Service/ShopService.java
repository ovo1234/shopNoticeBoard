package com.example.shopnoticeboard.Service;

import com.example.shopnoticeboard.Dto.shop.*;
import com.example.shopnoticeboard.Dto.user.UserDeleteRequest;
import com.example.shopnoticeboard.Dto.user.UserViewResponse;
import com.example.shopnoticeboard.Entity.Shop;
import com.example.shopnoticeboard.Entity.User;
import com.example.shopnoticeboard.Repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.shopnoticeboard.Error.ErrorCode.ACCESS_DENIED_EXCEPTION;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {
    private final ShopRepository shopRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // 가게 등록
    public void signup(ShopSingupRequest shopSingupRequest, HttpServletRequest request) {
        User user = userService.findUserByToken(request);
        shopSingupRequest.setShopOwner(user.getNickname());
        shopRepository.save(shopSingupRequest.toEntity());
    }

    // 가게 정보 수정
    public void updateShop(ShopUpdateRequest shopUpdateRequest, HttpServletRequest request) {
        User user = userService.findUserByToken(request);
        Shop shop = shopRepository.findByShopOwner(user.getNickname()).orElseThrow();
        shop.updateShop(shopUpdateRequest);
        shopRepository.save(shop);
    }

    // 가게 물품 수정
    public void updateThing(ShopThingUpdateRequest shopThingUpdateRequest, HttpServletRequest request) {
        User user = userService.findUserByToken(request);
        Shop shop = shopRepository.findByShopOwner(user.getNickname()).orElseThrow();
        shop.updateThing(shopThingUpdateRequest);
        shopRepository.save(shop);
    }

    // 모든 가게 보기
    public List<Shop> viewAll() {
        return shopRepository.findAll();
    }

    // 한 가게 보기
    public ShopViewResponse viewOne(String shopName) {
        Shop shop = shopRepository.findByShopName(shopName).orElseThrow();
        ShopViewResponse shopViewResponse = ShopViewResponse.builder()
                .shopOwner(shop.getShopOwner())
                .shopName(shop.getShopName())
                .shopLocation(shop.getShopLocation())
                .shopThing(shop.getShopThing())
                .build();

        return shopViewResponse;
    }

    // 가게 삭제
    public String deleteShop(ShopDeleteRequest shopDeleteRequest, HttpServletRequest request) {
        User user = userService.findUserByToken(request);
        Shop shop = shopRepository.findByShopName(shopDeleteRequest.getShopName()).orElseThrow();
        if(passwordEncoder.matches(shopDeleteRequest.getPassword(), user.getPassword())){
            shopRepository.deleteByShopName(shop.getShopName());
            return "가게가 삭제되었습니다.";
        }
        return "비밀번호가 맞지 않습니다. 다시 한번 해주세요.";
    }
}
