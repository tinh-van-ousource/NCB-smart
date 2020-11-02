package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.dto.ReferFriendConfigurationDto;
import com.tvo.dto.ReferFriendRegistrationDto;
import com.tvo.request.SearchReferFriendRequest;
import com.tvo.request.UpdateReferFriendConfiguration;
import com.tvo.response.ResponeData;
import com.tvo.service.ReferFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/refer-friend")
public class ReferFriendController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReferFriendService referFriendService;

    @GetMapping(value = "")
    public ResponeData<Page<ReferFriendConfigurationDto>> search(@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return referFriendService.search(pageable);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PostMapping(value = "")
    public ResponeData<ReferFriendConfigurationDto> create(@RequestBody UpdateReferFriendConfiguration createReferFriendConfiguration) {
        try {
            return referFriendService.create(createReferFriendConfiguration);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponeData<ReferFriendConfigurationDto> update(@PathVariable Long id,
                                                           @RequestBody UpdateReferFriendConfiguration updateReferFriendConfiguration) {
        try {
            return referFriendService.update(id, updateReferFriendConfiguration);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponeData<ReferFriendConfigurationDto> detail(@PathVariable Long id) {
        try {
            return referFriendService.detail(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponeData<Boolean> delete(@PathVariable Long id) {
        try {
            return referFriendService.delete(id);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }

    @GetMapping(value = "/friends")
    public ResponeData<Page<ReferFriendRegistrationDto>> searchReferFriend(SearchReferFriendRequest request
            , @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        try {
            return referFriendService.searchReferFriend(request, pageable);
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
    }
}

