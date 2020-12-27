package com.mem.vo.controller.user;

import com.mem.vo.business.base.model.po.UserAddress;
import com.mem.vo.business.base.model.po.UserAddressQuery;
import com.mem.vo.business.base.service.UserAddressService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-26 22:05
 */
@RestController
@RequestMapping("/user/address")
@Slf4j
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private TokenService tokenService;

    @Transactional
    @CommonExHandler(key = "添加用户地址")
    @PostMapping("/add")
    public ResponseDto<Integer> inset(@RequestHeader String token, UserAddress userAddress) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        BizAssert.notNull(userAddress, BizCode.PARAM_NULL.getMessage());
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        userAddress.setUserId(commonLoginContext.getUserId());
        //如果添加的是默认地址
        if (userAddress.getIsDefault().equals(1)) {
            UserAddressQuery query = UserAddressQuery.builder()
                    .userId(userAddress.getUserId())
                    .isDefault(1)
                    .build();
            List<UserAddress> userAddresses = userAddressService.findByCondition(query);
            if (CollectionUtils.isNotEmpty(userAddresses)) {
                for (UserAddress address : userAddresses) {
                    address.setIsDefault(0);
                    userAddressService.updateById(address);
                }
            }
        }
        return responseDto.successData(userAddressService.insert(userAddress));
    }

    @CommonExHandler(key = "根据id查找用户地址")
    @PostMapping("/findById")
    public ResponseDto<UserAddress> findById(@RequestHeader String token, Long id) {
        ResponseDto<UserAddress> responseDto = ResponseDto.successDto();
        BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
        return responseDto.successData(userAddressService.findById(id));
    }

    @CommonExHandler(key = "根据token查找用户地址")
    @PostMapping("/findByUser")
    public ResponseDto<List<UserAddress>> findByUser(@RequestHeader String token) {
        ResponseDto<List<UserAddress>> responseDto = ResponseDto.successDto();
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        UserAddressQuery query = UserAddressQuery
                .builder()
                .userId(commonLoginContext.getUserId())
                .build();
        return responseDto.successData(userAddressService.findByCondition(query));
    }

    @Transactional
    @CommonExHandler(key = "根据ID更新地址")
    @PostMapping("/updateById")
    public ResponseDto<Integer> update(@RequestHeader String token, UserAddress userAddress) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        BizAssert.notNull(userAddress.getId(), BizCode.PARAM_NULL.getMessage());
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        //如果是默认地址
        if (userAddress.getIsDefault().equals(1)) {
            UserAddressQuery query = UserAddressQuery.builder()
                    .userId(commonLoginContext.getUserId())
                    .isDefault(1)
                    .build();
            List<UserAddress> userAddresses = userAddressService.findByCondition(query);
            if (CollectionUtils.isNotEmpty(userAddresses)) {
                for (UserAddress address : userAddresses) {
                    address.setIsDefault(0);
                    userAddressService.updateById(address);
                }
            }
        }
        return responseDto.successData(userAddressService.updateById(userAddress));
    }

    @CommonExHandler(key = "根据ID删除地址")
    @PostMapping("/deleteById")
    public ResponseDto<Integer> deleteById(@RequestHeader String token, Long id) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
        return responseDto.successData(userAddressService.deleteById(id));
    }

    @CommonExHandler(key = "根据用户默认地址")
    @PostMapping("/findDefaultAddress")
    public ResponseDto<UserAddress> findDefaultAddress(@RequestHeader String token) {
        ResponseDto<UserAddress> responseDto = ResponseDto.successDto();
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        UserAddressQuery query = UserAddressQuery.builder()
                .userId(commonLoginContext.getUserId())
                .isDefault(1)
                .build();
        List<UserAddress> userAddressList = userAddressService.findByCondition(query);
        if (CollectionUtils.isEmpty(userAddressList)) {
            return responseDto.successData(null);
        }
        return responseDto.successData(userAddressList.get(0));
    }
}
