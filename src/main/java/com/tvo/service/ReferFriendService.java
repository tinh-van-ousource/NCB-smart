package com.tvo.service;

import com.tvo.dto.ReferFriendConfigurationDto;
import com.tvo.dto.ReferFriendRegistrationDto;
import com.tvo.request.SearchReferFriendRequest;
import com.tvo.request.UpdateReferFriendConfiguration;
import com.tvo.response.ResponeData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author thanglt on 11/2/2020
 * @version 1.0
 */
public interface ReferFriendService {
    /**
     * Search ReferFriendsConfiguration.
     *
     * @param pageable pageable
     * @return Page<ReferFriendsConfigurationDto>
     * @throws Exception Exception
     */
    ResponeData<Page<ReferFriendConfigurationDto>> search(Pageable pageable) throws Exception;

    /**
     * Create ReferFriendsConfiguration.
     *
     * @param friendsConfiguration friendsConfiguration
     * @return ReferFriendsConfigurationDto
     * @throws Exception Exception
     */
    ResponeData<ReferFriendConfigurationDto> create(UpdateReferFriendConfiguration friendsConfiguration) throws Exception;

    /**
     * Update ReferFriendsConfiguration.
     *
     * @param id                   id
     * @param friendsConfiguration friendsConfiguration
     * @return ReferFriendsConfigurationDto
     * @throws Exception Exception
     */
    ResponeData<ReferFriendConfigurationDto> update(Long id, UpdateReferFriendConfiguration friendsConfiguration) throws Exception;

    /**
     * Find ReferFriendsConfiguration by id.
     *
     * @param id id
     * @return ReferFriendsConfigurationDto
     * @throws Exception Exception
     */
    ResponeData<ReferFriendConfigurationDto> detail(Long id) throws Exception;

    /**
     * Delete QrService.
     *
     * @param id id
     * @return Boolean
     * @throws Exception Exception
     */
    ResponeData<Boolean> delete(Long id) throws Exception;

    /**
     * Search ReferFriendsRegistration.
     *
     * @param search SearchReferFriendRequest
     * @param pageable pageable
     * @return Page<ReferFriendRegistrationDto>
     * @throws Exception Exception
     */
    ResponeData<Page<ReferFriendRegistrationDto>> searchReferFriend(SearchReferFriendRequest search,
                                                                    Pageable pageable) throws Exception;
}
