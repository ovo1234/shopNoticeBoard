package com.example.shopnoticeboard.Controller;

import com.example.shopnoticeboard.Dto.shop.*;
import com.example.shopnoticeboard.Dto.user.UserDeleteRequest;
import com.example.shopnoticeboard.Dto.user.UserSignupRequest;
import com.example.shopnoticeboard.Dto.user.UserViewResponse;
import com.example.shopnoticeboard.Entity.Shop;
import com.example.shopnoticeboard.Error.ErrorException;
import com.example.shopnoticeboard.Repository.ShopRepository;
import com.example.shopnoticeboard.Service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.example.shopnoticeboard.Error.ErrorCode.ACCESS_DENIED_EXCEPTION;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/shops")
public class ShopController {
    private final ShopService shopService;
    private final ShopRepository shopRepository;
    @PostMapping("/signup")
    public ResponseEntity<String> shopSignup(@RequestBody ShopSingupRequest shopSingupRequest, HttpServletRequest request) {
        if (shopRepository.existsByShopName(shopSingupRequest.getShopName())) {
            return ResponseEntity.badRequest().body("이미 등록된 가게 입니다.");
        }
        shopService.signup(shopSingupRequest, request);
        return ResponseEntity.ok("가게 등록이 완료되었습니다.");
    }

    @PutMapping("/updateShop")
    public ResponseEntity<String> shopUpdate(@RequestBody ShopUpdateRequest shopUpdateRequest, HttpServletRequest request) {
        shopService.updateShop(shopUpdateRequest, request);
        return ResponseEntity.ok("가게 주요 정보가 업데이트 되었습니다.");
    }
    @PutMapping("/updateThing")
    public ResponseEntity<String> ThingUpdate(@RequestBody ShopThingUpdateRequest shopThingUpdateRequest, HttpServletRequest request) {
        shopService.updateThing(shopThingUpdateRequest, request);
        return ResponseEntity.ok("가게 물품 정보가 업데이트 되었습니다.");
    }

    // 모든 가게 보기
    @GetMapping("/viewAll")
    public List<Shop> viewBoardList() {
        return shopService.viewAll();
    }

    // 한 가게 보기
    @GetMapping("/viewOne")
    public ShopViewResponse viewBoard(@RequestParam String shopName) {
        return shopService.viewOne(shopName);
    }

    // 가게 삭제
    @DeleteMapping("/delete")
    public String delete(@RequestBody ShopDeleteRequest shopDeleteRequest, HttpServletRequest request) {
        return shopService.deleteShop(shopDeleteRequest, request);
    }

}
