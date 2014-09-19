package com.liubaing.galaxy.repository.jpa;

import com.liubaing.galaxy.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 类说明:设备令牌持久化
 *
 * @author heshuai
 * @version 2012-5-7
 */

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Integer> {

    DeviceToken findByUseridAndDevicetoken(int userID, String deviceToken);

    List<DeviceToken> findByStatus(int status);

    List<DeviceToken> findByUseridAndStatus(int userID, int status);

    @Modifying
    @Query("delete from DeviceToken where devicetoken in ?1 ")
    void deleteByDevicetokenIn(String[] tokens);

}
